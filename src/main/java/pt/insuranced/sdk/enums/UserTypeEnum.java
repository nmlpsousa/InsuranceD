package pt.insuranced.sdk.enums;

import java.util.HashMap;
import java.util.Map;

public enum UserTypeEnum {
    CLIENT(1, "Client"),
    ACCOUNT_MANAGER(2, "Account Manager");

    private final int code;

    private final String userType;

    private static Map<Integer, UserTypeEnum> enumByInteger = new HashMap<>(10);

    static {
        for (UserTypeEnum typeEnum : UserTypeEnum.values()) {
            enumByInteger.put(typeEnum.getCode(),typeEnum);
        }
    }

    private UserTypeEnum(int code, String userType) {
        this.code = code;
        this.userType = userType;
    }

    public static UserTypeEnum getTypeByCode(Integer code) {
        return enumByInteger.get(code);
    }

    public int getCode() {
        return this.code;
    }

    public String getUserType() {
        return this.userType;
    }
}
