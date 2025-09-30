package br.com.thiagoodev.portfolio.infrastructure.services.github;

import br.com.thiagoodev.portfolio.infrastructure.config.GithubConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GithubService {
    private final WebClient client;
    private final GithubConfig config;

    public GithubService(
        WebClient client,
        GithubConfig config
    ) {
        this.client = client;
        this.config = config;
    }

    public Mono<List<GithubProject>> getProjects() {
        return client.get()
            .uri(config.getUri())
            .retrieve()
            .bodyToFlux(GithubProject.class)
            .collectList()
            .onErrorResume(e -> Mono.empty());
    }
}
