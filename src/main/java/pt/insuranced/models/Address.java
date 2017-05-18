package pt.insuranced.models;

import pt.insuranced.sdk.enums.CountryEnum;

public class Address {
    private Long id;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String postalCode;

    private CountryEnum country;

    public Address() {
    }

    public Address(Long id, String addressLine1, String addressLine2, String city, String postalCode, CountryEnum country) {
        this.id = id;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    public String getAddressLine1() {
        return this.addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return this.addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public CountryEnum getCountry() {
        return this.country;
    }

    public void setCountry(CountryEnum country) {
        this.country = country;
    }

    public Long getId() {
        return this.id;
    }
}
