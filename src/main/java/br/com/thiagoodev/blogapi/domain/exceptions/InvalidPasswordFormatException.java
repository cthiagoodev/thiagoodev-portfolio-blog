package br.com.thiagoodev.blogapi.domain.exceptions;

public class InvalidPasswordFormatException extends RuntimeException {
    public InvalidPasswordFormatException(String message) {
        super(message);
    }

    public InvalidPasswordFormatException() {
        super("Invalid password format");
    }
}
