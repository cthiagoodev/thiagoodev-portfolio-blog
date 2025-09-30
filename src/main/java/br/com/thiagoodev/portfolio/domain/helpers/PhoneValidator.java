package br.com.thiagoodev.portfolio.domain.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator {
    private static final String REGEX_PHONE = "^(?:\\(?([1-9][0-9])\\)?\\s?)?(?:((?:9\\d|[2-9])\\d{3})\\-?(\\d{4}))$";
    private static final Pattern PATTERN_PHONE = Pattern.compile(REGEX_PHONE);

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }

        Matcher matcher = PATTERN_PHONE.matcher(phoneNumber);
        return matcher.matches();
    }
}
