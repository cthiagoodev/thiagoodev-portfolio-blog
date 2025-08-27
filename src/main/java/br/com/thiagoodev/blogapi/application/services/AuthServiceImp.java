package br.com.thiagoodev.blogapi.application.services;

import br.com.thiagoodev.blogapi.domain.entities.JwtToken;
import br.com.thiagoodev.blogapi.domain.services.AuthService;
import br.com.thiagoodev.blogapi.infrastructure.security.JwtTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImp implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public AuthServiceImp(
        AuthenticationManager authenticationManager,
        JwtTokenService jwtTokenService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public JwtToken authenticate(String email, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtTokenService.generateToken(userDetails);
        LocalDateTime expirationDate = jwtTokenService.getExpirationDateFromToken(token);

        return new JwtToken(token, expirationDate);
    }
}
