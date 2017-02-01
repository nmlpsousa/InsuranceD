package pt.insuranced.models;

import java.time.LocalDate;

public class PersonalIdentification {
    private int id;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private PhoneNumber phoneNumber;

    private Address address;

    private String email;

    private String identificationNumber;

    private String fiscalNumber;

    public PersonalIdentification() {
    }

    public PersonalIdentification(int id, String firstName, String lastName, LocalDate dateOfBirth, PhoneNumber phoneNumber, Address address, String email,
            String identificationNumber, String fiscalNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.identificationNumber = identificationNumber;
        this.fiscalNumber = fiscalNumber;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public PhoneNumber getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentificationNumber() {
        return this.identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getFiscalNumber() {
        return this.fiscalNumber;
    }

    public void setFiscalNumber(String fiscalNumber) {
        this.fiscalNumber = fiscalNumber;
    }

    public int getId() {
        return this.id;
    }

    public static final class Builder {
        private int id;

        private String firstName;

        private String lastName;

        private LocalDate dateOfBirth;

        private PhoneNumber phoneNumber;

        private Address address;

        private String email;

        private String identificationNumber;

        private String fiscalNumber;

        private Builder(int id) {
            this.id = id;
        }

        public static Builder newBuilder(int id) {
            return new Builder(id);
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder setPhoneNumber(PhoneNumber phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setAddress(Address address) {
            this.address = address;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setIdentificationNumber(String identificationNumber) {
            this.identificationNumber = identificationNumber;
            return this;
        }

        public Builder setFiscalNumber(String fiscalNumber) {
            this.fiscalNumber = fiscalNumber;
            return this;
        }

        public PersonalIdentification build() {
            return new PersonalIdentification(this.id, this.firstName, this.lastName, this.dateOfBirth, this.phoneNumber, this.address, this.email, this.identificationNumber,
                    this.fiscalNumber);
        }
    }
}
