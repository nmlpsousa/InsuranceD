package pt.insuranced.main;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.insuranced.models.*;
import pt.insuranced.persistence.dao.ClientDaoImpl;
import pt.insuranced.sdk.enums.CountryEnum;
import pt.insuranced.sdk.enums.UserStatusEnum;
import pt.insuranced.sdk.enums.UserTypeEnum;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

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

        options.addOption("a", false,"Insert New Client");

        CommandLineParser parser = new DefaultParser();
        try {
            cmd = parser.parse(options, params);
        }
        catch (ParseException parseException) {
            //-- In case of parsing error print usage info
            new HelpFormatter().printHelp(("java ") + this.getClass().getName(), "", options, "\n" + parseException.getMessage(), true);
            return false;
        }
        return true;
    }

    /**
     *  Illustrates typical activity of the application with respect to values fo parsed command-line arguments
     */
    private void doSomething() {

        /**
        *  Option: New Client
        */
        if  (cmd.hasOption("a")){
            Client newClient = new Client();
            PersonalIdentification newPersonalId = new PersonalIdentification();
            Address newAddress = new Address();
            PhoneNumber newPhoneNo = new PhoneNumber();
            Password newPassword = new Password();

            ClientDaoImpl clientDao = new ClientDaoImpl();

            System.out.println(labels.getString("newClient"));
            System.out.println(new Date());

            //Client Details
            System.out.println(labels.getString("userName"));
            newClient.setUsername(scanner.next());
            System.out.println(labels.getString("password"));
            newPassword.setHashedPassword(scanner.next());
            newClient.setPassword(newPassword);
            System.out.println(labels.getString("firstName"));
            newPersonalId.setFirstName(scanner.next());
            System.out.println(labels.getString("lastName"));
            newPersonalId.setLastName(scanner.next());
            //BirthDate
            System.out.println(labels.getString("birthDate"));
            DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            dtFormatter = dtFormatter.withLocale(Locale.US); //Utilizar internaciaonalização
            LocalDate localDate = LocalDate.parse(scanner.next(), dtFormatter);
            newPersonalId.setDateOfBirth(localDate);
            //PhoneNumber
            System.out.println(labels.getString("phoneNo"));
            System.out.println(labels.getString("prefix"));
            newPhoneNo.setPrefix(scanner.next());
            System.out.println(labels.getString("number"));
            newPhoneNo.setNumber(scanner.nextInt());
            newPersonalId.setPhoneNumber(newPhoneNo);
            //Address
            System.out.println(labels.getString("address"));
            System.out.println(labels.getString("addressLine1"));
            newAddress.setAddressLine1(scanner.next());
            System.out.println(labels.getString("addressLine2"));
            newAddress.setAddressLine2(scanner.next());
            System.out.println(labels.getString("city"));
            newAddress.setCity(scanner.next());
            System.out.println(labels.getString("country"));
            newAddress.setCountry(CountryEnum.getCountryByCode(scanner.nextLong()));
            System.out.println(labels.getString("postCode"));
            newAddress.setPostalCode(scanner.next());
            newPersonalId.setAddress(newAddress);
            //Other Client Details
            System.out.println(labels.getString("email"));
            newPersonalId.setEmail(scanner.next());
            System.out.println(labels.getString("idNo"));
            newPersonalId.setIdentificationNumber(scanner.next());
            System.out.println(labels.getString("fiscalNo"));
            newPersonalId.setFiscalNumber(scanner.next());
            newClient.setPersonalIdentification(newPersonalId);
            System.out.println(labels.getString("userStatusMenu"));
            System.out.println(labels.getString("selectOption"));
            newClient.setUserStatus(UserStatusEnum.getStatusByCode(scanner.nextLong()));
            System.out.println(labels.getString("userTypeMenu"));
            System.out.println(labels.getString("selectOption"));
            newClient.setUserType(UserTypeEnum.getTypeByCode(scanner.nextLong()));
            System.out.println("##########################################");

            System.out.println(labels.getString("clientDetails"));
            System.out.println("\nYour new client is..." +
                    "\nUsername: " +
                    newClient.getUsername() +
                    "\nPassword: " +
                    newClient.getPassword() +
                    "\nFirst Name: " +
                    newClient.getPersonalIdentification().getFirstName() +
                    "\nLast Name: " +
                    newClient.getPersonalIdentification().getLastName() +
                    "\nBirth date: " +
                    newClient.getPersonalIdentification().getDateOfBirth() +
                    "\nPhone Number" +
                    "\nPrefix: " +
                    newClient.getPersonalIdentification().getPhoneNumber().getPrefix() +
                    "\nNumber: " +
                    newClient.getPersonalIdentification().getPhoneNumber().getNumber() +
                    "\nAddress Line 1: " +
                    newClient.getPersonalIdentification().getAddress().getAddressLine1() +
                    "\nAddress Line 2: " +
                    newClient.getPersonalIdentification().getAddress().getAddressLine2() +
                    "\nCity: " +
                    newClient.getPersonalIdentification().getAddress().getCity() +
                    "\nCountry: " +
                    newClient.getPersonalIdentification().getAddress().getCountry() +
                    "\nPostal Code: " +
                    newClient.getPersonalIdentification().getAddress().getPostalCode() +
                    "\nEmail: " +
                    newClient.getPersonalIdentification().getEmail() +
                    "\nIdentification Number: " +
                    newClient.getPersonalIdentification().getIdentificationNumber() +
                    "\nFiscal Number: " +
                    newClient.getPersonalIdentification().getFiscalNumber() +
                    "\nUser type: " +
                    newClient.getUserType() +
                    "\nUser status: " +
                    newClient.getUserStatus());
            System.out.println(labels.getString("clientDetails"));

            try {
                clientDao.insert(newClient);
            } catch (InsuranceDException e) {
                logger.error(labels.getString("errorInsertClient"), e);
            }
        }
    }

    /**
     *
     * Accepts raw command-line arguments, validates them and invokes method to illustrate typical application activity.
     *
     * @param params raw command-line arguments as passed into main() method
     **/
    public cli(String[] params) {
        cmd = null;
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\\n");
        System.out.println("Language: ");
        language = new String(scanner.next());
        System.out.println("Country: ");
        country = new String(scanner.next());
        currentLocale = new Locale(language, country);
        labels = ResourceBundle.getBundle("LabelsBundle", currentLocale);

        if (validateOptions(params)) {
            doSomething();
        }
    }


}
