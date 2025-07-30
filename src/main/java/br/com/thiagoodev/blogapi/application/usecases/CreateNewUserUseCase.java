package br.com.thiagoodev.blogapi.application.usecases;

import br.com.thiagoodev.blogapi.domain.entities.User;
import br.com.thiagoodev.blogapi.domain.services.UserService;
import org.springframework.stereotype.Component;

@Component
public class CreateNewUserUseCase {
    private final UserService userService;

    public CreateNewUserUseCase(UserService userService) {
        this.userService = userService;
    }

    public User call(User newUser) { return userService.create(newUser); }
}
