package pt.insuranced.sdk.enums;

import java.util.HashMap;
import java.util.Map;

public enum UserStatusEnum {
    ACTIVE(1, "Active"),
    INACTIVE(2, "Inactive"),
    PENDING(3, "Pending");

    private final int code;

    private final String userStatus;

    private static Map<Integer, UserStatusEnum> enumByInteger = new HashMap<>(10);

    static {
        for (UserStatusEnum statusEnum : UserStatusEnum.values()) {
            enumByInteger.put(statusEnum.getCode(),statusEnum);
        }
    }

    private UserStatusEnum(int code, String userStatus) {
        this.code = code;
        this.userStatus = userStatus;
    }

    public static UserStatusEnum getStatusByCode(Integer code) {
        return enumByInteger.get(code);
    }

    public int getCode() {
        return this.code;
    }

    public String getUserStatus() {
        return this.userStatus;
    }
}