package br.com.thiagoodev.blogapi.application.usecases;

import br.com.thiagoodev.blogapi.domain.services.UserService;
import org.springframework.stereotype.Component;

@Component
public class DeleteUserUseCase {
    private final UserService userService;

    public DeleteUserUseCase(UserService userService) {
        this.userService = userService;
    }

    public boolean call(String uuid) { return userService.delete(uuid); }
}
