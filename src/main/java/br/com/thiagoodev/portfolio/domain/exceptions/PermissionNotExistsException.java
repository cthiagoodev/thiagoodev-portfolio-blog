package br.com.thiagoodev.portfolio.domain.exceptions;

public class PermissionNotExistsException extends RuntimeException {
    public PermissionNotExistsException(String message) {
        super(message);
    }

    public PermissionNotExistsException() {
        super("Permission not exists");
    }
}
