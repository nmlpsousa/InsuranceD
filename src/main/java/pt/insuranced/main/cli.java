package pt.insuranced.main;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.insuranced.models.*;
import pt.insuranced.persistence.dao.ClientDaoImpl;
import pt.insuranced.persistence.dao.PolicyDaoImpl;
import pt.insuranced.persistence.dao.sdk.interfaces.ClientDao;
import pt.insuranced.persistence.dao.sdk.interfaces.PolicyDao;
import pt.insuranced.sdk.enums.CountryEnum;
import pt.insuranced.sdk.enums.UserStatusEnum;
import pt.insuranced.sdk.enums.UserTypeEnum;
import pt.insuranced.sdk.exceptions.InsuranceDException;
import pt.insuranced.services.FileImportService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by gcoutinho on 16-05-2017.
 */
public class cli {
    /**
     * Private attribute to store parsed command-line arguments
     */
    private CommandLine cmd;
    private Scanner scanner;

    private static final Logger logger = LoggerFactory.getLogger(cli.class);

    private ResourceBundle labels;
    private Locale currentLocale;
    private String language;
    private String country;

    public void setCLILanguage() {

        System.out.print("Language: ");
        language = new String(scanner.next());
        System.out.print("Country: ");
        country = new String(scanner.next());
        currentLocale = new Locale(language, country);
        labels = ResourceBundle.getBundle("LabelsBundle", currentLocale);
    }

    /**
     * Validates raw command-line arguments with respect to the set of desired options.
     * Defines attribute (@link #cmd cmd) for latler use by other class methods.
     *
     * @param params raw command-line arguments as passed into main() method
     * @return boolean flag indicating whether passed options valid or not
     */
    public boolean validateOptions(String[] params) {
        //-- Create a new instance of Options class
        Options options = new Options();
        //-- Create a new instance of OptionGroup (list of mutually exclusive options) class
        OptionGroup actionGroup = new OptionGroup();

        options.addOption("a", false, "New Client");
        options.addOption("b", false, "Import Clients");
        options.addOption("c", false, "Client Details");
        options.addOption("d", false, "New Policy");
        options.addOption("e", false, "Policy Details");

        CommandLineParser parser = new DefaultParser();

        try {
            cmd = parser.parse(options, params);
        } catch (ParseException parseException) {
            //-- In case of parsing error print usage info
            new HelpFormatter().printHelp(("java ") + this.getClass().getName(), "", options, "\n" + parseException.getMessage(), true);
            return false;
        }
        return true;
    }

    /**
     * Illustrates typical activity of the application with respect to values fo parsed command-line arguments
     */
    private void doSomething() {

        /**
         *  Option: New Client
         */
        if (cmd.hasOption("a")) {
            newClient();
        }

        if (cmd.hasOption("b")) {
            importClients();
        }

        if (cmd.hasOption("c")) {
            clientDetails();
        }

        if (cmd.hasOption("d")) {
            newPolicy();
        }

        if (cmd.hasOption("e")) {
            policyDetails();
        }

    }

