package pt.insuranced.persistence.dao;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.insuranced.models.Address;
import pt.insuranced.models.Client;
import pt.insuranced.models.Password;
import pt.insuranced.models.PersonalIdentification;
import pt.insuranced.models.PhoneNumber;
import pt.insuranced.persistence.connection.ConnectionManager;
import pt.insuranced.persistence.dao.sdk.interfaces.ClientDao;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class ClientDaoImpl implements ClientDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientDaoImpl.class);

    @Override
    public Optional<Client> get(int userId) throws InsuranceDException {
        String query = "select * from public.\"Users\" where \"Users\".id = " + userId + ';';
        Client client = null;

        try (Connection connection = ConnectionManager.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            if (!resultSet.next()) {
                // No user found
                return Optional.empty();
            }

            System.out.println("Id: " + resultSet.getString("id") + ", Username: " + resultSet.getString("username"));
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            Password password1 = new Password(0, password);
            int personalId = resultSet.getInt("personalId");
            int typeId = resultSet.getInt("typeId");
            client = new Client(id, username, password1, null, null, null, null, null, null);
        } catch (SQLException e) {
            System.out.println("Exception: " + e.getMessage());
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
        return Optional.ofNullable(client);
    }

    @Override
    public Client insert(Client client) throws InsuranceDException {
        Connection connection = null;
        Boolean previousAutoCommit = null;
        try {
            connection = ConnectionManager.getConnection();
            previousAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            PersonalIdentification personalIdentification = client.getPersonalIdentification();
            PhoneNumber phoneNumber = personalIdentification.getPhoneNumber();
            Address address = personalIdentification.getAddress();

            long addressId = insertAddress(connection, address);
            long phoneNumberId = insertPhoneNumber(connection, phoneNumber);
            long personalIdentificationId = insertPersonalIdentification(connection, personalIdentification, addressId, phoneNumberId);

            LOGGER.info("Inserted Address ID is {}", addressId);
            LOGGER.info("Inserted Phone Number ID is {}", phoneNumberId);
            LOGGER.info("Inserted Personal Identification ID is {}", personalIdentificationId);

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

    private long insertPhoneNumber(Connection connection, PhoneNumber phoneNumber) throws SQLException, InsuranceDException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO public.\"PhoneNumber\"( prefix, \"number\") VALUES(?, ?);", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, phoneNumber.getPrefix());
        preparedStatement.setInt(2, phoneNumber.getNumber());

        int generatedKeys = preparedStatement.executeUpdate();
        if (generatedKeys == 0) {
            throw new InsuranceDException("Error inserting Phone Number into the DB.");
        }

        preparedStatement.getGeneratedKeys().next();
        return preparedStatement.getGeneratedKeys().getLong("id");
    }

    private long insertPersonalIdentification(Connection connection, PersonalIdentification personalIdentification, long addressId, long phoneNumberId) throws SQLException,
            InsuranceDException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO public.\"PersonalIdentification\"( \"firstName\", \"lastName\", \"dateOfBirth\", \"addressId\", "
                + "\"identificationNo\", \"fiscalNumber\", \"phoneNumberId\") VALUES(?, ?, ?, ?, ?, ?, ?); ", Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, personalIdentification.getFirstName());
        statement.setString(2, personalIdentification.getLastName());
        statement.setDate(3, Date.valueOf(personalIdentification.getDateOfBirth()));
        statement.setLong(4, addressId);
        statement.setString(5, personalIdentification.getIdentificationNumber());
        statement.setString(6, personalIdentification.getFiscalNumber());
        statement.setLong(7, phoneNumberId);

        int generatedKeys = statement.executeUpdate();
        if (generatedKeys == 0) {
            throw new InsuranceDException("Error inserting Personal Identification into the DB.");
        }

        statement.getGeneratedKeys().next();
        return statement.getGeneratedKeys().getLong("id");
    }

    private long insertAddress(Connection connection, Address address) throws SQLException, InsuranceDException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO public.\"Address\"( \"addressLine1\", \"addressLine2\", city, \"postalCode\", \"countryId\") "
                + "VALUES (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, address.getAddressLine1());
        statement.setString(2, address.getAddressLine2());
        statement.setString(3, address.getCity());
        statement.setString(4, address.getPostalCode());
        statement.setInt(5, address.getCountry().getCode());

        int generatedKeys = statement.executeUpdate();
        if (generatedKeys == 0) {
            throw new InsuranceDException("Error inserting Address into the DB.");
        }

        statement.getGeneratedKeys().next();
        return statement.getGeneratedKeys().getLong("id");
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

    @Override
    public Client update(Client client) throws InsuranceDException {
        return null;
    }
}
