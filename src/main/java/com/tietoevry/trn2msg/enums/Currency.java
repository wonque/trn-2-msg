package com.tietoevry.trn2msg.enums;

import java.util.Optional;

public enum Currency {
    USD("840", "usd"),
    EUR("978", "eur"),
    GPB("826", "gpb"),
    RUB("643", "rub")
    ;

    private final String numericCode;
    private final String alphaCode;

    Currency(String numericCode, String alphaCode) {
        this.numericCode = numericCode;
        this.alphaCode = alphaCode;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public String getAlphaCode() {
        return alphaCode;
    }

    public static Optional<Currency> getByNumericCode(String code) {
        for (Currency c : values()) {
            if (c.numericCode.equals(code)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    public static Optional<Currency> getByNumericCode(int code) {
        for (Currency c : values()) {
            if (c.numericCode.equals(String.valueOf(c))) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }
}
