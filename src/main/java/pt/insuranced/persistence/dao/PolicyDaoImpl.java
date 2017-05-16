package pt.insuranced.persistence.dao;

import pt.insuranced.models.Address;
import pt.insuranced.models.Client;
import pt.insuranced.models.Password;
import pt.insuranced.models.PersonalIdentification;
import pt.insuranced.models.PhoneNumber;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PolicyDaoImpl implements PolicyDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PolicyDaoImpl.class);

    @Override
    public Optional<Policy> get(int policyId) throws InsuranceDException {
    	
    	String query = "select * from public.\"Policy\" p where p.id = " + policyId + ';';
        Policy policy = null;

        try (Connection connection = ConnectionManager.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            if (!resultSet.next()) {
                // No policy found
                return Optional.empty();
            }

            System.out.println("Id: " + resultSet.getString("id") + ", Username: " + resultSet.getString("username"));
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            Password password1 = new Password(0, password);
            int personalId = resultSet.getInt("personalId");
            int typeId = resultSet.getInt("typeId");
            //client = new Client(id, username, password1, null, null, null, null, null, null);
        } catch (SQLException e) {
            System.out.println("Exception: " + e.getMessage());
            System.out.println(ExceptionUtils.getStackTrace(e));
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
    public List<Policy> getPoliciesFromUser(int userId) throws InsuranceDException {
        return Collections.emptyList();
    }

    // TODO: Not Implemented
    @Override
    public Optional<Policy> getExtendedPolicyInformation(int policyId) throws InsuranceDException {
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
