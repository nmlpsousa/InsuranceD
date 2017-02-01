package pt.insuranced.models;

import pt.insuranced.sdk.enums.UserStatusEnum;
import pt.insuranced.sdk.enums.UserTypeEnum;

import java.time.LocalDate;
import java.util.List;

public class AccountManager extends AbstractUser {
    private int employeeNo;

    public AccountManager(int id, String username, Password password, PersonalIdentification personalIdentification, List<Password> oldPasswords,
            LocalDate lastPasswordChangeDate, UserTypeEnum userType, UserStatusEnum userStatus, int employeeNo) {
        super(id, username, password, personalIdentification, oldPasswords, lastPasswordChangeDate, userType, userStatus);
        this.employeeNo = employeeNo;
    }

    public static final class Builder {
        private int id;

        private int employeeNo;

        private String username;

        private Password password;

        private PersonalIdentification personalIdentification;

        private List<Password> oldPasswords;

        private LocalDate lastPasswordChangeDate;

        private UserTypeEnum userType;

        private UserStatusEnum userStatus;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setEmployeeNo(int employeeNo) {
            this.employeeNo = employeeNo;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(Password password) {
            this.password = password;
            return this;
        }

        public Builder setPersonalIdentification(PersonalIdentification personalIdentification) {
            this.personalIdentification = personalIdentification;
            return this;
        }

        public Builder setOldPasswords(List<Password> oldPasswords) {
            this.oldPasswords = oldPasswords;
            return this;
        }

        public Builder setLastPasswordChangeDate(LocalDate lastPasswordChangeDate) {
            this.lastPasswordChangeDate = lastPasswordChangeDate;
            return this;
        }

        public Builder setUserType(UserTypeEnum userType) {
            this.userType = userType;
            return this;
        }

        public Builder setUserStatus(UserStatusEnum userStatus) {
            this.userStatus = userStatus;
            return this;
        }

        public AccountManager build() {
            return new AccountManager(this.id, this.username, this.password, this.personalIdentification, this.oldPasswords, this.lastPasswordChangeDate, this.userType,
                    this.userStatus,
                    this.employeeNo);
        }
    }
}
