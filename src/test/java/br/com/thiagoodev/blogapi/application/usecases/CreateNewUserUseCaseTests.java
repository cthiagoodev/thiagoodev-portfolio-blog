package br.com.thiagoodev.blogapi.application.usecases;

import br.com.thiagoodev.blogapi.domain.entities.User;
import br.com.thiagoodev.blogapi.domain.entities.UserPermission;
import br.com.thiagoodev.blogapi.domain.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateNewUserUseCase Tests")
public class CreateNewUserUseCaseTests {
    @Mock
    private UserService userService;
    @InjectMocks
    private CreateNewUserUseCase createNewUserUseCase;

    private User newUser;

    @BeforeEach
    void setUp() {
        ArrayList<UserPermission> permissions = new ArrayList<>();
        permissions.add(UserPermission.USER);

        newUser = new User(
            UUID.randomUUID().toString(),
            "New User",
            "newuser",
            "plainPassword",
            "new@example.com",
            true,
            "99999999999",
            permissions,
            LocalDateTime.now(),
            null,
            null
        );
    }

    @Test
    @DisplayName("Should delegate to UserService.create and return created user")
    void shouldDelegateToService() {
        when(userService.create(newUser)).thenReturn(newUser);

        User result = createNewUserUseCase.call(newUser);

        assertNotNull("Result should not be null", result);
        assertEquals(newUser, result);
        verify(userService).create(newUser);
    }
}
