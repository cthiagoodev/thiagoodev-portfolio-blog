package br.com.thiagoodev.blogapi.domain.entities;

import java.time.LocalDateTime;

public class JwtToken {
    private final String token;
    private final LocalDateTime expirationDate;

    public JwtToken(String token, LocalDateTime expirationDate) {
        this.token = token;
        this.expirationDate = expirationDate;
    }

    public String getToken() { return this.token; }
    public LocalDateTime getExpirationDate() { return this.expirationDate; }
}
