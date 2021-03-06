package pt.insuranced.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.insuranced.sdk.enums.UserStatusEnum;
import pt.insuranced.sdk.enums.UserTypeEnum;

import java.time.LocalDate;
import java.util.List;

public abstract class AbstractUser {
    private int id;

    private String username;

    @JsonIgnore
    private Password password;

    private PersonalIdentification personalIdentification;

    private List<Password> oldPasswords;

    private LocalDate lastPasswordChangeDate;

    private UserTypeEnum userType;

    private UserStatusEnum userStatus;

    protected AbstractUser() {
    }

    protected AbstractUser(int id, String username, Password password, PersonalIdentification personalIdentification, List<Password> oldPasswords, LocalDate lastPasswordChangeDate,
            UserTypeEnum userType, UserStatusEnum userStatus) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.personalIdentification = personalIdentification;
        this.oldPasswords = oldPasswords;
        this.lastPasswordChangeDate = lastPasswordChangeDate;
        this.userType = userType;
        this.userStatus = userStatus;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public Password getPassword() {
        return this.password;
    }

    @JsonProperty
    public void setPassword(Password password) {
        this.password = password;
    }

    public PersonalIdentification getPersonalIdentification() {
        return this.personalIdentification;
    }

    public void setPersonalIdentification(PersonalIdentification personalIdentification) {
        this.personalIdentification = personalIdentification;
    }

    public List<Password> getOldPasswords() {
        return this.oldPasswords;
    }

    public void setOldPasswords(List<Password> oldPasswords) {
        this.oldPasswords = oldPasswords;
    }

    public LocalDate getLastPasswordChangeDate() {
        return this.lastPasswordChangeDate;
    }

    public void setLastPasswordChangeDate(LocalDate lastPasswordChangeDate) {
        this.lastPasswordChangeDate = lastPasswordChangeDate;
    }

    public UserTypeEnum getUserType() {
        return this.userType;
    }

    public void setUserType(UserTypeEnum userType) {
        this.userType = userType;
    }

    public UserStatusEnum getUserStatus() {
        return this.userStatus;
    }

    public void setUserStatus(UserStatusEnum userStatus) {
        this.userStatus = userStatus;
    }

    public int getId() {
        return this.id;
    }
}
