package br.com.thiagoodev.blogapi.presentation.controllers;

import br.com.thiagoodev.blogapi.application.dto.UserResponseDto;
import br.com.thiagoodev.blogapi.application.services.UserService;
import br.com.thiagoodev.blogapi.domain.entities.User;
import br.com.thiagoodev.blogapi.presentation.mappers.UserToUserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UserService userService;
    private final UserToUserResponseDto userToUserResponseDto;

    UsersController(
        UserService userService,
        UserToUserResponseDto userToUserResponseDto
    ) {
        this.userService = userService;
        this.userToUserResponseDto = userToUserResponseDto;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserResponseDto> getByUuid(@PathVariable("uuid") String uuid) {
        User user = userService.getByUuid(uuid);
        UserResponseDto response = userToUserResponseDto.map(user);
        return ResponseEntity.ok(response);
    }
}
