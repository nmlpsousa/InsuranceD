package pt.insuranced.sdk.enums;

import java.util.HashMap;
import java.util.Map;

public enum ClaimStatusEnum {
    DRAFT(1,"Draft"),
    VALIDATED(2,"Validated"),
    PAYMENT(3,"Payment"),
    PROCESSED(4,"Processed");

    private final long code;
    
    private final String description;

    private ClaimStatusEnum(long code, String description) {
        this.code = code;
        this.description = description;
    }

    private static Map<Long, ClaimStatusEnum> enumValuesByCode;

    static {
        enumValuesByCode = new HashMap<>(ClaimStatusEnum.values().length);
        for (ClaimStatusEnum claimStatusEnum : ClaimStatusEnum.values()) {
            enumValuesByCode.put(claimStatusEnum.getCode(), claimStatusEnum);
        }
    }

    public long getCode() {
        return this.code;
    }
    
    public String getDescription() {
        return description;
    }

    public static ClaimStatusEnum getClaimStatusByCode(long code) {
        return enumValuesByCode.get(code);
    }
}
