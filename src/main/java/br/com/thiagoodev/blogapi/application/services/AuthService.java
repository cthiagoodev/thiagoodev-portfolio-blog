package br.com.thiagoodev.blogapi.application.services;

import br.com.thiagoodev.blogapi.domain.data_values.JwtToken;

public interface AuthService {
    JwtToken authenticate(String email, String password);
}
