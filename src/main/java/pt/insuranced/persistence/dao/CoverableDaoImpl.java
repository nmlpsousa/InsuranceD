package pt.insuranced.persistence.dao;

import pt.insuranced.models.Coverable;
import pt.insuranced.persistence.connection.ConnectionManager;
import pt.insuranced.persistence.dao.sdk.interfaces.CoverableDao;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoverableDaoImpl implements CoverableDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CoverageDaoImpl.class);
	
    @Override
    public Optional<Coverable> get(long coverableId) throws InsuranceDException {
    	Connection connection = null;
        Boolean previousAutoCommit = null;
        Coverable coverable = null;
        try {
            connection = ConnectionManager.getConnection();
            previousAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            
            PreparedStatement preparedStatement = connection.prepareStatement(
        			"SELECT * FROM public.coverable c WHERE c.id=?;");
            
            preparedStatement.setLong(1, coverableId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
            	LOGGER.info("There is no coverable with the ID {}.", coverableId);
            	return Optional.empty();
            }

            String description = resultSet.getString("description");
            Long policyId = resultSet.getLong("policyid");
            
            Coverable.Builder builder = Coverable.Builder.newBuilder();
            builder.setId(coverableId);
            builder.setPolicyId(policyId);
            builder.setDescription(description);
            
            coverable = builder.build();
            
            LOGGER.info("Got Coverable with ID {}: Description={}, PolicyID={}.", 
            		coverableId, description, policyId);

            connection.commit();
        } catch (SQLException e) {
            rollbackConnection(connection);
            throw new InsuranceDException("Error connecting with the database.", e);
        } finally {
            if (previousAutoCommit != null) {
                restoreAutoCommitAndCloseConnection(connection, previousAutoCommit);
            } else {
                closeConnection(connection);
            }
        }

        return Optional.ofNullable(coverable);
    }

    @Override
    public Coverable insert(Coverable coverable) throws InsuranceDException {
    	Connection connection = null;
        Boolean previousAutoCommit = null;
        try {
            connection = ConnectionManager.getConnection();
            previousAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            long coverableId = insertCoverable(connection, coverable);
            
            LOGGER.info("Inserted Coverable ID is {}", coverableId);

            connection.commit();
        } catch (SQLException e) {
            rollbackConnection(connection);
            throw new InsuranceDException("Error connecting with the database.", e);
        } finally {
            if (previousAutoCommit != null) {
                restoreAutoCommitAndCloseConnection(connection, previousAutoCommit);
            } else {
                closeConnection(connection);
            }
        }
        return coverable;
    }

	@Override
    public Coverable update(Coverable coverable) throws InsuranceDException {
    	Connection connection = null;
        Boolean previousAutoCommit = null;
        try {
            connection = ConnectionManager.getConnection();
            previousAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            long coverableId = updateCoverable(connection, coverable);
            
            LOGGER.info("Successfully updated Coverable with ID {}", coverableId);

            connection.commit();
        } catch (SQLException e) {
            rollbackConnection(connection);
            throw new InsuranceDException("Error connecting with the database.", e);
        } finally {
            if (previousAutoCommit != null) {
                restoreAutoCommitAndCloseConnection(connection, previousAutoCommit);
            } else {
                closeConnection(connection);
            }
        }
        return coverable;
    }
    
    private long insertCoverable(Connection connection, Coverable coverable) throws SQLException, InsuranceDException {
    	PreparedStatement preparedStatement = connection.prepareStatement(
    			"INSERT INTO public.coverable(description, policyid) " + 
    			"VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
    	
    	preparedStatement.setString(1, coverable.getDescription());
    	preparedStatement.setLong(2, coverable.getPolicyId());

        int generatedKeys = preparedStatement.executeUpdate();
        if (generatedKeys == 0) {
            throw new InsuranceDException("Error inserting Coverable into the DB.");
        }

        preparedStatement.getGeneratedKeys().next();
        return preparedStatement.getGeneratedKeys().getLong("id");
	}
	
    private long updateCoverable(Connection connection, Coverable coverable) throws SQLException, InsuranceDException {
    	PreparedStatement preparedStatement = connection.prepareStatement(
    			"UPDATE public.coverable SET description=?, policyid=? " + 
    			"WHERE id=?;", Statement.RETURN_GENERATED_KEYS);
    	
    	preparedStatement.setString(1, coverable.getDescription());
    	preparedStatement.setLong(2, coverable.getPolicyId());
    	preparedStatement.setLong(3, coverable.getId());

        int generatedKeys = preparedStatement.executeUpdate();
        if (generatedKeys == 0) {
            throw new InsuranceDException("Error updating Coverable into the DB.");
        }

        preparedStatement.getGeneratedKeys().next();
        return preparedStatement.getGeneratedKeys().getLong("id");
	}

	private static void rollbackConnection(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.error("Error rolling back the connection.", e);
        }
    }

    private static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Error closing connection to the DB.", e);
        }
    }
    
    private static void restoreAutoCommitAndCloseConnection(Connection connection, Boolean previousAutoCommit) {
        try {
            connection.setAutoCommit(previousAutoCommit);
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Error closing connection to the DB.", e);
        }
    }
}
