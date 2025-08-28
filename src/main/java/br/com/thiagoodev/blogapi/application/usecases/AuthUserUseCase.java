package br.com.thiagoodev.blogapi.application.usecases;

import br.com.thiagoodev.blogapi.domain.entities.JwtToken;
import br.com.thiagoodev.blogapi.domain.services.AuthService;
import org.springframework.stereotype.Component;

@Component
public class AuthUserUseCase {
    private final AuthService authService;

    public AuthUserUseCase(AuthService authService) {
        this.authService = authService;
    }

    public JwtToken call(String email, String password) { return authService.authenticate(email, password); }
}
