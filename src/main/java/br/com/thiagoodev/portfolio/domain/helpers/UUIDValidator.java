package br.com.thiagoodev.portfolio.domain.helpers;

import java.util.UUID;
import java.util.regex.Pattern;

public class UUIDValidator {
    private static final String UUID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
    private static final Pattern UUID_PATTERN = Pattern.compile(UUID_REGEX);

    public static boolean isValidUUID(String uuid) {
        if (uuid == null || uuid.trim().isEmpty()) return false;
        return UUID_PATTERN.matcher(uuid).matches();
    }

    public static boolean isValidUUID(UUID uuid) {
        if (uuid == null) return false;
        return UUID_PATTERN.matcher(uuid.toString()).matches();
    }
}
