package pt.insuranced.main;

import org.apache.commons.cli.*;
import pt.insuranced.models.*;
import pt.insuranced.sdk.enums.CountryEnum;
import pt.insuranced.sdk.enums.UserStatusEnum;
import pt.insuranced.sdk.enums.UserTypeEnum;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by gcoutinho on 16-05-2017.
 */
public class cli {
    /**
     * Private attribute to store parsed command-line arguments
     */
    private CommandLine cmd;

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
            Scanner scanner = new Scanner(System.in);
            scanner.useDelimiter("\\n");
            Client newClient = new Client();
            PersonalIdentification newPersonalId = new PersonalIdentification();
            Address newAddress = new Address();
            PhoneNumber newPhoneNo = new PhoneNumber();
            Password newPassword = new Password();

            System.out.println("############### NEW CLIENT ###############");
            System.out.println(new Date());

            //Client Details
            System.out.println("User Name: ");
            newClient.setUsername(scanner.next());
            System.out.println("Password: ");
            newPassword.setHashedPassword(scanner.next());
            newClient.setPassword(newPassword);
            System.out.println("First Name: ");
            newPersonalId.setFirstName(scanner.next());
            System.out.println("Last Name: ");
            newPersonalId.setLastName(scanner.next());
            //BirthDate
            System.out.println("Birth Date (YYYY-MM-DD): ");
            DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            dtFormatter = dtFormatter.withLocale(Locale.US); //Utilizar internaciaonalização
            LocalDate localDate = LocalDate.parse(scanner.next(), dtFormatter);
            newPersonalId.setDateOfBirth(localDate);
            //PhoneNumber
            System.out.println("Phone Number\n");
            System.out.println("Prefix: ");
            newPhoneNo.setPrefix(scanner.next());
            System.out.println("Number: ");
            newPhoneNo.setNumber(scanner.nextInt());
            newPersonalId.setPhoneNumber(newPhoneNo);
            //Address
            System.out.println("Adress ");
            System.out.println("Enter Address Line 1: ");
            newAddress.setAddressLine1(scanner.next());
            System.out.println("Enter Address Line 2: ");
            newAddress.setAddressLine2(scanner.next());
            System.out.println("City: ");
            newAddress.setCity(scanner.next());
            System.out.println("Country: ");
            newAddress.setCountry(CountryEnum.getCountryByCode(scanner.nextInt()));
            System.out.println("Postal Code: ");
            newAddress.setPostalCode(scanner.next());
            newPersonalId.setAddress(newAddress);
            //Other Client Details
            System.out.println("Email: ");
            newPersonalId.setEmail(scanner.next());
            System.out.println("Identification Number: ");
            newPersonalId.setIdentificationNumber(scanner.next());
            System.out.println("Fiscal Number: ");
            newPersonalId.setFiscalNumber(scanner.next());
            newClient.setPersonalIdentification(newPersonalId);
            System.out.println("User Status\n\n 1) Active\n 2) Inactive\n 3) Pending\n");
            System.out.println("Select Option: ");
            newClient.setUserStatus(UserStatusEnum.getStatusByCode(scanner.nextInt()));
            System.out.println("User Type\n\n 1) Client\n 2) Manager\n");
            System.out.println("Select Option: ");
            newClient.setUserType(UserTypeEnum.getTypeByCode(scanner.nextInt()));
            System.out.println("##########################################");

            System.out.println("############# CLIENT DETAILS #############");
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
                    newClient.getPersonalIdentification().getAddress().getPostalCode()+
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
            System.out.println("############# CLIENT DETAILS #############");
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

        if (validateOptions(params)) {
            doSomething();
        }
    }


}
