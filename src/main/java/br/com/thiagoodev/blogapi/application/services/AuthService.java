package br.com.thiagoodev.blogapi.application.services;

import br.com.thiagoodev.blogapi.application.dto.JwtTokenDto;
import br.com.thiagoodev.blogapi.application.dto.LoginDto;
import br.com.thiagoodev.blogapi.domain.entities.User;
import br.com.thiagoodev.blogapi.infrastructure.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(
        JwtService jwtService,
        AuthenticationManager authenticationManager
    ) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public JwtTokenDto generateToken(LoginDto dto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(user.getEmail());

        return JwtTokenDto.builder()
            .access(token)
            .build();
    }
}
