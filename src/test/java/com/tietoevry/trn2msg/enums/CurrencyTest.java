package com.tietoevry.trn2msg.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyTest {

    @ParameterizedTest
    @NullAndEmptySource
    public void testGetCurrencyByNumericCodeNullAndEmptyValues(String code) {
        assertTrue(Currency.getByNumericCode(code).isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "123", "!@#@!#", "null", "_", "I'm a table"})
    public void testGetCurrencyByNumericCodeInvalidStringValues(String code) {
        assertTrue(Currency.getByNumericCode(code).isEmpty());
    }

    @Test
    public void testGetCurrencyByNumericCodeValidCodes() {
        Optional<Currency> result = Currency.getByNumericCode("978");
        assertFalse(result.isEmpty());
        assertEquals(Currency.EUR, result.get());

        result = Currency.getByNumericCode("840");
        assertFalse(result.isEmpty());
        assertEquals(Currency.USD, result.get());

        result = Currency.getByNumericCode("826");
        assertFalse(result.isEmpty());
        assertEquals(Currency.GPB, result.get());

        result = Currency.getByNumericCode("643");
        assertFalse(result.isEmpty());
        assertEquals(Currency.RUB, result.get());
    }
}