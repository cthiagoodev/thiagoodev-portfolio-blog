package br.com.thiagoodev.blogapi.domain.exceptions;

public class UserAlreadyEnabledException extends RuntimeException {
    public UserAlreadyEnabledException(String message) {
        super(message);
    }

    public UserAlreadyEnabledException() {
        super("User already enabled");
    }
}