    private void importClients() {
        FileImportService file = new FileImportService("postgres");

        System.out.print(labels.getString("csvPath"));
        String path = scanner.next();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> file.importClients(path));
        executorService.shutdown();
    }

    private void policyDetails() {
        PolicyDao policyDao = new PolicyDaoImpl();

        try {
            System.out.print(labels.getString("policyNo"));
            Optional<Policy> policyOptional = policyDao.get(scanner.nextLong());
            if (!policyOptional.isPresent()) {
                System.out.println(labels.getString("policyNotFound"));
            } else {
                Policy policy = policyOptional.get();
                List<Coverage> coverageList = policy.getCoverageList();

                System.out.println("\n" +
                        labels.getString("startDate") +
                        labels.getString("endDate") +
                        policy.getEndDate() +
                        labels.getString("clientID") +
                        policy.getUserId() +
                        labels.getString("coverableDescription") +
                        policy.getCoverableList().get(0).getDescription() +
                        labels.getString("coverageDescription"));

                for (Coverage coverage : coverageList) {

                    System.out.println("\n + " +
                            labels.getString("coverageDescription") +
                            coverage.getDescription() +
                            labels.getString("coverageLimit") +
                            coverage.getLimit() +
                            labels.getString("coveragePremium") +
                            coverage.getPremium());
                }
            }
        } catch (InsuranceDException e) {
            System.out.println(labels.getString("policyNotFound"));
        }
    }

    private void clientDetails() {
        ClientDao clientDao = new ClientDaoImpl();
        try {
            Optional<Client> clientOptional = clientDao.get(scanner.nextLong());
            if (!clientOptional.isPresent()) {
                System.out.println(labels.getString("clientNotFound"));
            } else {
                System.out.print("\n" +
                        labels.getString("userName") +
                        clientOptional.get().getUsername() +
                        labels.getString("password") +
                        clientOptional.get().getPassword() +
                        labels.getString("firstName") +
                        clientOptional.get().getPersonalIdentification().getFirstName() +
                        labels.getString("lastName") +
                        clientOptional.get().getPersonalIdentification().getLastName() +
                        labels.getString("birthDate") +
                        clientOptional.get().getPersonalIdentification().getDateOfBirth() +
                        labels.getString("phoneNo") +
                        labels.getString("prefix") +
                        clientOptional.get().getPersonalIdentification().getPhoneNumber().getPrefix() +
                        labels.getString("number") +
                        clientOptional.get().getPersonalIdentification().getPhoneNumber().getNumber() +
                        labels.getString("addressLine1") +
                        clientOptional.get().getPersonalIdentification().getAddress().getAddressLine1() +
                        labels.getString("addressLine2") +
                        clientOptional.get().getPersonalIdentification().getAddress().getAddressLine2() +
                        labels.getString("city") +
                        clientOptional.get().getPersonalIdentification().getAddress().getCity() +
                        labels.getString("country") +
                        clientOptional.get().getPersonalIdentification().getAddress().getCountry() +
                        labels.getString("postCode") +
                        clientOptional.get().getPersonalIdentification().getAddress().getPostalCode() +
                        labels.getString("email") +
                        clientOptional.get().getPersonalIdentification().getEmail() +
                        labels.getString("idNo") +
                        clientOptional.get().getPersonalIdentification().getIdentificationNumber() +
                        labels.getString("fiscalNo") +
                        clientOptional.get().getPersonalIdentification().getFiscalNumber() +
                        labels.getString("userTypeMenu") +
                        clientOptional.get().getUserType() +
                        labels.getString("userStatusMenu") +
                        clientOptional.get().getUserStatus());
            }

        } catch (InsuranceDException e) {
            logger.error(labels.getString("errorGetClient"), e);
        }
    }

    private void newPolicy() {
        Policy newPolicy = new Policy();
        Coverage newCoverage = new Coverage();
        Coverable newCoverable = new Coverable();
        List<Coverage> coverageList = new ArrayList<Coverage>();
        List<Coverable> coverableList = new ArrayList<Coverable>();
        Double totalPremium = 0.0;

        PolicyDao policyDao = new PolicyDaoImpl();

        System.out.println(labels.getString("newPolicy"));
        System.out.println(new Date());

        //Policy Details
        System.out.print(labels.getString("startDate"));
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dtFormatter = dtFormatter.withLocale(currentLocale);
        LocalDate localDate;
        localDate = LocalDate.parse(scanner.next(), dtFormatter);
        newPolicy.setStartDate(localDate);
        System.out.print(labels.getString("endDate"));
        localDate = LocalDate.parse(scanner.next(), dtFormatter);
        newPolicy.setEndDate(localDate);
        System.out.print(labels.getString("clientID"));
        newPolicy.setUserId(scanner.nextLong());

        //Coverable Details
        System.out.print(labels.getString("coverableDescription"));
        newCoverable.setDescription(scanner.next());
        coverableList.add(newCoverable);

        //Policy Coverage
        do {
            System.out.print(labels.getString("coverageDescription"));
            newCoverage.setDescription(scanner.next());
            System.out.print(labels.getString("coverageLimit"));
            newCoverage.setLimit(scanner.nextDouble());
            System.out.print(labels.getString("coveragePremium"));
            newCoverage.setPremium(scanner.nextDouble());
            coverageList.add(newCoverage);
            totalPremium += newCoverage.getPremium();
            System.out.print(labels.getString("newCoverage"));
        } while ((scanner.next()).equalsIgnoreCase("Y"));

        newPolicy.setPremium(totalPremium);

        try {
            policyDao.insert(newPolicy);
        } catch (InsuranceDException e) {
            logger.error(labels.getString("errorInsertPolicy"), e);
        }
    }

    private void newClient() {
        Client newClient = new Client();
        PersonalIdentification newPersonalId = new PersonalIdentification();
        Address newAddress = new Address();
        PhoneNumber newPhoneNo = new PhoneNumber();
        Password newPassword = new Password();

        ClientDao clientDao = new ClientDaoImpl();

        System.out.println(labels.getString("newClient"));
        System.out.println(new Date());

        //Client Details
        System.out.print(labels.getString("userName"));
        newClient.setUsername(scanner.next());
        System.out.print(labels.getString("password"));
        newPassword.setHashedPassword(scanner.next());
        newClient.setPassword(newPassword);
        System.out.print(labels.getString("firstName"));
        newPersonalId.setFirstName(scanner.next());
        System.out.print(labels.getString("lastName"));
        newPersonalId.setLastName(scanner.next());
        //BirthDate
        System.out.print(labels.getString("birthDate"));
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dtFormatter = dtFormatter.withLocale(currentLocale);
        LocalDate localDate = LocalDate.parse(scanner.next(), dtFormatter);
        newPersonalId.setDateOfBirth(localDate);
        //PhoneNumber
        System.out.println(labels.getString("phoneNo"));
        System.out.print(labels.getString("prefix"));
        newPhoneNo.setPrefix(scanner.next());
        System.out.print(labels.getString("number"));
        newPhoneNo.setNumber(scanner.nextInt());
        newPersonalId.setPhoneNumber(newPhoneNo);
        //Address
        System.out.println(labels.getString("address"));
        System.out.print(labels.getString("addressLine1"));
        newAddress.setAddressLine1(scanner.next());
        System.out.print(labels.getString("addressLine2"));
        newAddress.setAddressLine2(scanner.next());
        System.out.print(labels.getString("city"));
        newAddress.setCity(scanner.next());
        System.out.print(labels.getString("country"));
        newAddress.setCountry(CountryEnum.getCountryByCode(scanner.nextLong()));
        System.out.print(labels.getString("postCode"));
        newAddress.setPostalCode(scanner.next());
        newPersonalId.setAddress(newAddress);
        //Other Client Details
        System.out.print(labels.getString("email"));
        newPersonalId.setEmail(scanner.next());
        System.out.print(labels.getString("idNo"));
        newPersonalId.setIdentificationNumber(scanner.next());
        System.out.print(labels.getString("fiscalNo"));
        newPersonalId.setFiscalNumber(scanner.next());
        newClient.setPersonalIdentification(newPersonalId);
        System.out.println(labels.getString("userStatusMenu"));
        System.out.print(labels.getString("selectOption"));
        newClient.setUserStatus(UserStatusEnum.getStatusByCode(scanner.nextLong()));
        System.out.print(labels.getString("userTypeMenu"));
        System.out.print(labels.getString("selectOption"));
        newClient.setUserType(UserTypeEnum.getTypeByCode(scanner.nextLong()));
        System.out.print("##########################################");

        try {
            clientDao.insert(newClient);
            System.out.print(labels.getString("clientDetails"));
        } catch (InsuranceDException e) {
            logger.error(labels.getString("errorInsertClient"), e);
        }
    }

    /**
     * Accepts raw command-line arguments, validates them and invokes method to illustrate typical application activity.
     *
     * @param params raw command-line arguments as passed into main() method
     **/
    public cli(String[] params, Scanner inScanner) {
        cmd = null;
        scanner = inScanner;
        setCLILanguage();

        if (validateOptions(params)) {
            doSomething();
        }
    }


}
