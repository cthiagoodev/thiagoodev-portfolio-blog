package br.com.thiagoodev.blogapi.application.dto;

import br.com.thiagoodev.blogapi.domain.entities.Permission;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record UserResponseDto(
    UUID uuid,
    String name,
    String username,
    String email,
    boolean isVerified,
    String phone,
    Set<Permission> permissions,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt
) {
}
