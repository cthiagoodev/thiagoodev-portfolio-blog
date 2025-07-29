package br.com.thiagoodev.blogapi.domain.exceptions;

public class InvalidUsernameFormatException extends RuntimeException {
    public InvalidUsernameFormatException(String message) {
        super(message);
    }

    public InvalidUsernameFormatException() {
        super("Invalid username format");
    }
}
