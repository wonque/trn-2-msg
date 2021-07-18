package com.tietoevry.trn2msg.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ObfuscationUtilTest {

    @Test
    public void testObfuscationUtil() {
        assertNull(ObfuscationUtil.obfuscate(null));
        assertEquals("", ObfuscationUtil.obfuscate(""));
        assertEquals(" ", ObfuscationUtil.obfuscate(" "));

        assertEquals(1, countObfuscatedChars(ObfuscationUtil.obfuscate("one")));
        assertEquals(5, countObfuscatedChars(ObfuscationUtil.obfuscate("obfuscator")));
        assertEquals(8, countObfuscatedChars(ObfuscationUtil.obfuscate("9667969696906093")));
    }

    private long countObfuscatedChars(String obfuscatedString) {
        return obfuscatedString.chars().filter(c -> c == '*').count();
    }
}