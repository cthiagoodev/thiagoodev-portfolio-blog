package br.com.thiagoodev.blogapi.domain.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.AssertionErrors.assertFalse;

@DisplayName("User Entity Tests")
public class UserTests {

    private String uuid;
    private String name;
    private String username;
    private String password;
    private String email;
    private boolean isVerified;
    private String phone;
    private ArrayList<UserPermission> permissions;

    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID().toString();
        name = "Test User";
        username = "testuser";
        password = "password123";
        email = "test@example.com";
        isVerified = true;
        phone = "1234567890";
        permissions = new ArrayList<>();
        permissions.add(UserPermission.USER);
    }

    @Test
    @DisplayName("Should create a new user with valid arguments")
    void shouldCreateUserSuccessfullyWithValidArguments() {
        User user = new User(
            uuid,
            name,
            username,
            password,
            email,
            isVerified,
            phone,
            permissions
        );

        assertNotNull(user);
        assertEquals(uuid, user.getUuid());
        assertEquals(name, user.getName());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(email, user.getEmail());
        assertEquals(isVerified, user.getIsVerified());
        assertEquals(phone, user.getPhone());
        assertEquals(permissions, user.getPermissions());
    }

    @ParameterizedTest(name = "{0} should throw IllegalArgumentException when empty")
    @MethodSource("provideEmptyConstructorArguments")
    @DisplayName("Should throw IllegalArgumentException for empty constructor arguments")
    void shouldThrowIllegalArgumentExceptionForEmptyConstructorArguments(
        String fieldName,
        String uuidArg,
        String nameArg,
        String usernameArg,
        String passwordArg,
        String emailArg,
        String phoneArg,
        ArrayList<UserPermission> permissionsArg
    ) {
        assertThrows(IllegalArgumentException.class, () -> {
            new User(uuidArg, nameArg, usernameArg, passwordArg, emailArg, isVerified, phoneArg, permissionsArg);
        });
    }

    private static Stream<Arguments> provideEmptyConstructorArguments() {
        String baseUuid = UUID.randomUUID().toString();
        String baseName = "Test User";
        String baseUsername = "testuser";
        String basePassword = "password123";
        String baseEmail = "test@example.com";
        String basePhone = "1234567890";
        ArrayList<UserPermission> basePermissions = new ArrayList<>();
        basePermissions.add(UserPermission.USER);

        return Stream.of(
            Arguments.of("UUID", "", baseName, baseUsername, basePassword, baseEmail, basePhone, basePermissions),
            Arguments.of("Name", baseUuid, "", baseUsername, basePassword, baseEmail, basePhone, basePermissions),
            Arguments.of("Username", baseUuid, baseName, "", basePassword, baseEmail, basePhone, basePermissions),
            Arguments.of("Password", baseUuid, baseName, baseUsername, "", baseEmail, basePhone, basePermissions),
            Arguments.of("Email", baseUuid, baseName, baseUsername, basePassword, "", basePhone, basePermissions),
            Arguments.of("Phone", baseUuid, baseName, baseUsername, basePassword, baseEmail, "", basePermissions),
            Arguments.of("Permissions", baseUuid, baseName, baseUsername, basePassword, baseEmail, basePhone, new ArrayList<>())
        );
    }

    @Test
    @DisplayName("Should update Name correctly")
    void shouldUpdateNameCorrectly() {
        User user = new User(
            uuid,
            name,
            username,
            password,
            email,
            isVerified,
            phone,
            permissions
        );

        String newName = "New Name";
        user.setName(newName);
        assertEquals(newName, user.getName());
    }

    @Test
    @DisplayName("Should update Username correctly")
    void shouldUpdateUsernameCorrectly() {
        User user = new User(
            uuid,
            name,
            username,
            password,
            email,
            isVerified,
            phone,
            permissions
        );

        String newUsername = "newusername";
        user.setUsername(newUsername);
        assertEquals(newUsername, user.getUsername());
    }

    @Test
    @DisplayName("Should update Password correctly")
    void shouldUpdatePasswordCorrectly() {
        User user = new User(
            uuid,
            name,
            username,
            password,
            email,
            isVerified,
            phone,
            permissions
        );

        String newPassword = "newpassword";
        user.setPassword(newPassword);
        assertEquals(newPassword, user.getPassword());
    }

    @Test
    @DisplayName("Should update IsVerified status correctly")
    void shouldUpdateIsVerifiedCorrectly() {
        User user = new User(
            uuid,
            name,
            username,
            password,
            email,
            isVerified,
            phone,
            permissions
        );

        user.setIsVerified(false);
        assertFalse(null, user.getIsVerified());
    }

    @Test
    @DisplayName("Should update Phone correctly")
    void shouldUpdatePhoneCorrectly() {
        User user = new User(
            uuid,
            name,
            username,
            password,
            email,
            isVerified,
            phone,
            permissions
        );
        String newPhone = "0987654321";
        user.setPhone(newPhone);
        assertEquals(newPhone, user.getPhone());
    }

    @Test
    @DisplayName("Should update Email and set IsVerified to false")
    void shouldUpdateEmailAndSetIsVerifiedToFalse() {
        User user = new User(
            uuid,
            name,
            username,
            password,
            email,
            isVerified,
            phone,
            permissions
        );
        String newEmail = "new@example.com";
        user.setEmail(newEmail);
        assertEquals(newEmail, user.getEmail());
        assertFalse(null, user.getIsVerified());
    }

    @Test
    @DisplayName("Should add a new permission")
    void shouldAddNewPermission() {
        User user = new User(
            uuid,
            name,
            username,
            password,
            email,
            isVerified,
            phone,
            permissions
        );
        assertTrue(user.getPermissions().contains(UserPermission.USER));
        assertEquals(1, user.getPermissions().size());

        user.addPermission(UserPermission.ADMIN);
        assertTrue(user.getPermissions().contains(UserPermission.ADMIN));
        assertEquals(2, user.getPermissions().size());
    }

    @Test
    @DisplayName("Should not add an existing permission")
    void shouldNotAddExistingPermission() {
        permissions.add(UserPermission.MODERATOR);

        User user = new User(
            uuid,
            name,
            username,
            password,
            email,
            isVerified,
            phone,
            permissions
        );

        user.addPermission(UserPermission.MODERATOR);
        assertEquals(2, user.getPermissions().size());
        assertTrue(user.getPermissions().contains(UserPermission.USER));
        assertTrue(user.getPermissions().contains(UserPermission.MODERATOR));
    }

    @Test
    @DisplayName("Should remove an existing permission")
    void shouldRemoveExistingPermission() {
        permissions.add(UserPermission.MODERATOR);

        User user = new User(
            uuid,
            name,
            username,
            password,
            email,
            isVerified,
            phone,
            permissions
        );

        user.removePermission(UserPermission.USER);
        assertFalse(null, user.getPermissions().contains(UserPermission.USER));
        assertEquals(1, user.getPermissions().size());
    }

    @Test
    @DisplayName("Should not remove a non-existing permission")
    void shouldNotRemoveNonExistingPermission() {
        User user = new User(
            uuid,
            name,
            username,
            password,
            email,
            isVerified,
            phone,
            permissions
        );

        user.removePermission(UserPermission.ADMIN);
        assertEquals(1, user.getPermissions().size());
        assertTrue(user.getPermissions().contains(UserPermission.USER));
    }

    @Test
    @DisplayName("isAdmin should return true when ADMIN permission is present")
    void isAdminShouldReturnTrueWhenAdminPermissionIsPresent() {
        permissions.add(UserPermission.ADMIN);
        User user = new User(uuid, name, username, password, email, isVerified, phone, permissions);
        assertTrue(user.isAdmin());
    }

    @Test
    @DisplayName("isAdmin should return false when ADMIN permission is not present")
    void isAdminShouldReturnFalseWhenAdminPermissionIsNotPresent() {
        User user = new User(uuid, name, username, password, email, isVerified, phone, permissions);
        assertFalse(null, user.isAdmin());
    }

    @Test
    @DisplayName("isModerator should return true when MODERATOR permission is present")
    void isModeratorShouldReturnTrueWhenModeratorPermissionIsPresent() {
        permissions.add(UserPermission.MODERATOR);
        User user = new User(uuid, name, username, password, email, isVerified, phone, permissions);
        assertTrue(user.isModerator());
    }

    @Test
    @DisplayName("isModerator should return false when MODERATOR permission is not present")
    void isModeratorShouldReturnFalseWhenModeratorPermissionIsNotPresent() {
        User user = new User(uuid, name, username, password, email, isVerified, phone, permissions);
        assertFalse(null, user.isModerator());
    }
}
