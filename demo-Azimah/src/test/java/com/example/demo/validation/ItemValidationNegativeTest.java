package com.example.demo.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ItemValidationNegativeTest {

    @Test
    @DisplayName("parseAndValidateLongId: Should throw IllegalArgumentException for a non-numeric string")
    void parseAndValidateLongId_InvalidCase_NonNumeric() {
        String input = "abc";

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            ItemValidation.parseAndValidateLongId(input);
        });

        assertEquals("Invalid ID format. Must be a positive number.", thrown.getMessage());
    }

    @Test
    @DisplayName("parseAndValidateLongId: Should throw IllegalArgumentException for a negative number")
    void parseAndValidateLongId_InvalidCase_NegativeNumber() {
        String input = "-123";

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            ItemValidation.parseAndValidateLongId(input);
        });

        assertEquals("Invalid ID format. Must be a positive number.", thrown.getMessage());
    }

    @Test
    @DisplayName("parseAndValidateLongId: Should throw IllegalArgumentException for a blank string")
    void parseAndValidateLongId_InvalidCase_Blank() {
        String input = "   ";

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            ItemValidation.parseAndValidateLongId(input);
        });

        assertEquals("Invalid ID format. Must be a positive number.", thrown.getMessage());
    }

    @Test
    @DisplayName("parseAndValidateLongId: Should throw IllegalArgumentException for a null input")
    void parseAndValidateLongId_InvalidCase_Null() {
        String input = null;

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            ItemValidation.parseAndValidateLongId(input);
        });

        assertEquals("Invalid ID format. Must be a positive number.", thrown.getMessage());
    }
}
