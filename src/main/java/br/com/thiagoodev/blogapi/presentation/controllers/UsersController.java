package br.com.thiagoodev.blogapi.presentation.controllers;

import br.com.thiagoodev.blogapi.application.usecases.CreateNewUserUseCase;
import br.com.thiagoodev.blogapi.application.usecases.GetUserByUuidUseCase;
import br.com.thiagoodev.blogapi.domain.entities.User;
import br.com.thiagoodev.blogapi.presentation.dtos.CreateUserDto;
import br.com.thiagoodev.blogapi.presentation.mappers.UserDtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final GetUserByUuidUseCase getUserByUuidUseCase;
    private final CreateNewUserUseCase createNewUserUseCase;

    public UsersController(
        GetUserByUuidUseCase getUserByUuidUseCase,
        CreateNewUserUseCase createNewUserUseCase
    ) {
        this.getUserByUuidUseCase = getUserByUuidUseCase;
        this.createNewUserUseCase = createNewUserUseCase;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<User> getUserByUuid(@PathVariable String uuid) {
        User user = getUserByUuidUseCase.call(uuid);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody CreateUserDto dto) {
        UserDtoMapper mapper = UserDtoMapper.INSTANCE;
        User user = createNewUserUseCase.call(mapper.userToCreateUserDto(dto));
        return ResponseEntity.status(201).body(user);
    }
}
