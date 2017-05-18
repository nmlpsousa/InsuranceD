package pt.insuranced.models;

import org.apache.commons.lang3.builder.ToStringBuilder;
import pt.insuranced.sdk.enums.UserStatusEnum;
import pt.insuranced.sdk.enums.UserTypeEnum;

import java.time.LocalDate;
import java.util.List;

public class Client extends AbstractUser {
    private List<Policy> policyList;

    public Client() {
        super();
    }

    public Client(Long id, String username, Password password, PersonalIdentification personalIdentification, List<Password> oldPasswords, LocalDate lastPasswordChangeDate,
            UserTypeEnum userType, UserStatusEnum userStatus, List<Policy> policyList) {
        super(id, username, password, personalIdentification, oldPasswords, lastPasswordChangeDate, userType, userStatus);
        this.policyList = policyList;
    }

    public List<Policy> getPolicyList() {
        return this.policyList;
    }

    public void setPolicyList(List<Policy> policyList) {
        this.policyList = policyList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", getId())
                .append("username", getUsername())
                .append("password", getPassword())
                .append("personalIdentification", getPersonalIdentification())
                .append("oldPasswords", getOldPasswords())
                .append("lastPasswordChangeDate", getLastPasswordChangeDate())
                .append("userType", getUserType())
                .append("userStatus", getUserStatus())
                .append("policyList", this.policyList)
                .toString();
    }

    public static final class Builder {
        private Long id;

        private List<Policy> policyList;

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

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setPolicyList(List<Policy> policyList) {
            this.policyList = policyList;
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

        public Client build() {
            return new Client(this.id, this.username, this.password, this.personalIdentification, this.oldPasswords, this.lastPasswordChangeDate, this.userType, this.userStatus,
                    this.policyList);
        }
    }
}
