package com.tietoevry.trn2msg.transaction;

import com.tietoevry.trn2msg.enums.Currency;
import com.tietoevry.trn2msg.enums.TransactionType;
import com.tietoevry.trn2msg.model.Transaction;
import com.tietoevry.trn2msg.service.InputParsingService;
import com.tietoevry.trn2msg.service.InputValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InputParsingServiceTest {

    private InputValidator validator = new InputValidator();
    private InputParsingService service = new InputParsingService(validator);

    @ParameterizedTest
    @NullAndEmptySource
    public void testExtractPersonalAccountNullAndEmptyString(String input) {
        assertTrue(service.getTransactionObject(input).isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid", " ", "     ", "1234567889987"})
    public void testExtractPersonalAccountInvalidInputs(String input) {
        assertTrue(service.getTransactionObject(input).isEmpty());
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
}