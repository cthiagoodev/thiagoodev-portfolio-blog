package br.com.thiagoodev.blogapi.application.services;

import br.com.thiagoodev.blogapi.domain.entities.User;
import br.com.thiagoodev.blogapi.domain.entities.UserPermission;
import br.com.thiagoodev.blogapi.domain.exceptions.InvalidEmailFormatException;
import br.com.thiagoodev.blogapi.domain.exceptions.InvalidPhoneFormatException;
import br.com.thiagoodev.blogapi.domain.exceptions.InvalidUuidFormatException;
import br.com.thiagoodev.blogapi.domain.exceptions.UserNotEnabledException;
import br.com.thiagoodev.blogapi.domain.exceptions.UserNotExistsException;
import br.com.thiagoodev.blogapi.infrastructure.data.models.PermissionModel;
import br.com.thiagoodev.blogapi.infrastructure.data.models.UserModel;
import br.com.thiagoodev.blogapi.infrastructure.data.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserServiceImp Tests")
public class UserServiceImpTests {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UserServiceImp userServiceImp;

    private String uuid;
    private UserModel userModel;

    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID().toString();
        userModel = buildUserModel(UUID.fromString(uuid), true);
    }

    private UserModel buildUserModel(UUID id, boolean enabled) {
        UserModel model = new UserModel();
        model.setUuid(id);
        model.setName("John Doe");
        model.setUsername("johndoe");
        model.setPassword("$2a$10$hashed");
        model.setEmail("john@example.com");
        model.setVerified(enabled);
        model.setPhone("11912345678");
        model.setCreatedAt(LocalDateTime.now().minusDays(1));
        model.setUpdatedAt(null);
        model.setDeletedAt(null);

        PermissionModel p = new PermissionModel();
        p.setAuthority("ROLE_USER");
        model.setPermissions(new HashSet<>(Set.of(p)));
        return model;
    }

    private User buildDomainUser(String id, String email, String phone, String username) {
        return new User(
            id,
            "John Doe",
            username,
            "plainpass",
            email,
            true,
            phone,
            new ArrayList<>(List.of(UserPermission.USER)),
            LocalDateTime.now().minusDays(2),
            null,
            null
        );
    }

    @Test
    @DisplayName("getByUuid should throw InvalidUuidFormatException for invalid uuid format")
    void getByUuidShouldThrowForInvalidUuid() {
        assertThrows(InvalidUuidFormatException.class, () -> userServiceImp.getByUuid("not-a-uuid"));
    }

    @Test
    @DisplayName("getByUuid should return mapped domain user when repository finds user")
    void getByUuidShouldReturnUser() {
        when(usersRepository.findByUuid(UUID.fromString(uuid))).thenReturn(Optional.of(userModel));

        User result = userServiceImp.getByUuid(uuid);

        assertNotNull(result);
        assertEquals(userModel.getUuid().toString(), result.getUuid());
        assertEquals(userModel.getUsername(), result.getUsername());
        assertEquals(userModel.getEmail(), result.getEmail());
        assertEquals(1, result.getPermissions().size());
        verify(usersRepository).findByUuid(UUID.fromString(uuid));
    }

    @Test
    @DisplayName("getByUuid should throw UserNotExistsException when repository returns empty")
    void getByUuidShouldThrowWhenNotFound() {
        when(usersRepository.findByUuid(UUID.fromString(uuid))).thenReturn(Optional.empty());
        assertThrows(UserNotExistsException.class, () -> userServiceImp.getByUuid(uuid));
    }

    @Test
    @DisplayName("getByEmail should validate email and return user")
    void getByEmailShouldReturnUser() {
        when(usersRepository.findByEmail("john@example.com")).thenReturn(Optional.of(userModel));
        User result = userServiceImp.getByEmail("john@example.com");
        assertEquals("john@example.com", result.getEmail());
        verify(usersRepository).findByEmail("john@example.com");
    }

    @Test
    @DisplayName("getByEmail should throw InvalidEmailFormatException for bad email")
    void getByEmailShouldThrowForBadEmail() {
        assertThrows(InvalidEmailFormatException.class, () -> userServiceImp.getByEmail("bad-email"));
    }

    @Test
    @DisplayName("create should validate availability, hash password, save and return mapped user")
    void createShouldSaveAndReturnUser() {
        when(usersRepository.findByUuid(any())).thenReturn(Optional.empty());
        when(usersRepository.findByUsername("johndoe")).thenReturn(Optional.empty());
        when(usersRepository.findByEmail("john@example.com")).thenReturn(Optional.empty());
        when(usersRepository.findByPhone(anyString())).thenReturn(Optional.empty());
        when(usersRepository.save(any(UserModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User toCreate = buildDomainUser(uuid, "john@example.com", "abc", "johndoe");

        User created = userServiceImp.create(toCreate);

        assertNotNull(created);
        ArgumentCaptor<UserModel> captor = ArgumentCaptor.forClass(UserModel.class);
        verify(usersRepository).save(captor.capture());
        String savedPassword = captor.getValue().getPassword();
        assertTrue(savedPassword.startsWith("$2"));
        assertEquals(toCreate.getUsername(), created.getUsername());
        assertEquals(toCreate.getEmail(), created.getEmail());
        verify(usersRepository).findByUuid(UUID.fromString(uuid));
        verify(usersRepository).findByUsername("johndoe");
        verify(usersRepository).findByEmail("john@example.com");
        verify(usersRepository).findByPhone("abc");
    }

    @Test
    @DisplayName("update should update mutable fields and return domain user when enabled")
    void updateShouldWorkWhenEnabled() {
        when(usersRepository.findByUuid(UUID.fromString(uuid))).thenReturn(Optional.of(userModel));
        when(usersRepository.findByUsername("newusername")).thenReturn(Optional.empty());
        when(usersRepository.findByEmail("new@example.com")).thenReturn(Optional.empty());
        when(usersRepository.findByPhone("xyz")).thenReturn(Optional.empty());
        when(usersRepository.save(any(UserModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User update = buildDomainUser(uuid, "new@example.com", "xyz", "newusername");

        User updated = userServiceImp.update(update);
        assertEquals("newusername", updated.getUsername());
        assertEquals("new@example.com", updated.getEmail());
        verify(usersRepository).save(any(UserModel.class));
    }

    @Test
    @DisplayName("update should throw when user is disabled")
    void updateShouldThrowWhenDisabled() {
        UserModel disabled = buildUserModel(UUID.fromString(uuid), false);
        when(usersRepository.findByUuid(UUID.fromString(uuid))).thenReturn(Optional.of(disabled));

        User update = buildDomainUser(uuid, "new@example.com", "xyz", "newusername");
        assertThrows(UserNotEnabledException.class, () -> userServiceImp.update(update));
    }

    @Test
    @DisplayName("delete should delete when enabled and return true")
    void deleteShouldWorkWhenEnabled() {
        when(usersRepository.findByUuid(UUID.fromString(uuid))).thenReturn(Optional.of(userModel));
        boolean result = userServiceImp.delete(uuid);
        assertTrue(result);
        verify(usersRepository).deleteById(UUID.fromString(uuid));
    }

    @Test
    @DisplayName("delete should throw when disabled")
    void deleteShouldThrowWhenDisabled() {
        UserModel disabled = buildUserModel(UUID.fromString(uuid), false);
        when(usersRepository.findByUuid(UUID.fromString(uuid))).thenReturn(Optional.of(disabled));
        assertThrows(UserNotEnabledException.class, () -> userServiceImp.delete(uuid));
    }

    @Test
    @DisplayName("toDataModel should map fields and permissions correctly")
    void toDataModelShouldMapCorrectly() {
        User domain = new User(
            uuid,
            "Jane",
            "jane",
            "$2a$10$hash",
            "jane@example.com",
            true,
            "11987654321",
            new ArrayList<>(List.of(UserPermission.ADMIN, UserPermission.USER)),
            LocalDateTime.now().minusDays(3),
            LocalDateTime.now().minusDays(2),
            null
        );

        UserModel mapped = userServiceImp.toDataModel(domain);
        assertEquals(UUID.fromString(uuid), mapped.getUuid());
        assertEquals("jane", mapped.getUsername());
        assertEquals("jane@example.com", mapped.getEmail());
        assertEquals(2, mapped.getPermissions().size());
        assertTrue(mapped.getPermissions().stream().anyMatch(p -> Objects.equals(p.getAuthority(), "ROLE_ADMIN")));
        assertTrue(mapped.getPermissions().stream().anyMatch(p -> Objects.equals(p.getAuthority(), "ROLE_USER")));
    }

    // The two tests below expose the current phone validation inconsistency.
    @Test
    @DisplayName("getByPhone should throw InvalidPhoneFormatException when phone is invalid (expected behavior)")
    void getByPhoneShouldThrowForInvalidPhone_expectedBehavior() {
        // no repository interaction should be required if validation fails early
        assertThrows(InvalidPhoneFormatException.class, () -> userServiceImp.getByPhone("abc"));
    }

    @Test
    @DisplayName("getByPhone should return user when phone is valid (expected behavior)")
    void getByPhoneShouldReturnUser_expectedBehavior() {
        when(usersRepository.findByPhone("11912345678")).thenReturn(Optional.of(userModel));
        User result = userServiceImp.getByPhone("11912345678");
        assertNotNull(result);
        assertEquals("11912345678", result.getPhone());
    }
}
