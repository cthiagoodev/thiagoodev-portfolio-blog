package br.com.thiagoodev.blogapi.infrastructure.services.github;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public record GithubProject(
    Long id,
    String name,
    @JsonProperty("full_name")
    String fullName,
    @JsonProperty("html_url")
    String htmlUrl,
    String description,
    String language,
    Set<String> topics,
    String homepage,
    @JsonProperty("pushed_at")
    String pushedAt,
    @JsonProperty("created_at")
    String createdAt,
    @JsonProperty("updated_at")
    String updatedAt
) { }
