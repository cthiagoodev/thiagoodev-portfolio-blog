package br.com.thiagoodev.portfolio.domain.exceptions;

public class ProfileNotExistsException extends RuntimeException {
    public ProfileNotExistsException(String message) {
        super(message);
    }

    public ProfileNotExistsException() {
        super("Profile not exists");
    }
}
