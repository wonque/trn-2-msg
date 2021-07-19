package com.tietoevry.trn2msg.service;

import com.tietoevry.trn2msg.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InputValidatorTest {

    private final InputValidator validator = new InputValidator();

    @ParameterizedTest(name = "{index} - {1}")
    @MethodSource("invalidInputs")
    public void testInputValidationInvalidInputs(String input, String description) {
        assertThrows(ValidationException.class, () -> validator.validate(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testInputValidationNullAndEmpty(String input) {
        assertThrows(ValidationException.class, () -> validator.validate(input));
    }

    @Test
    public void testInputValidationWithValidInputs() {
        Arrays.asList(
                "01966796969690609300000000459920181111143445840",
                "00966796969690609311111114599201811111434458400"
        ).forEach(validator::validate);
    }

    private List<Arguments> invalidInputs() {
        return Arrays.asList(
                arguments("", "Empty string"),
                arguments(" ", "String with length=1 containing whitespace char"),
                arguments("a", "String with length=1, single letter"),
                arguments("a a", "String with letters and whitespace character"),
                arguments("some random string", "Arbitrary string with random phrase"),
                arguments("abcdefghklmnhjls;sasd031", "Arbitrary characters"),
                arguments("10966796969690609300000000459920181111143445840", "All numeric string, not valid first character"),
                arguments("019667969696906093000000004599201811111434458400", "All numeric string with length > max expected"),
                arguments("0096679696969060930000000045992018111114344584", "All numeric string with length < max expected")
        );
    }
}