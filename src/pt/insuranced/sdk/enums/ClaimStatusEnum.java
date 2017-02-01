package pt.insuranced.sdk.enums;

import java.util.HashMap;
import java.util.Map;

public enum ClaimStatusEnum {
    DRAFT(0),

    VALIDATED(1),

    PAYMENT(2),

    PROCESSED(3);

    private int code;

    private ClaimStatusEnum(int code) {
        this.code = code;
    }

    private static Map<Integer, ClaimStatusEnum> enumValuesByCode;

    static {
        enumValuesByCode = new HashMap<>(ClaimStatusEnum.values().length);
        for (ClaimStatusEnum claimStatusEnum : ClaimStatusEnum.values()) {
            enumValuesByCode.put(claimStatusEnum.getCode(), claimStatusEnum);
        }
    }

    public int getCode() {
        return this.code;
    }

    public static ClaimStatusEnum getClaimStatusByCode(int code) {
        return enumValuesByCode.get(code);
    }
}
