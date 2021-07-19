package com.tietoevry.trn2msg.service;

import com.tietoevry.trn2msg.enums.Currency;
import com.tietoevry.trn2msg.enums.TransactionType;
import com.tietoevry.trn2msg.exception.TransactionParsingException;
import com.tietoevry.trn2msg.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InputParsingServiceTest {

    private InputValidator validator = new InputValidator();
    private InputParsingService service = new InputParsingService(validator);

    @ParameterizedTest
    @NullAndEmptySource
    public void testGetTransactionObjectNullOrEmptyInput(String input) {
        assertTrue(service.getTransactionObject(input).isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid", " ", "     ", "1234567889987"})
    public void testGetTransactionObjectInvalidInputs(String input) {
        assertTrue(service.getTransactionObject(input).isEmpty());
    }

    @ParameterizedTest(name = "{index} - {1}")
    @MethodSource("invalidInputs")
    public void testGetTransactionObjectInvalidDataInInput(String input, String description) {
        assertThrows(TransactionParsingException.class, () -> service.getTransactionObject(input));
    }

    @Test
    public void testTransactionObjectFromInput() {
        String transaction = "00966796969690609300000000459920181111143445840";
        Optional<Transaction > optionalResult = service.getTransactionObject(transaction);

        assertFalse(optionalResult.isEmpty());
        Transaction result = optionalResult.get();
        assertEquals(TransactionType.PURCHASE, result.getTransactionType());
        assertEquals("9667969696906093", result.getAccNumber());
        assertEquals(BigDecimal.valueOf(45.99), result.getAmount());
        assertEquals(
                LocalDateTime.of(2018, 11, 11, 14, 34, 45),
                result.getTransactionTime()
        );
        assertEquals(Currency.USD, result.getCurrency());
    }

    private List<Arguments> invalidInputs() {
        return Arrays.asList(
                arguments("01966796969690609300000000459920181550143445840", "Invalid date/time"),
                arguments("01966796969690609300000000459920181111143445100", "Invalid (unsupported) currency")
        );
    }
}