package br.com.thiagoodev.portfolio.application.dto;

import jakarta.validation.constraints.Email;

public record UpdateUserDto(
    String name,
    String username,
    @Email(message = "Invalid email format")
    String email,
    String phone
) { }
