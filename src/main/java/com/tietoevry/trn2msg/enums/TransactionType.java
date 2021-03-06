package com.tietoevry.trn2msg.enums;

import java.util.Arrays;
import java.util.Optional;

public enum TransactionType {
    PURCHASE("00", "Purchase"),
    WITHDRAWAL("01", "Withdrawal");

    private final String numericCode;
    private final String formattedType;

    TransactionType(String numericCode, String formattedType) {
        this.numericCode = numericCode;
        this.formattedType = formattedType;
    }

    public String getFormattedType() {
        return formattedType;
    }

    public static Optional<TransactionType> getByNumericCode(String code) {
        return Arrays.stream(values()).filter(value -> value.numericCode.equals(code)).findFirst();
    }
}
