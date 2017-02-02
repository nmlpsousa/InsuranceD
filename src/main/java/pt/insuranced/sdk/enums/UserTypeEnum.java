package pt.insuranced.sdk.enums;

public enum UserTypeEnum {
    CLIENT(1, "Client"),
    ACCOUNT_MANAGER(2, "Account Manager");

    private final int code;

    private final String userType;

    private UserTypeEnum(int code, String userType) {
        this.code = code;
        this.userType = userType;
    }

    public int getCode() {
        return this.code;
    }

    public String getUserType() {
        return this.userType;
    }
}
