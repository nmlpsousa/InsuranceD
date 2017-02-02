package pt.insuranced.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import pt.insuranced.models.AbstractUser;
import pt.insuranced.models.Client;
import pt.insuranced.persistence.dao.UserDaoImpl;
import pt.insuranced.persistence.dao.sdk.interfaces.UserDao;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.io.IOException;
import java.util.Optional;

public class UserService {
    public String getClientDetails(String jsonInput) throws InsuranceDException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JSR310Module());
        try {
            Client client = objectMapper.readValue(jsonInput, Client.class);
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
            return objectMapper.writeValueAsString(clientWithDetails);
        } catch (IOException e) {
            throw new InsuranceDException("An error occurred while trying to retrieve the client details.", e);
        }
    }
}
