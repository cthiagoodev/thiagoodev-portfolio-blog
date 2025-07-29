package br.com.thiagoodev.blogapi.domain.exceptions;

public class UserNotExistsException extends RuntimeException {
    public UserNotExistsException(String message) {
        super(message);
    }

    public UserNotExistsException() {
        super("User not exists");
    }
}
