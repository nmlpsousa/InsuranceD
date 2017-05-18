package pt.insuranced.sdk.enums;

import java.util.HashMap;
import java.util.Map;

public enum UserStatusEnum {
    ACTIVE(1L, "Active"),
    INACTIVE(2L, "Inactive"),
    PENDING(3L, "Pending");

    private final Long code;

    private final String userStatus;

    private static Map<Long, UserStatusEnum> enumByCode = new HashMap<>(10);

    static {
        for (UserStatusEnum statusEnum : UserStatusEnum.values()) {
            enumByCode.put(statusEnum.getCode(), statusEnum);
        }
    }

    UserStatusEnum(Long code, String userStatus) {
        this.code = code;
        this.userStatus = userStatus;
    }

    public static UserStatusEnum getStatusByCode(Long code) {
        return enumByCode.get(code);
    }

    public Long getCode() {
        return this.code;
    }

    public String getUserStatus() {
        return this.userStatus;
    }
}