package br.com.thiagoodev.blogapi.infrastructure.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JwtConfig {
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.secret}")
    private String secret;
}
