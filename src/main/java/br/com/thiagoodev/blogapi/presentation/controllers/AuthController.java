package br.com.thiagoodev.blogapi.presentation.controllers;

import br.com.thiagoodev.blogapi.application.usecases.AuthUserUseCase;
import br.com.thiagoodev.blogapi.application.usecases.GithubAuthUserUseCase;
import br.com.thiagoodev.blogapi.domain.entities.ExternalToken;
import br.com.thiagoodev.blogapi.domain.entities.JwtToken;
import br.com.thiagoodev.blogapi.presentation.dtos.LoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthUserUseCase authUserUseCase;
    private final GithubAuthUserUseCase githubAuthUserUseCase;

    public AuthController(
        AuthUserUseCase authUserUseCase,
        GithubAuthUserUseCase githubAuthUserUseCase
    ) {
        this.authUserUseCase = authUserUseCase;
        this.githubAuthUserUseCase = githubAuthUserUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@RequestBody LoginDto dto) {
        JwtToken token = authUserUseCase.call(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/github")
    public ResponseEntity<ExternalToken> githubLogin(@RequestBody OAuth2AuthenticationToken authToken) {
        ExternalToken token = githubAuthUserUseCase.call(authToken);
        return ResponseEntity.ok(token);
    }
}
