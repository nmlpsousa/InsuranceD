package pt.insuranced.models;

import pt.insuranced.sdk.enums.UserStatusEnum;
import pt.insuranced.sdk.enums.UserTypeEnum;

import java.time.LocalDate;
import java.util.List;

public class Client extends User {
    private List<Policy> policyList;

    public Client(int id, String username, Password password, PersonalIdentification personalIdentification, List<Password> oldPasswords, LocalDate lastPasswordChangeDate,
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
}
