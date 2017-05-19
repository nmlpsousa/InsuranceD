package pt.insuranced.services;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * The type File import service.
 */
public class FileImportService {

    private String daoType;

    /**
     * The constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FileImportService.class);

    /**
     * Instantiates a new File Import service.
     *
     * @param daoType a String that defines the DAO type (e.g. "postgres")
     */
    public FileImportService(String daoType) {
        this.daoType = daoType;
    }

    /**
     * Imports clients from a CSV file into the Database
     *
     * @param csvPath the path to the CSV file
     */
    public void importClients(String csvPath) throws InsuranceDException {
        if (StringUtils.isBlank(csvPath)) {
            throw new IllegalArgumentException("The provided CSV path is invalid");
        }

        Path path = Paths.get(csvPath);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<?> future = executorService.submit(() -> bulkInsertClients(path));

        LOGGER.info("Doing something in background");
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new InsuranceDException("An error occured while importing clients", e);
        }

        LOGGER.info("Finished");
    }

    /**
     * Bulk insert clients.
     *
     * @param path the path
     */
    private void bulkInsertClients(Path path) {
        try {
            List<String> csvLines = Files.readAllLines(path, Charset.defaultCharset());
            // First line is the header, ignore it
            csvLines.remove(0);
            List<Client> clientList = csvLines.stream()
                    .map(FileImportService::csvToClient)
                    .collect(Collectors.toList());
            ClientDaoFactory clientDaoFactory = new ClientDaoFactory();
            ClientDao clientDao = clientDaoFactory.getDao(this.daoType);
            clientDao.bulkInsert(clientList);
        } catch (IOException | InsuranceDException e) {
            LOGGER.error("Error reading CSV file", e);
        }
    }

    /**
     * Csv to client client.
     *
     * @param csv the csv
     * @return the client
     */
    private static Client csvToClient(String csv) {
        String[] values = csv.split(",");
        String firstName = values[0];
        String lastName = values[1];
        String addressString = values[2];
        String city = values[3];
        Integer countryCode = StringUtils.isEmpty(values[4]) ? -1 : Integer.parseInt(values[4]);
        String prefix = values[5];
        Integer phoneNumberInt = StringUtils.isEmpty(values[6]) ? -1 : Integer.parseInt(values[6]);
        LocalDate dateOfBirth = LocalDate.parse(values[7], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String passwordString = values[8];
        Integer userTypeCode = StringUtils.isEmpty(values[9]) ? -1 : Integer.parseInt(values[9]);
        Integer userStatusCode = StringUtils.isEmpty(values[10]) ? -1 : Integer.parseInt(values[10]);
        String username = values[11];

        CountryEnum country = CountryEnum.PT;
        UserTypeEnum userType = UserTypeEnum.CLIENT;
        UserStatusEnum userStatus = UserStatusEnum.ACTIVE;

        Client client = new Client();
        client.setUsername(username);

        PersonalIdentification personalIdentification = new PersonalIdentification();
        Address address = new Address();
        address.setAddressLine1(addressString);
        address.setCity(city);
        address.setCountry(country);

        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setPrefix(prefix);
        phoneNumber.setNumber(phoneNumberInt);

        personalIdentification.setAddress(address);
        personalIdentification.setPhoneNumber(phoneNumber);
        personalIdentification.setDateOfBirth(dateOfBirth);
        personalIdentification.setFirstName(firstName);
        personalIdentification.setLastName(lastName);

        Password password = new Password();
        password.setHashedPassword(passwordString);
        client.setPassword(password);

        client.setUserType(userType);
        client.setUserStatus(userStatus);
        client.setPersonalIdentification(personalIdentification);

        return client;
    }
}
