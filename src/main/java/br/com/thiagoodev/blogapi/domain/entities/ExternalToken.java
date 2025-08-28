package br.com.thiagoodev.blogapi.domain.entities;

import java.time.LocalDateTime;

public class ExternalToken extends JwtToken {
    private final String externalToken;

    public ExternalToken(
        String externalToken,
        String token,
        LocalDateTime expirationDate
    ) {
        super(token, expirationDate);
        this.externalToken = externalToken;
    }

    public String getExternalToken() { return this.externalToken; }
}
