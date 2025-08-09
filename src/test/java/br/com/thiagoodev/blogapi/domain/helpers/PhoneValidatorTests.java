package br.com.thiagoodev.blogapi.domain.helpers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PhoneValidator Tests")
public class PhoneValidatorTests {

    @ParameterizedTest
    @ValueSource(strings = {
        "11 91234-5678",
        "(11) 91234-5678",
        "1191234-5678",
        "11912345678",
        "21 2345-6789",
        "(21)2345-6789"
    })
    @DisplayName("Should return true for valid phone numbers")
    void shouldReturnTrueForValidPhones(String phone) {
        assertTrue(PhoneValidator.isValidPhoneNumber(phone));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "",
        " ",
        "abc",
        "123",
        "(00) 0000-0000",
        "(1) 99999-9999"
    })
    @DisplayName("Should return false for invalid phone numbers")
    void shouldReturnFalseForInvalidPhones(String phone) {
        assertFalse(PhoneValidator.isValidPhoneNumber(phone));
    }

    @Test
    @DisplayName("Should return false for null phone")
    void shouldReturnFalseForNull() {
        assertFalse(PhoneValidator.isValidPhoneNumber(null));
    }
}
