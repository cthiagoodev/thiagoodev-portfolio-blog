package br.com.thiagoodev.blogapi.presentation.controllers;

import br.com.thiagoodev.blogapi.application.usecases.GetUserByUuidUseCase;
import br.com.thiagoodev.blogapi.domain.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/users")
public class UsersController {
    private final GetUserByUuidUseCase getUserByUuidUseCase;

    public UsersController(GetUserByUuidUseCase getUserByUuidUseCase) {
        this.getUserByUuidUseCase = getUserByUuidUseCase;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<User> getUserByUuid(@PathVariable("uuid") String uuid) {
        User user = getUserByUuidUseCase.call(uuid);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
