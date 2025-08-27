package br.com.thiagoodev.blogapi.domain.exceptions;

public class JwtAuthenticationException extends RuntimeException {
    public JwtAuthenticationException(String message) {
        super(message);
    }

    public JwtAuthenticationException() {
        super("On error in JWT authentication");
    }
}
