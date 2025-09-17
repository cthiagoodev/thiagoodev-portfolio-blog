package br.com.thiagoodev.blogapi.application.dto;

import lombok.Builder;

@Builder
public record JwtTokenDto(
    String access
) { }
