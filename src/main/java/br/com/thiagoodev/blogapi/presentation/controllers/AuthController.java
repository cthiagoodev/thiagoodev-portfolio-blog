package br.com.thiagoodev.blogapi.presentation.controllers;

import br.com.thiagoodev.blogapi.domain.entities.JwtToken;
import br.com.thiagoodev.blogapi.domain.services.AuthService;
import br.com.thiagoodev.blogapi.presentation.dtos.LoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@RequestBody LoginDto dto) {
        JwtToken token = authService.authenticate(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(token);
    }
}
