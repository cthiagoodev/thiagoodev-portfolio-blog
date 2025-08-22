package br.com.thiagoodev.blogapi.domain.exceptions;

public class PermissionNotExistsException extends RuntimeException {
    public PermissionNotExistsException(String message) {
        super(message);
    }

    public PermissionNotExistsException() {
        super("Permission not exists");
    }
}
