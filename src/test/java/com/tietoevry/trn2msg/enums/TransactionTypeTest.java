package com.tietoevry.trn2msg.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTypeTest {

    @ParameterizedTest
    @NullAndEmptySource
    public void testGetTransactionTypeByNumericCode(String code) {
        assertTrue(TransactionType.getByNumericCode(code).isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "123", "!@#@!#", "null", "_", "I'm a table"})
    public void testGetTransactionTypeByNumericCodeInvalidStringValues(String code) {
        assertTrue(TransactionType.getByNumericCode(code).isEmpty());
    }

    @Test
    public void testGetTransactionTypeByNumericCodeValidCodes() {
        Optional<TransactionType> result = TransactionType.getByNumericCode("00");
        assertFalse(result.isEmpty());
        assertEquals(TransactionType.PURCHASE, result.get());

        result = TransactionType.getByNumericCode("01");
        assertFalse(result.isEmpty());
        assertEquals(TransactionType.WITHDRAWAL, result.get());
    }

}