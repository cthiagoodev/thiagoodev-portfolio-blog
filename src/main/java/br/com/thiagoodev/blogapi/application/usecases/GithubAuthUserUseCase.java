package br.com.thiagoodev.blogapi.application.usecases;

import br.com.thiagoodev.blogapi.domain.entities.ExternalToken;
import br.com.thiagoodev.blogapi.infrastructure.services.GithubService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class GithubAuthUserUseCase {
    private final GithubService githubService;

    public GithubAuthUserUseCase(GithubService githubService) {
        this.githubService = githubService;
    }

    public ExternalToken call(OAuth2AuthenticationToken authToken) { return githubService.authenticate(authToken); }
}
