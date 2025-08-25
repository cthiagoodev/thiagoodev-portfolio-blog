package br.com.thiagoodev.blogapi.application.usecases;

import br.com.thiagoodev.blogapi.domain.entities.User;
import br.com.thiagoodev.blogapi.domain.services.UserService;
import org.springframework.stereotype.Component;

@Component
public class UpdateUserUseCase {
    private final UserService userService;

    public UpdateUserUseCase(UserService userService) {
        this.userService = userService;
    }

    public User call(User user) { return userService.update(user); }
}
