package pt.insuranced.persistence.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.insuranced.models.Coverage;
import pt.insuranced.persistence.connection.ConnectionManager;
import pt.insuranced.persistence.dao.sdk.interfaces.CoverageDao;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class CoverageDaoImpl implements CoverageDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoverageDaoImpl.class);

    @Override
    public Optional<Coverage> get(long coverageId) throws InsuranceDException {
        Connection connection = null;
        Boolean previousAutoCommit = null;
        Coverage coverage = null;
        try {
            connection = ConnectionManager.getConnection();
            previousAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM public.coverage c WHERE c.id=?;");

            preparedStatement.setLong(1, coverageId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                LOGGER.info("There is no coverage with the ID {}.", coverageId);
                return Optional.empty();
            }

            String description = resultSet.getString("description");
            Long coverableId = resultSet.getLong("coverableid");
            Double limit = resultSet.getDouble("lim");
            Double premium = resultSet.getDouble("premium");

            Coverage.Builder builder = Coverage.Builder.newBuilder();
            builder.setId(coverageId);
            builder.setCoverableId(coverableId);
            builder.setDescription(description);
            builder.setLimit(limit);
            builder.setPremium(premium);

            coverage = builder.build();

            LOGGER.info("Got Coverage with ID {}: CoverableID={}, Description={}, Limit={}, Premium={}.",
                    coverageId, coverableId, description, limit, premium);

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

        return Optional.ofNullable(coverage);
    }

    @Override
    public Coverage insert(Coverage coverage) throws InsuranceDException {
        Connection connection = null;
        Boolean previousAutoCommit = null;
        try {
            connection = ConnectionManager.getConnection();
            previousAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            long coverageId = insertCoverage(connection, coverage);

            LOGGER.info("Inserted Coverage ID is {}", coverageId);

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
        return coverage;
    }

    @Override
    public Coverage update(Coverage coverage) throws InsuranceDException {
        Connection connection = null;
        Boolean previousAutoCommit = null;
        try {
            connection = ConnectionManager.getConnection();
            previousAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            long coverageId = updateCoverage(connection, coverage);

            LOGGER.info("Successfully updated Coverage with ID {}", coverageId);

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
        return coverage;
    }

    private long insertCoverage(Connection connection, Coverage coverage) throws SQLException, InsuranceDException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO public.coverage(coverableid, lim, premium, description) " +
                        "VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setLong(1, coverage.getCoverableId());
        preparedStatement.setDouble(2, coverage.getLimit());
        preparedStatement.setDouble(3, coverage.getPremium());
        preparedStatement.setString(4, coverage.getDescription());

        int generatedKeys = preparedStatement.executeUpdate();
        if (generatedKeys == 0) {
            throw new InsuranceDException("Error inserting Coverage into the DB.");
        }

        preparedStatement.getGeneratedKeys().next();
        return preparedStatement.getGeneratedKeys().getLong("id");
    }

    private long updateCoverage(Connection connection, Coverage coverage) throws SQLException, InsuranceDException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE public.coverage SET coverableid=?, lim=?, premium=?, description=? " +
                        "WHERE id=?;", Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setLong(1, coverage.getCoverableId());
        preparedStatement.setDouble(2, coverage.getLimit());
        preparedStatement.setDouble(3, coverage.getPremium());
        preparedStatement.setString(4, coverage.getDescription());
        preparedStatement.setLong(5, coverage.getId());

        int generatedKeys = preparedStatement.executeUpdate();
        if (generatedKeys == 0) {
            throw new InsuranceDException("Error updating Coverage into the DB.");
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
