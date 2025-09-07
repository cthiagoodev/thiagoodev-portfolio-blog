package br.com.thiagoodev.blogapi.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(
    @NotBlank(message = "Name is required")
    String name,
    @NotBlank(message = "Username is required")
    String username,
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email,
    @NotBlank(message = "Password is required")
    String password,
    @NotBlank(message = "Phone is required")
    String phone
) { }
