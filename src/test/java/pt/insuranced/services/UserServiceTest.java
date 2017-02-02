package pt.insuranced.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mockit.Mock;
import mockit.MockUp;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import pt.insuranced.models.AbstractUser;
import pt.insuranced.models.Client;
import pt.insuranced.models.Password;
import pt.insuranced.models.Policy;
import pt.insuranced.persistence.dao.PolicyDaoImpl;
import pt.insuranced.persistence.dao.UserDaoImpl;
import pt.insuranced.sdk.enums.UserStatusEnum;
import pt.insuranced.sdk.enums.UserTypeEnum;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

public class UserServiceTest {
    private static Client exampleClient;

    @Before
    public void setUp() {
        exampleClient = Client.Builder.newBuilder()
                .setId(0)
                .setUsername("nmlpsousa")
                .setPassword(new Password(0, "pass"))
                .setLastPasswordChangeDate(LocalDate.of(2017, 1, 30))
                .setUserStatus(UserStatusEnum.PENDING)
                .setUserType(UserTypeEnum.CLIENT)
                .build();
    }

    @Test(expected = InsuranceDException.class)
    public void testGetClientDetailsNonExistent() throws InsuranceDException {
        new MockUp<UserDaoImpl>() {
            @Mock
            public Optional<AbstractUser> get(int userId) {
                return Optional.empty();
            }
        };
        String jsonInput = "{\"id\":\"0\"}";
        UserService userService = new UserService();
        String response = userService.getClientDetails(jsonInput);

        // If the method does not throw an exception, it means the test failed
        fail();
    }

    @Test
    public void testGetClientDetailsExistent() {
        new MockUp<UserDaoImpl>() {
            @Mock
            public Optional<AbstractUser> get(int userId) {
                return Optional.of(exampleClient);
            }
        };

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String jsonInput = "{\"id\":\"0\"}";
            UserService userService = new UserService();
            String response = userService.getClientDetails(jsonInput);
            Client retrievedClient = objectMapper.readValue(response, Client.class);

            assertEquals(0, retrievedClient.getId());
            assertNull(retrievedClient.getPassword());
        } catch (InsuranceDException | IOException exception) {
            fail(exception.getMessage());
        }
    }

    @Test
    @Ignore
    public void testInsertNewClientSuccess() {
        new MockUp<UserDaoImpl>() {
            @Mock
            public AbstractUser insert(AbstractUser user) throws InsuranceDException {
                return exampleClient;
            }
        };

        String jsonInput = "{\"username\":\"nmlpsousa\",\"personalIdentification\":null,\"oldPasswords\":null,\"lastPasswordChangeDate\":[2017,1,30],\"userType\":\"CLIENT\","
                + "\"userStatus\":\"PENDING\",\"policyList\":null}";
        UserService userService = new UserService();
        String response;
    }
}