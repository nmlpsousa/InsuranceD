package pt.insuranced.persistence.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.insuranced.models.Claim;
import pt.insuranced.models.ReserveLine;
import pt.insuranced.persistence.connection.ConnectionManager;
import pt.insuranced.persistence.dao.sdk.interfaces.ClaimDao;
import pt.insuranced.sdk.enums.ClaimStatusEnum;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClaimDaoImpl implements ClaimDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClaimDaoImpl.class);

    // TODO: Not Implemented
    @Override
    public List<Claim> getLastClaimsFromUser(int userId, int numberOfClaims) {
        return new ArrayList<>(0);
    }

    @Override
    public Optional<Claim> get(long claimId) throws InsuranceDException {
        Connection connection = null;
        Boolean previousAutoCommit = null;
        Claim claim = null;
        try {
            connection = ConnectionManager.getConnection();
            previousAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM public.claim c WHERE c.id=?;");

            preparedStatement.setLong(1, claimId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                LOGGER.info("There is no claim with the ID {}.", claimId);
                return Optional.empty();
            }

            String description = resultSet.getString("description");
            Long coverableId = resultSet.getLong("coverableid");
            Long claimStatusId = resultSet.getLong("claimstatusid");
            Long reserveLineId = resultSet.getLong("reservelineid");
            LocalDate incidentDate = resultSet.getDate("incidentdate").toLocalDate();

            Claim.Builder builder = Claim.Builder.newBuilder();
            builder.setId(claimId);
            builder.setDescription(description);
            builder.setStatus(ClaimStatusEnum.getClaimStatusByCode(claimStatusId));
            builder.setIncidentDate(incidentDate);
            builder.setReserveLine(null);
            builder.setCoverableId(coverableId);

            claim = builder.build();

            LOGGER.info("Got Claim with ID {}: Description={}, CoverableID={}, ClaimStatusID={}, " +
                            "ReserveLineID={}, IncidentDate={}.",
                    claimId, description, coverableId, claimStatusId, reserveLineId, incidentDate);

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

        return Optional.ofNullable(claim);
    }

    @Override
    public Claim insert(Claim claim) throws InsuranceDException {
        Connection connection = null;
        Boolean previousAutoCommit = null;
        try {
            connection = ConnectionManager.getConnection();
            previousAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            prepareAndExecuteInsertStatements(claim, connection);

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
    public Claim update(Claim claim) throws InsuranceDException {
        return null;
    }

    private void prepareAndExecuteInsertStatements(Claim claim, Connection connection) throws SQLException, InsuranceDException {

        ReserveLine reserveLine = claim.getReserveLine();

        long reserveLineId = insertReserveLine(connection, reserveLine);
        long claimId = insertClaim(connection, claim, reserveLineId);

        LOGGER.info("Inserted ReserveLine ID is {}", reserveLineId);
        LOGGER.info("Inserted Claim ID is {}", claimId);
    }

    private long insertReserveLine(Connection connection, ReserveLine reserveLine) throws SQLException, InsuranceDException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO public.reserveline" +
                "(description, lim, usedfunds) VALUES(?, ?, ?);", Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, reserveLine.getDescription());
        statement.setDouble(2, reserveLine.getLimit());
        statement.setDouble(3, reserveLine.getUsedFunds());

        int generatedKeys = statement.executeUpdate();
        if (generatedKeys == 0) {
            throw new InsuranceDException("Error inserting Address into the DB.");
        }

        statement.getGeneratedKeys().next();
        return statement.getGeneratedKeys().getLong("id");
    }

    private long insertClaim(Connection connection, Claim claim, long reserveLineId) throws SQLException, InsuranceDException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO public.claim" +
                        "(coverableid, description, incidentdate, claimstatusid, reservelineid) VALUES(?, ?, ?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setLong(1, claim.getCoverableId());
        preparedStatement.setString(2, claim.getDescription());
        preparedStatement.setDate(3, Date.valueOf(claim.getIncidentDate()));
        preparedStatement.setLong(4, claim.getStatus().getCode());
        preparedStatement.setLong(5, reserveLineId);

        int generatedKeys = preparedStatement.executeUpdate();
        if (generatedKeys == 0) {
            throw new InsuranceDException("Error inserting Claim into the DB.");
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