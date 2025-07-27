package br.com.thiagoodev.blogapi.application.usecases;

import br.com.thiagoodev.blogapi.domain.entities.User;
import br.com.thiagoodev.blogapi.domain.services.UserService;

public class GetUserByUuidUseCase {
    private final UserService userService;

    GetUserByUuidUseCase(UserService userService) {
        this.userService = userService;
    }

    User call(String uuid) {
        return userService.getByUuid(uuid);
    }
}
