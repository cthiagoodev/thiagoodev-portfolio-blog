package br.com.thiagoodev.blogapi.infrastructure.services;

import br.com.thiagoodev.blogapi.domain.entities.ExternalToken;
import br.com.thiagoodev.blogapi.infrastructure.security.JwtTokenService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GithubService {
    private final JwtTokenService jwtTokenService;
    private final OAuth2AuthorizedClientService authorizedClientService;

    public GithubService(
        JwtTokenService jwtTokenService,
        OAuth2AuthorizedClientService authorizedClientService
    ) {
        this.jwtTokenService = jwtTokenService;
        this.authorizedClientService = authorizedClientService;
    }

    public ExternalToken authenticate(OAuth2AuthenticationToken authToken) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
            authToken.getAuthorizedClientRegistrationId(),
            authToken.getName()
        );

        String githubToken = client.getAccessToken().getTokenValue();
        String jwt = jwtTokenService.generateToken(authToken.getPrincipal());
        LocalDateTime expirationDate = jwtTokenService.getExpirationDateFromToken(jwt);

        return new ExternalToken(
            githubToken,
            jwt,
            expirationDate
        );
    }
}
