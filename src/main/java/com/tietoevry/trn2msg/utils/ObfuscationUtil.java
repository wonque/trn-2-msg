package com.tietoevry.trn2msg.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public final class ObfuscationUtil {

    private static Logger log = LoggerFactory.getLogger(ObfuscationUtil.class);
    private static final String OBFUSCATION_CHAR = "*";

    private ObfuscationUtil() {
    }

    public static String obfuscate(String input) {
        if (Objects.isNull(input) || input.isBlank()) {
            log.warn("ObfuscationUtil - Unable to obfuscate null or blank input string!");
            return input;
        }
        if (input.length() < 3) {
            return input;
        }
        StringBuilder sb = new StringBuilder();
        int totalCharactersToObfuscate = input.length() / 2;
        int startingInd = totalCharactersToObfuscate == 1 ? 1 : totalCharactersToObfuscate / 2;

        for (int i = 0; i < input.length(); i++) {
            if (i == startingInd) {
                for (int k = 1; k <= totalCharactersToObfuscate; k++) {
                    sb.append(OBFUSCATION_CHAR);
                    i++;
                }
            }
            sb.append(input.charAt(i));
        }
        return sb.toString();
    }
}
