package br.com.thiagoodev.blogapi.infrastructure.services.github;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GithubService {
    private final WebClient client;

    public GithubService(WebClient client) {
        this.client = client;
    }

    public Mono<List<GithubProject>> getProjects() {
        return client.get()
            .uri("https://api.github.com/users/cthiagoodev/repos?per_page=100")
            .retrieve()
            .bodyToFlux(GithubProject.class)
            .collectList()
            .onErrorResume(e -> Mono.empty());
    }
}
