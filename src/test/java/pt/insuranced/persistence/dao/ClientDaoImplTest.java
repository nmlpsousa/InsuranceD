package pt.insuranced.persistence.dao;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.insuranced.models.Client;
import pt.insuranced.persistence.dao.sdk.interfaces.ClientDao;

import java.util.Optional;

import static org.junit.Assert.fail;

public class ClientDaoImplTest {
    @Test
    public void get() throws Exception {
        ClientDao clientDao = new ClientDaoImpl();
        Optional<Client> clientOptional = clientDao.get(1);
        Client client = null;
        if (!clientOptional.isPresent()) {
            fail();
        }

        client = clientOptional.get();
        System.out.println(client);

        Assert.assertEquals("abc", client.getUsername());
    }

}