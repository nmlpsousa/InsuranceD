package pt.insuranced.sdk.enums;

import java.util.HashMap;
import java.util.Map;

public enum CountryEnum {
    PT(1L, "Portugal", "PT"),
    ES(2L, "Spain", "ES"),
    US(3L, "United Status", "US"),
    FR(4L, "France", "FR");

    private final Long code;

    private final String description;

    private final String abbreviation;

    private static Map<Long,CountryEnum> enumByCode = new HashMap<>(10);

    static {
        for (CountryEnum countryEnum : CountryEnum.values()) {
            enumByCode.put(countryEnum.getCode(),countryEnum);
        }
    }

    CountryEnum(Long code, String description, String abbreviation) {
        this.code = code;
        this.description = description;
        this.abbreviation = abbreviation;
    }

    public static CountryEnum getCountryByCode(Long code) {
        return enumByCode.get(code);
    }

    public Long getCode() {
        return this.code;
    }

    public String getDescription() {
        return description;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
