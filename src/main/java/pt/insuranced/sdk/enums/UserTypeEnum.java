package pt.insuranced.sdk.enums;

import java.util.HashMap;
import java.util.Map;

public enum UserTypeEnum {
    CLIENT(1L, "Client"),
    ACCOUNT_MANAGER(2L, "Account Manager");

    private final Long code;

    private final String userType;

    private static Map<Long, UserTypeEnum> enumByCode = new HashMap<>(10);

    static {
        for (UserTypeEnum typeEnum : UserTypeEnum.values()) {
            enumByCode.put(typeEnum.getCode(), typeEnum);
        }
    }

    UserTypeEnum(Long code, String userType) {
        this.code = code;
        this.userType = userType;
    }

    public static UserTypeEnum getTypeByCode(Long code) {
        return enumByCode.get(code);
    }

    public Long getCode() {
        return this.code;
    }

    public String getUserType() {
        return this.userType;
    }
}
