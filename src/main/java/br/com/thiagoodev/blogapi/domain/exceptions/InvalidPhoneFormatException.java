package br.com.thiagoodev.blogapi.domain.exceptions;

public class InvalidPhoneFormatException extends RuntimeException {
    public InvalidPhoneFormatException(String message) {
        super(message);
    }

    public InvalidPhoneFormatException() {
        super("Invalid phone format");
    }
}
