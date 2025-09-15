package br.com.thiagoodev.blogapi.application.services;

import br.com.thiagoodev.blogapi.infrastructure.security.JwtService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JwtService jwtService;

    public AuthService(JwtService jwtService) {
        this.jwtService = jwtService;
    }
}
