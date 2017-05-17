package pt.insuranced.persistence.dao;

import pt.insuranced.models.Policy;
import pt.insuranced.persistence.connection.ConnectionManager;
import pt.insuranced.persistence.dao.sdk.interfaces.PolicyDao;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PolicyDaoImpl implements PolicyDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PolicyDaoImpl.class);

    @Override
    public Optional<Policy> get(long policyId) throws InsuranceDException {
    	Connection connection = null;
        Boolean previousAutoCommit = null;
        Policy policy = null;
        try {
            connection = ConnectionManager.getConnection();
            previousAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            
            PreparedStatement preparedStatement = connection.prepareStatement(
        			"SELECT * FROM public.policy p WHERE p.id=?;");
            
            preparedStatement.setLong(1, policyId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
            	LOGGER.info("There is no policy with the ID {}.", policyId);
            	return Optional.empty();
            }

            LocalDate startDate = resultSet.getDate("startdate").toLocalDate();
            LocalDate endDate = resultSet.getDate("enddate").toLocalDate();
            Long userId = resultSet.getLong("userid");
            Double premium = resultSet.getDouble("premium");
            
            Policy.Builder builder = Policy.Builder.newBuilder();
            builder.setId(policyId);
            builder.setStartDate(startDate);
            builder.setEndDate(endDate);
            builder.setUserId(userId);
            builder.setPremium(premium);
            
            policy = builder.build();
            
            LOGGER.info("Got Policy with ID {}: UserID={}, StartDate={}, EndDate={}, Premium={}.", 
            		policyId, userId, startDate, endDate, premium);

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

        return Optional.ofNullable(policy);
    }

    @Override
    public Policy insert(Policy policy) throws InsuranceDException {
    	Connection connection = null;
        Boolean previousAutoCommit = null;
        try {
            connection = ConnectionManager.getConnection();
            previousAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            long policyId = insertPolicy(connection, policy);
            
            LOGGER.info("Inserted Policy ID is {}", policyId);

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
        return null;
    }
    
    // TODO: Not Implemented
	@Override
    public Policy update(Policy policy) throws InsuranceDException {
		return null;
    }

	// TODO: Not Implemented
    @Override
    public List<Policy> getPoliciesFromUser(long userId) throws InsuranceDException {
        return Collections.emptyList();
    }

    // TODO: Not Implemented
    @Override
    public Optional<Policy> getExtendedPolicyInformation(long policyId) throws InsuranceDException {
        return null;
    }
    
    private long insertPolicy(Connection connection, Policy policy) throws SQLException, InsuranceDException {
    	PreparedStatement preparedStatement = connection.prepareStatement(
    			"INSERT INTO public.policy(startdate, enddate, premium, userid) " + 
    			"VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
        
    	preparedStatement.setDate(1, Date.valueOf(policy.getStartDate()));
    	preparedStatement.setDate(2, Date.valueOf(policy.getEndDate()));
    	preparedStatement.setDouble(3, policy.getPremium());
    	preparedStatement.setLong(4, policy.getUserId());

        int generatedKeys = preparedStatement.executeUpdate();
        if (generatedKeys == 0) {
            throw new InsuranceDException("Error inserting Policy into the DB.");
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
