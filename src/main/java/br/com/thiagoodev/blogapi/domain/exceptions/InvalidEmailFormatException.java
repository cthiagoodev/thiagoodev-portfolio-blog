package br.com.thiagoodev.blogapi.domain.exceptions;

public class InvalidEmailFormatException extends RuntimeException {
    public InvalidEmailFormatException(String message) {
        super(message);
    }

    public InvalidEmailFormatException() {
        super("Invalid E-mail format");
    }
}
