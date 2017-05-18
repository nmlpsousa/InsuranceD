package pt.insuranced.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.StringUtils;
import pt.insuranced.models.AbstractUser;
import pt.insuranced.models.Client;
import pt.insuranced.persistence.dao.UserDaoImpl;
import pt.insuranced.persistence.dao.sdk.interfaces.UserDao;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;

public class UserService {
    private static final ObjectMapper OBJECT_MAPPER;

    private UserDao userDao;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    public UserService() {
        this.userDao = new UserDaoImpl();
    }

    public String getClientDetails(String jsonInput) throws InsuranceDException {
        try {
            Client client = OBJECT_MAPPER.readValue(jsonInput, Client.class);
            Long userId = client.getId();

            Optional<AbstractUser> retrievedUserOptional = this.userDao.get(userId);
            if (!retrievedUserOptional.isPresent()) {
                throw new InsuranceDException("The client does not exist.");
            }
            AbstractUser retrievedUser = retrievedUserOptional.get();
            if (!(retrievedUser instanceof Client)) {
                throw new InsuranceDException("The retrieved user is not of the Client type.");
            }
            Client clientWithDetails = (Client) retrievedUser;
            return OBJECT_MAPPER.writeValueAsString(clientWithDetails);
        } catch (IOException e) {
            throw new InsuranceDException("An error occurred while trying to retrieve the client details.", e);
        }
    }

    public String insertClient(String jsonInput) throws InsuranceDException {
        try {
            Client newClient = OBJECT_MAPPER.readValue(jsonInput, Client.class);
            Boolean clientIsValid = isClientValid(newClient, client -> StringUtils.isNotEmpty(client.getUsername()) && client.getPassword() != null);
            if (!clientIsValid) {
                throw new InsuranceDException("The client is invalid.");
            }
            Client insertedClient = (Client) this.userDao.insert(newClient);
            return OBJECT_MAPPER.writeValueAsString(insertedClient);
        } catch (IOException exception) {
            throw new InsuranceDException("An error occurred while trying to insert the client.", exception);
        }
    }

    private static Boolean isClientValid(Client client, Predicate<Client> predicate) {
        return predicate.test(client);
    }
}
