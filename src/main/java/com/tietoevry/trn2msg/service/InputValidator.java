package com.tietoevry.trn2msg.service;

import com.tietoevry.trn2msg.exception.ValidationException;
import com.tietoevry.trn2msg.utils.ObfuscationUtil;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {

    /**
     * According to specification, valid input should always start with 0
     * Second character is either 0 or 1
     */
    private final Pattern REGEX_PATTERN = Pattern.compile("[0][0-1][0-9]{45}");

    public void validate(String input) throws ValidationException {
        checkIfNullOrEmpty(input);
        Matcher matcher = REGEX_PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new ValidationException(String.format("Invalid input received. Input: %s", ObfuscationUtil.obfuscate(input)));
        }
    }

    private void checkIfNullOrEmpty(String input) {
        if (Objects.isNull(input) || input.isBlank()) {
            throw new ValidationException("Provided input is null or blank");
        }
    }
}
