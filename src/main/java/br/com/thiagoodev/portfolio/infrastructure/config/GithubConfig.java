package br.com.thiagoodev.portfolio.infrastructure.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class GithubConfig {
    @Value("${github.uri}")
    private String uri;
}
