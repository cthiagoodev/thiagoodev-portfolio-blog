package br.com.thiagoodev.blogapi.domain.services;

import br.com.thiagoodev.blogapi.domain.entities.JwtToken;

public interface AuthService {
    JwtToken authenticate(String email, String password);
}
