package br.com.thiagoodev.portfolio.domain.exceptions;

public class UserHasDeletedException extends RuntimeException {
    public UserHasDeletedException(String message) {
        super(message);
    }

    public UserHasDeletedException() {
        super("User has been deleted");
    }
}
