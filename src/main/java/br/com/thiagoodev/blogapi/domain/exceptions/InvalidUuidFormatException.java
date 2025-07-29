package br.com.thiagoodev.blogapi.domain.exceptions;

public class InvalidUuidFormatException extends RuntimeException {
    public InvalidUuidFormatException(String message) {
        super(message);
    }

    public InvalidUuidFormatException() {
        super("Invalid UUID format");
    }
}
