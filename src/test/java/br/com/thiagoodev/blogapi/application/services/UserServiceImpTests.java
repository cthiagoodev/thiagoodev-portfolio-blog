package br.com.thiagoodev.blogapi.application.services;

import br.com.thiagoodev.blogapi.domain.exceptions.InvalidUuidFormatException;
import br.com.thiagoodev.blogapi.domain.exceptions.UserNotEnabledException;
import br.com.thiagoodev.blogapi.domain.exceptions.UserNotExistsException;
import br.com.thiagoodev.blogapi.domain.entities.Permission;
import br.com.thiagoodev.blogapi.domain.entities.User;
import br.com.thiagoodev.blogapi.infrastructure.persistence.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTests {

    @Mock
    UsersRepository usersRepository;

    @InjectMocks
    UserServiceImp userService;

    private UUID validUuid;
    private User enabledUser;
    private User disabledUser;

    @BeforeEach
    void setup() {
        validUuid = UUID.randomUUID();
        enabledUser = buildUserModel(validUuid, true, null);
        disabledUser = buildUserModel(UUID.randomUUID(), false, null);
    }

    @Nested
    class GetByUuid {
        @Test
        void success_with_valid_uuid() {
            when(usersRepository.findByUuid(validUuid)).thenReturn(Optional.of(enabledUser));
            var result = userService.getByUuid(validUuid.toString());
            assertNotNull(result);
            assertEquals(validUuid.toString(), result.getUuid());
            assertEquals(enabledUser.getEmail(), result.getEmail());
        }

        @Test
        void failure_with_null_uuid() {
            assertThrows(InvalidUuidFormatException.class, () -> userService.getByUuid(null));
        }

        @Test
        void failure_with_empty_uuid() {
            assertThrows(InvalidUuidFormatException.class, () -> userService.getByUuid(""));
        }

        @Test
        void failure_with_invalid_uuid_format() {
            assertThrows(InvalidUuidFormatException.class, () -> userService.getByUuid("invalid-uuid"));
        }

        @Test
        void failure_when_user_not_found() {
            when(usersRepository.findByUuid(validUuid)).thenReturn(Optional.empty());
            assertThrows(UserNotExistsException.class, () -> userService.getByUuid(validUuid.toString()));
        }
    }

    @Nested
    class GetByEmail {
        @Test
        void success_with_valid_email() {
            when(usersRepository.findByEmail(enabledUser.getEmail())).thenReturn(Optional.of(enabledUser));
            var result = userService.getByEmail(enabledUser.getEmail());
            assertNotNull(result);
            assertEquals(enabledUser.getEmail(), result.getEmail());
        }

        @Test
        void failure_with_invalid_email() {
            assertThrows(IllegalArgumentException.class, () -> userService.getByEmail("not-an-email"));
        }

        @Test
        void failure_when_user_not_found() {
            when(usersRepository.findByEmail(enabledUser.getEmail())).thenReturn(Optional.empty());
            assertThrows(UserNotExistsException.class, () -> userService.getByEmail(enabledUser.getEmail()));
        }
    }

    @Nested
    class GetByPhone {
        @Test
        void success_with_valid_phone() {
            when(usersRepository.findByPhone(enabledUser.getPhone())).thenReturn(Optional.of(enabledUser));
            var result = userService.getByPhone(enabledUser.getPhone());
            assertNotNull(result);
            assertEquals(enabledUser.getPhone(), result.getPhone());
        }

        @Test
        void failure_with_null_phone() {
            assertThrows(IllegalArgumentException.class, () -> userService.getByPhone(null));
        }

        @Test
        void failure_with_empty_phone() {
            assertThrows(IllegalArgumentException.class, () -> userService.getByPhone(""));
        }

        @Test
        void failure_when_user_not_found() {
            when(usersRepository.findByPhone(enabledUser.getPhone())).thenReturn(Optional.empty());
            assertThrows(UserNotExistsException.class, () -> userService.getByPhone(enabledUser.getPhone()));
        }
    }

    @Nested
    class CreateUser {
        @Test
        void success_creates_user_and_encrypts_password() {
            var newUser = new br.com.thiagoodev.blogapi.domain.data_values.User("John Doe", "john", "plainPass123", "john.doe@mail.com", "(11) 99999-9999");
            var savedModel = buildUserModel(UUID.fromString(newUser.getUuid()), true, null);
            when(usersRepository.save(any(User.class))).thenReturn(savedModel);
            var created = userService.create(newUser);
            assertNotNull(created);
            assertEquals(savedModel.getEmail(), created.getEmail());
            assertNotEquals("plainPass123", savedModel.getPassword());
            assertTrue(BCrypt.checkpw("plainPass123", savedModel.getPassword()));
            verify(usersRepository, times(1)).save(any(User.class));
        }

        @Test
        void failure_with_invalid_uuid() {
            var invalidUser = mock(br.com.thiagoodev.blogapi.domain.data_values.User.class);
            when(invalidUser.getUuid()).thenReturn("");
            assertThrows(InvalidUuidFormatException.class, () -> userService.create(invalidUser));
            verify(usersRepository, never()).save(any());
        }
    }

    @Nested
    class UpdateUser {
        @Test
        void success_updates_existing_enabled_user() {
            var incoming = mock(br.com.thiagoodev.blogapi.domain.data_values.User.class);
            when(incoming.getUuid()).thenReturn(validUuid.toString());
            when(incoming.getEmail()).thenReturn("new.mail@mail.com");
            when(incoming.getPhone()).thenReturn("(11) 98888-8888");
            when(usersRepository.findByUuid(validUuid)).thenReturn(Optional.of(enabledUser));
            var saved = buildUserModel(validUuid, true, null);
            saved.setEmail("new.mail@mail.com");
            saved.setUsername("new.mail@mail.com");
            saved.setName("new.mail@mail.com");
            saved.setPhone("(11) 98888-8888");
            when(usersRepository.save(any(User.class))).thenReturn(saved);
            var result = userService.update(incoming);
            assertNotNull(result);
            assertEquals("new.mail@mail.com", result.getEmail());
            verify(usersRepository, times(1)).findByUuid(validUuid);
            verify(usersRepository, times(1)).save(any(User.class));
        }

        @Test
        void failure_with_invalid_uuid() {
            var incoming = mock(br.com.thiagoodev.blogapi.domain.data_values.User.class);
            when(incoming.getUuid()).thenReturn(null);
            assertThrows(InvalidUuidFormatException.class, () -> userService.update(incoming));
            verify(usersRepository, never()).findByUuid(any());
            verify(usersRepository, never()).save(any());
        }

        @Test
        void failure_when_user_not_found() {
            var incoming = mock(br.com.thiagoodev.blogapi.domain.data_values.User.class);
            when(incoming.getUuid()).thenReturn(validUuid.toString());
            when(usersRepository.findByUuid(validUuid)).thenReturn(Optional.empty());
            assertThrows(UserNotExistsException.class, () -> userService.update(incoming));
        }

        @Test
        void failure_when_user_not_enabled() {
            var incoming = mock(br.com.thiagoodev.blogapi.domain.data_values.User.class);
            when(incoming.getUuid()).thenReturn(disabledUser.getUuid().toString());
            when(usersRepository.findByUuid(disabledUser.getUuid())).thenReturn(Optional.of(disabledUser));
            assertThrows(UserNotEnabledException.class, () -> userService.update(incoming));
            verify(usersRepository, never()).save(any());
        }
    }

    @Nested
    class DeleteUser {
        @Test
        void success_soft_deletes_enabled_user() {
            when(usersRepository.findByUuid(validUuid)).thenReturn(Optional.of(enabledUser));
            when(usersRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
            var deleted = userService.delete(validUuid.toString());
            assertTrue(deleted);
            verify(usersRepository, times(1)).findByUuid(validUuid);
            verify(usersRepository, times(1)).save(any(User.class));
        }

        @Test
        void failure_with_invalid_uuid() {
            assertThrows(InvalidUuidFormatException.class, () -> userService.delete(""));
            verify(usersRepository, never()).findByUuid(any());
        }

        @Test
        void failure_when_user_not_found() {
            when(usersRepository.findByUuid(validUuid)).thenReturn(Optional.empty());
            assertThrows(UserNotExistsException.class, () -> userService.delete(validUuid.toString()));
        }

        @Test
        void failure_when_user_not_enabled() {
            var deletedAt = LocalDateTime.now();
            var notEnabled = buildUserModel(UUID.randomUUID(), false, deletedAt);
            when(usersRepository.findByUuid(notEnabled.getUuid())).thenReturn(Optional.of(notEnabled));
            assertThrows(UserNotEnabledException.class, () -> userService.delete(notEnabled.getUuid().toString()));
            verify(usersRepository, never()).save(any());
        }
    }

    private User buildUserModel(UUID id, boolean verified, LocalDateTime deletedAt) {
        return User.builder()
                .uuid(id)
                .name("John Doe")
                .username("john.doe")
                .password(BCrypt.hashpw("plainPass123", BCrypt.gensalt()))
                .email("john.doe@mail.com")
                .isVerified(verified)
                .phone("(11) 99999-9999")
                .permissions(Set.of(Permission.builder().authority("ROLE_USER").build()))
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .deletedAt(deletedAt)
                .build();
    }
}
