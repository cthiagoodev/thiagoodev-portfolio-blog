package br.com.thiagoodev.portfolio.domain.exceptions;

public class UserAlreadyEnabledException extends RuntimeException {
    public UserAlreadyEnabledException(String message) {
        super(message);
    }

    public UserAlreadyEnabledException() {
        super("User already enabled");
    }
}
