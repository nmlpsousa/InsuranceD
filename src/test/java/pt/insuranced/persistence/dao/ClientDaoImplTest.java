package pt.insuranced.persistence.dao;

import org.junit.Ignore;
import org.junit.Test;
import pt.insuranced.models.Address;
import pt.insuranced.models.Client;
import pt.insuranced.models.Password;
import pt.insuranced.models.PersonalIdentification;
import pt.insuranced.models.PhoneNumber;
import pt.insuranced.persistence.dao.sdk.interfaces.ClientDao;
import pt.insuranced.sdk.enums.CountryEnum;
import pt.insuranced.sdk.enums.UserStatusEnum;
import pt.insuranced.sdk.enums.UserTypeEnum;

import java.time.LocalDate;
import java.util.Optional;

public class ClientDaoImplTest {

    @Test
    @Ignore
    public void testGet() throws Exception {
        ClientDao clientDao = new ClientDaoImpl();
        Optional<Client> clientOptional = clientDao.get(1L);
        clientOptional.ifPresent(System.out::println);
    }

    @Test
    @Ignore
    public void testInsert() throws Exception {
        Client client = new Client();
        client.setUsername("abc");

        PersonalIdentification personalIdentification = new PersonalIdentification();
        Address address = new Address();
        address.setAddressLine1("diaojdpoia");
        address.setCity("Lisboa");
        address.setCountry(CountryEnum.PT);

        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setPrefix("+351");
        phoneNumber.setNumber(123456789);

        personalIdentification.setAddress(address);
        personalIdentification.setPhoneNumber(phoneNumber);
        personalIdentification.setDateOfBirth(LocalDate.now());
        personalIdentification.setFirstName("Nuno");
        personalIdentification.setLastName("Sousa");

        Password password = new Password();
        password.setHashedPassword("hdsahfsaf");
        client.setPassword(password);

        client.setUserType(UserTypeEnum.CLIENT);
        client.setUserStatus(UserStatusEnum.ACTIVE);
        client.setPersonalIdentification(personalIdentification);

        ClientDao clientDao = new ClientDaoImpl();
        clientDao.insert(client);
    }

}