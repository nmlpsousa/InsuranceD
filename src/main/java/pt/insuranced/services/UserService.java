package pt.insuranced.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    public String getClientDetails(String jsonInput) throws InsuranceDException {
        try {
            Client client = OBJECT_MAPPER.readValue(jsonInput, Client.class);
            int userId = client.getId();

            UserDao userDao = new UserDaoImpl();
            Optional<AbstractUser> retrievedUserOptional = userDao.get(userId);
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

    /*
    public String insertClient(String jsonInput) throws InsuranceDException {
        try {
            Client newClient = OBJECT_MAPPER.readValue(jsonInput, Client.class);

        } catch (IOException exception) {

        }
    }
    */

    private Boolean performValidations(Client client, Predicate<Client> predicate) {
        return predicate.test(client);
    }
}
