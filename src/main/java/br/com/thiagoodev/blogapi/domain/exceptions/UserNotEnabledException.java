package br.com.thiagoodev.blogapi.domain.exceptions;

public class UserNotEnabledException extends RuntimeException {
    public UserNotEnabledException(String message) {
        super(message);
    }

    public UserNotEnabledException() {
        super("User not enabled");
    }
}
