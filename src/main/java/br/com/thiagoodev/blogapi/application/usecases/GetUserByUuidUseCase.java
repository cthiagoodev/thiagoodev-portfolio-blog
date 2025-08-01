package br.com.thiagoodev.blogapi.application.usecases;

import br.com.thiagoodev.blogapi.domain.entities.User;
import br.com.thiagoodev.blogapi.domain.services.UserService;
import org.springframework.stereotype.Component;

@Component
public class GetUserByUuidUseCase {
    private final UserService userService;

    public GetUserByUuidUseCase(UserService userService) {
        this.userService = userService;
    }

    public User call(String uuid) { return userService.getByUuid(uuid); }
}
