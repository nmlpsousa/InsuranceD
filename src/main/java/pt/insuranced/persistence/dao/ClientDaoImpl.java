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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
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
        ResultSet resultSet = null;
        Connection connection = null;
        Boolean previousAutoCommit = null;
        try {
            connection = ConnectionManager.getConnection();
            previousAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();

            PersonalIdentification personalIdentification = client.getPersonalIdentification();
            Address address = personalIdentification.getAddress();
            PhoneNumber phoneNumber = personalIdentification.getPhoneNumber();

            Integer countryCode = address.getCountry() == null ? null : address.getCountry().getCode();

            String addressInsertSql = MessageFormat.format("INSERT INTO public.\"Address\"( \"addressLine1\", \"addressLine2\", city, \"postalCode\", \"countryId\") VALUES({0}, "
                    + "{1}, {2}, {3}, "
                    + "{4});", getOrNull(address.getAddressLine1()), getOrNull(address.getAddressLine2()), getOrNull(address.getCity()), getOrNull(address.getPostalCode()),
                    getOrNull(countryCode));

            //String query = "insert into public.\"Client\"(username) values ('" + client.getUsername() + "');";
            int generatedKeys = statement.executeUpdate(addressInsertSql, Statement.RETURN_GENERATED_KEYS);
            if (generatedKeys == 0) {
                throw new InsuranceDException("Error inserting Address into the DB.");
            }

            statement.getGeneratedKeys().next();
            long addressId = statement.getGeneratedKeys().getLong("id");
            LOGGER.info("Inserted Address ID is {}", addressId);

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

    private static String getOrNull(Object object) {
        return object == null ? "NULL" : "'" + object + "'";
    }

    @Override
    public Client update(Client client) throws InsuranceDException {
        return null;
    }
}
