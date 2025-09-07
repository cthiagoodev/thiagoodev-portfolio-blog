package br.com.thiagoodev.blogapi.presentation.controllers;

import br.com.thiagoodev.blogapi.application.dto.UserResponseDto;
import br.com.thiagoodev.blogapi.application.services.UserService;
import br.com.thiagoodev.blogapi.domain.entities.User;
import br.com.thiagoodev.blogapi.presentation.mappers.UserToUserResponseDtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UserService userService;
    private final UserToUserResponseDtoMapper userToUserResponseDtoMapper;

    UsersController(
        UserService userService,
        UserToUserResponseDtoMapper userToUserResponseDtoMapper
    ) {
        this.userService = userService;
        this.userToUserResponseDtoMapper = userToUserResponseDtoMapper;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserResponseDto> getByUuid(@PathVariable("uuid") String uuid) {
        User user = userService.getByUuid(uuid);
        UserResponseDto response = userToUserResponseDtoMapper.map(user);
        return ResponseEntity.ok(response);
    }
}
