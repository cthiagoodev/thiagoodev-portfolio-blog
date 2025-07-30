package br.com.thiagoodev.blogapi.presentation.controllers;

import br.com.thiagoodev.blogapi.domain.entities.User;
import br.com.thiagoodev.blogapi.domain.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/users")
public class UsersController {
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<User> getUserByUuid(@PathVariable("uuid") String uuid) {
        User user = userService.getByUuid(uuid);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
