package pt.insuranced.sdk.enums;

import java.util.HashMap;
import java.util.Map;

public enum CountryEnum {
    PT(1, "Portugal", "PT"),
    ES(2, "Spain", "ES"),
    US(3, "United Status", "US"),
    FR(4, "France", "FR");

    private final int code;

    private final String description;

    private final String abbreviation;

    private static Map<Integer,CountryEnum> enumByInteger = new HashMap<>(10);

    static {
        for (CountryEnum countryEnum : CountryEnum.values()) {
            enumByInteger.put(countryEnum.getCode(),countryEnum);
        }
    }

    private CountryEnum(int code, String description, String abbreviation) {
        this.code = code;
        this.description = description;
        this.abbreviation = abbreviation;
    }

    public static CountryEnum getCountryByCode(Integer code) {
        return enumByInteger.get(code);
    }

    public int getCode() {
        return this.code;
    }

    public String getDescription() {
        return description;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
