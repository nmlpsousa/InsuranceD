package pt.insuranced.persistence.dao;

import org.apache.commons.lang3.exception.ExceptionUtils;
import pt.insuranced.models.Client;
import pt.insuranced.models.Password;
import pt.insuranced.persistence.connection.ConnectionManager;
import pt.insuranced.persistence.dao.sdk.interfaces.ClientDao;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class ClientDaoImpl implements ClientDao {

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

        return null;
    }

    @Override
    public Client update(Client client) throws InsuranceDException {
        return null;
    }
}
