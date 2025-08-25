package br.com.thiagoodev.blogapi.presentation.dtos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDto {
    private String uuid;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;
    @NotBlank(message = "Phone is required")
    @Digits(integer = 11, fraction = 0, message = "Phone is invalid")
    private String phone;
}
