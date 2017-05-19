package pt.insuranced.persistence.dao;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import pt.insuranced.models.Address;
import pt.insuranced.models.Client;
import pt.insuranced.models.Password;
import pt.insuranced.models.PersonalIdentification;
import pt.insuranced.models.PhoneNumber;
import pt.insuranced.persistence.dao.factory.ClientDaoFactory;
import pt.insuranced.persistence.dao.sdk.interfaces.ClientDao;
import pt.insuranced.sdk.enums.CountryEnum;
import pt.insuranced.sdk.enums.UserStatusEnum;
import pt.insuranced.sdk.enums.UserTypeEnum;

import java.time.LocalDate;
import java.util.Optional;

public class ClientDaoImplTest {

    @Test
    public void testGet() throws Exception {
        ClientDao clientDao = new ClientDaoImpl();
        Optional<Client> clientOptional = clientDao.get(1L);
        clientOptional.ifPresent(System.out::println);
    }

    @Test
    public void testInsert() throws Exception {
        Client client = new Client();
        client.setUsername("guirodrigues");

        PersonalIdentification personalIdentification = new PersonalIdentification();
        Address address = new Address();
        address.setAddressLine1("Av. da Boavista");
        address.setCity("Porto");
        address.setCountry(CountryEnum.PT);

        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setPrefix("+351");
        phoneNumber.setNumber(123456789);

        personalIdentification.setAddress(address);
        personalIdentification.setPhoneNumber(phoneNumber);
        personalIdentification.setDateOfBirth(LocalDate.now());
        personalIdentification.setFirstName("Guilherme");
        personalIdentification.setLastName("Rodrigues");
        personalIdentification.setEmail("guirodrigues@deloitte.pt");

        Password password = new Password();
        password.setHashedPassword("password");
        client.setPassword(password);

        client.setUserType(UserTypeEnum.CLIENT);
        client.setUserStatus(UserStatusEnum.ACTIVE);
        client.setPersonalIdentification(personalIdentification);

        ClientDao clientDao = new ClientDaoImpl();
        clientDao.insert(client);
    }

    @Test
    @Ignore
    public void testUpdate() throws Exception {
        ClientDao clientDao = new ClientDaoFactory().getDao("postgres");

        Optional<Client> clientOptional = clientDao.get(1L);
        if (!clientOptional.isPresent()) {
            Assert.fail();
        }
        Client client = clientOptional.get();
        client.setUserStatus(UserStatusEnum.INACTIVE);

        clientDao.update(client);

        Optional<Client> updatedClientOpt = clientDao.get(1L);
        Assert.assertEquals(UserStatusEnum.INACTIVE, updatedClientOpt.get().getUserStatus());
    }
}