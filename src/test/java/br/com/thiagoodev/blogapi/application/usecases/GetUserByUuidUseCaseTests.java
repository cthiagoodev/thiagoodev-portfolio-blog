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

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetUserByUuidUseCase Tests")
public class GetUserByUuidUseCaseTests {
    @Mock
    private UserService userService;
    @InjectMocks
    private GetUserByUuidUseCase getUserByUuidUseCase;

    private String uuid;
    private User mockUser;

    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID().toString();
        ArrayList<UserPermission> permissions = new ArrayList<>();
        permissions.add(UserPermission.USER);

        mockUser = new User(
            uuid,
            "Test User",
            "testuser",
            "password123",
            "test@example.com",
            true,
            "1234567890",
            permissions
        );
    }

    @Test
    @DisplayName("Should return a user by UUID")
    void shouldReturnAUserBydUuid() {
        when(getUserByUuidUseCase.call(uuid)).thenReturn(mockUser);

        User user = getUserByUuidUseCase.call(uuid);

        assertNotNull("The user should not be null", user);
        assertEquals(mockUser, user, "The returned user should match the expected user");
        verify(userService).getByUuid(uuid);
    }
}
