package com.tietoevry.trn2msg.service;

import com.tietoevry.trn2msg.enums.Currency;
import com.tietoevry.trn2msg.enums.TransactionType;
import com.tietoevry.trn2msg.exception.TransactionParsingException;
import com.tietoevry.trn2msg.exception.ValidationException;
import com.tietoevry.trn2msg.model.Transaction;
import com.tietoevry.trn2msg.utils.ObfuscationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Optional;

public class InputParsingService {

    private final Logger log = LoggerFactory.getLogger(InputParsingService.class);
    private InputValidator validator;

    public InputParsingService(InputValidator validator) {
        this.validator = validator;
    }

    public Optional<Transaction> getTransactionObject(String input) throws TransactionParsingException {
        try {
            validator.validate(input);
            TransactionType type = getTransactionType(input);
            LocalDateTime time = getTransactionTime(input);
            Currency currency = getTransactionCurrency(input);
            BigDecimal amount = getTransactionAmount(input);
            String accountNumber = getSubstring(input, 2, 18);

            Transaction transaction = new Transaction(
                    type, accountNumber, time, amount, currency
            );
            log.debug("TransactionInputParsingService - successfully created Transaction object for input " + ObfuscationUtil.obfuscate(input));
            return Optional.of(transaction);
        } catch (ValidationException ex) {
            log.error("TransactionInputParsingService - validation failed. Exception: ", ex);
            return Optional.empty();
        }
    }

    private TransactionType getTransactionType(String input) {
        String extracted = getSubstring(input, 0, 2);
        return TransactionType.getByNumericCode(extracted).orElseThrow(()
                -> new TransactionParsingException("TransactionInputParsingService - unable to get transaction type!"));
    }

    private LocalDateTime getTransactionTime(String input) {
        try {
            String extractedValue = getSubstring(input, 30, 44);
            return LocalDateTime.parse(extractedValue, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        } catch (DateTimeParseException ex) {
            throw new TransactionParsingException("TransactionInputParsingService - unable to get transaction time!");
        }
    }

    private Currency getTransactionCurrency(String input) {
        String extractedValue = getSubstring(input, 44, 47);
        return Currency.getByNumericCode(extractedValue).
                orElseThrow(()
                        -> new TransactionParsingException("TransactionInputParsingService - unable to get transaction currency!"));
    }

    private BigDecimal getTransactionAmount(String input) {
        try {
            String extractedValue = getSubstring(input, 18, 30);
            return BigDecimal.valueOf(Double.parseDouble(extractedValue) / 100.0);
        } catch (NumberFormatException ex) {
            throw new TransactionParsingException("TransactionInputParsingService - unable to get transaction amount!");
        }
    }

    private String getSubstring(String input, int start, int end) {
        if (Objects.isNull(input) || input.isBlank()) {
            throw new TransactionParsingException("Illegal argument supplied - input should not be null or blank!");
        }
        try {
            return input.substring(start, end);
        } catch (IndexOutOfBoundsException ex) {
            throw new TransactionParsingException(String.format("Unable to get substring from input %s. Exception: ",
                    ObfuscationUtil.obfuscate(input)), ex);
        }
    }
}
