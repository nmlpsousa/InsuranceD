package pt.insuranced.persistence.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.insuranced.models.Address;
import pt.insuranced.models.Client;
import pt.insuranced.models.Password;
import pt.insuranced.models.PersonalIdentification;
import pt.insuranced.models.PhoneNumber;
import pt.insuranced.persistence.connection.ConnectionManager;
import pt.insuranced.persistence.dao.sdk.interfaces.ClientDao;
import pt.insuranced.sdk.enums.CountryEnum;
import pt.insuranced.sdk.enums.UserStatusEnum;
import pt.insuranced.sdk.enums.UserTypeEnum;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ClientDaoImpl implements ClientDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientDaoImpl.class);

    @Override
    public Optional<Client> get(long userId) throws InsuranceDException {
        String query =
                "SELECT usertype.id AS usertypeid, userstatus.id AS userstatusid, client.username, client.lastpasswordchangedate, client.id AS clientid, password.id AS "
                        + "passwordid, password.isactive, password.password, personalidentification.id AS personalid, personalidentification.fiscalnumber, personalidentification"
                        + ".identificationno, personalidentification.dateofbirth, personalidentification.lastname, personalidentification.firstname, address.addressline1, "
                        + "address.addressline2, address.city, address.postalcode, address.id AS addressid, phonenumber.id AS phonenumberid, phonenumber.pref, phonenumber.num, "
                        + "countries.id AS countryid FROM public.client, public.usertype, public.userstatus, public.personalidentification, public.address, public.phonenumber, "
                        + "public.countries, public.password WHERE client.personalid = personalidentification.id AND usertype.id = client.typeid AND userstatus.id = client"
                        + ".statusid AND personalidentification.addressid = address.id AND phonenumber.id = personalidentification.phonenumberid AND countries.id = address"
                        + ".countryid AND password.userid = client.id AND password.isactive = TRUE AND client.id = ?; ";
        ResultSet resultSet = null;

        try (Connection connection = ConnectionManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                // No user found
                return Optional.empty();
            }

            PreparedStatement passwordQueryStatement =
                    connection.prepareStatement(
                            "SELECT password.password, password.isactive, password.id AS passwordid, client.id AS clientid FROM public.client, public.password WHERE password"
                                    + ".userid = client.id AND client.id = ?; ");
            passwordQueryStatement.setLong(1, userId);

            ResultSet passwordResults = passwordQueryStatement.executeQuery();
            List<Password> oldPasswords = new LinkedList<>();
            Password activePassword = null;
            while (passwordResults.next()) {
                Boolean isActive = passwordResults.getBoolean("isactive");
                Password password = new Password(resultSet.getLong("passwordid"), resultSet.getString("password"));
                if (isActive) {
                    activePassword = password;
                } else {
                    oldPasswords.add(password);
                }
            }

            String username = resultSet.getString("username");
            LocalDate lastPasswordChangeDate = resultSet.getDate("lastpasswordchangedate").toLocalDate();
            String firstName = resultSet.getString("firstname");
            String lastName = resultSet.getString("lastname");
            LocalDate dateOfBirth = resultSet.getDate("dateofbirth").toLocalDate();
            String identificationNo = resultSet.getString("identificationno");
            String fiscalNo = resultSet.getString("fiscalnumber");
            String addressLine1 = resultSet.getString("addressline1");
            String addressLine2 = resultSet.getString("addressline2");
            String city = resultSet.getString("city");
            String postalCode = resultSet.getString("postalcode");
            String phonePrefix = resultSet.getString("pref");
            Integer phoneNumber = resultSet.getInt("num");

            Long clientId = resultSet.getLong("clientid");
            Long personalIdentificationid = resultSet.getLong("personalid");
            Long addressId = resultSet.getLong("addressid");
            Long phoneNumberId = resultSet.getLong("phonenumberid");
            Long countryId = resultSet.getLong("countryid");
            Long userTypeId = resultSet.getLong("usertypeid");
            Long userStatusId = resultSet.getLong("userstatusid");

            UserTypeEnum userTypeEnum = UserTypeEnum.getTypeByCode(userTypeId);
            UserStatusEnum userStatusEnum = UserStatusEnum.getStatusByCode(userStatusId);
            CountryEnum countryEnum = CountryEnum.getCountryByCode(countryId);

            Address address = new Address(addressId, addressLine1, addressLine2, city, postalCode, countryEnum);
            PhoneNumber phoneNumber1 = new PhoneNumber(phoneNumberId, phonePrefix, phoneNumber);
            // TODO Email is missing
            PersonalIdentification personalIdentification = PersonalIdentification.Builder.newBuilder(personalIdentificationid)
                    .setIdentificationNumber(identificationNo)
                    .setAddress(address)
                    .setDateOfBirth(dateOfBirth)
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setFiscalNumber(fiscalNo)
                    .setPhoneNumber(phoneNumber1)
                    .build();

            Client client1 = new Client(clientId, username, activePassword, personalIdentification, oldPasswords, lastPasswordChangeDate, userTypeEnum, userStatusEnum, null);
            return Optional.of(client1);

        } catch (SQLException e) {
            throw new InsuranceDException("Error retrieving client", e);
        } finally {
            if (resultSet != null) {
                closeResultSet(resultSet);
            }
        }
    }

    private static void closeResultSet(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (SQLException e) {
            LOGGER.error("Error while attempting to close ResultSet", e);
        }
    }

    @Override
    public Client insert(Client client) throws InsuranceDException {
        Connection connection = null;
        Boolean previousAutoCommit = null;
        try {
            connection = ConnectionManager.getConnection();
            previousAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            prepareAndExecuteInsertStatements(client, connection);

            connection.commit();
        } catch (SQLException e) {
            rollbackConnection(connection);
            throw new InsuranceDException("Error connecting with the database.", e);
        } finally {
            if (previousAutoCommit != null) {
                restoreAutoCommit(connection, previousAutoCommit);
            }
            closeConnection(connection);
        }
        return null;
    }

    private void prepareAndExecuteInsertStatements(Client client, Connection connection) throws SQLException, InsuranceDException {
        PersonalIdentification personalIdentification = client.getPersonalIdentification();
        PhoneNumber phoneNumber = personalIdentification.getPhoneNumber();
        Address address = personalIdentification.getAddress();
        Password password = client.getPassword();

        long addressId = insertAddress(connection, address);
        long phoneNumberId = insertPhoneNumber(connection, phoneNumber);
        long personalIdentificationId = insertPersonalIdentification(connection, personalIdentification, addressId, phoneNumberId);
        long clientId = insertClient(connection, client, personalIdentificationId);
        long passwordId = insertPassword(connection, password, clientId);

        LOGGER.debug("Inserted Address ID is {}", addressId);
        LOGGER.debug("Inserted Phone Number ID is {}", phoneNumberId);
        LOGGER.debug("Inserted Personal Identification ID is {}", personalIdentificationId);
        LOGGER.debug("Inserted Client ID is {}", clientId);
        LOGGER.debug("Inserted Password ID is {}", passwordId);
    }

    private long insertPassword(Connection connection, Password password, long clientId) throws SQLException, InsuranceDException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("INSERT INTO public.password( password, userid, isactive) VALUES(?, ?, ?);  ", Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, password.getHashedPassword());
        preparedStatement.setLong(2, clientId);
        preparedStatement.setBoolean(3, true);

        int generatedKeys = preparedStatement.executeUpdate();
        if (generatedKeys == 0) {
            throw new InsuranceDException("Error inserting Password into the DB.");
        }

        preparedStatement.getGeneratedKeys().next();
        return preparedStatement.getGeneratedKeys().getLong("id");

    }

    private long insertClient(Connection connection, Client client, long personalIdentificationId) throws SQLException, InsuranceDException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO public.client( personalid, typeid, username, lastpasswordchangedate, statusid) VALUES(?, ?, ?, ?, ?); ",
                        Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setLong(1, personalIdentificationId);
        preparedStatement.setLong(2, client.getUserType().getCode());
        preparedStatement.setString(3, client.getUsername());
        preparedStatement.setDate(4, Date.valueOf(LocalDate.now()));
        preparedStatement.setLong(5, client.getUserStatus().getCode());

        int generatedKeys = preparedStatement.executeUpdate();
        if (generatedKeys == 0) {
            throw new InsuranceDException("Error inserting Client into the DB.");
        }

        preparedStatement.getGeneratedKeys().next();
        return preparedStatement.getGeneratedKeys().getLong("id");
    }

    private long insertPhoneNumber(Connection connection, PhoneNumber phoneNumber) throws SQLException, InsuranceDException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO public.phonenumber( pref, num) VALUES(?, ?); ", Statement.RETURN_GENERATED_KEYS);
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
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO public.personalidentification( firstname, lastname, dateofbirth, addressid, identificationno, fiscalnumber, phonenumberid) VALUES(?, ?, ?, ?,"
                        + " ?, ?, ?); ",
                Statement.RETURN_GENERATED_KEYS);

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
        PreparedStatement statement = connection
                .prepareStatement("INSERT INTO public.address( addressline1, addressline2, city, postalcode, countryid) VALUES(?, ?, ?, ?, ?); ",
                        Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, address.getAddressLine1());
        statement.setString(2, address.getAddressLine2());
        statement.setString(3, address.getCity());
        statement.setString(4, address.getPostalCode());
        statement.setLong(5, address.getCountry().getCode());

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

    private static void restoreAutoCommit(Connection connection, Boolean previousAutoCommit) {
        try {
            connection.setAutoCommit(previousAutoCommit);
        } catch (SQLException e) {
            LOGGER.error("Error closing connection to the DB.", e);
        }
    }

    @Override
    public Client update(Client client) throws InsuranceDException {
        try (Connection connection = ConnectionManager.getConnection();) {
            PreparedStatement updateStatement =
                    connection.prepareStatement("UPDATE public.client SET typeid=?, username=?, statusid=? WHERE client.id = ?; ");
            updateStatement.setLong(1, client.getUserType().getCode());
            updateStatement.setString(2, client.getUsername());
            updateStatement.setLong(3, client.getUserStatus().getCode());
            updateStatement.setLong(4, client.getId());

            updateStatement.executeUpdate();

        } catch (SQLException e) {
            throw new InsuranceDException("Error connecting with the database.", e);
        }
        return client;
    }

    @Override
    public void bulkInsert(Collection<Client> clients) throws InsuranceDException {
        Connection connection = null;
        Boolean previousAutoCommit = null;
        try {
            connection = ConnectionManager.getConnection();
            previousAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            int i = 0;
            for (Client client : clients) {
                LOGGER.info("Inserting Client record {} of {}", ++i, clients.size());
                prepareAndExecuteInsertStatements(client, connection);
                connection.commit();
            }
        } catch (SQLException e) {
            rollbackConnection(connection);
            throw new InsuranceDException("Error connecting with the database.", e);
        } finally {
            if (previousAutoCommit != null) {
                restoreAutoCommit(connection, previousAutoCommit);
            }
            closeConnection(connection);
        }
    }
}
