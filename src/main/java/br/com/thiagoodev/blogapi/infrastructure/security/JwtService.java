package br.com.thiagoodev.blogapi.infrastructure.security;

import br.com.thiagoodev.blogapi.infrastructure.config.JwtConfig;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {
    private final JwtConfig config;
    private final Algorithm algorithm;
    private static final long EXPIRATION_TIME = 86400000L;

    public JwtService(JwtConfig config) {
        this.config = config;
        this.algorithm = Algorithm.HMAC256(config.getSecret());
    }

    public String generateToken(String subject) {
        return JWT.create()
                .withIssuer(this.config.getIssuer())
                .withSubject(subject)
                .withExpiresAt(this.expiresAt())
                .sign(algorithm);
    }

    public String getSubject(String token) {
        return JWT.require(algorithm)
            .withIssuer(this.config.getIssuer())
            .build()
            .verify(token)
            .getSubject();
    }

    private Date expiresAt() {
        return Date.from(Instant.now().plusMillis(EXPIRATION_TIME));
    }
}
