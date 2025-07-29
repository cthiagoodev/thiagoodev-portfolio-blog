package br.com.thiagoodev.blogapi.presentation.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {

    private String name;
    private String username;
    private String password;
    private String email;
    private String phone;
}
