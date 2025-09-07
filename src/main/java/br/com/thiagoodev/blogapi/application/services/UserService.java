package br.com.thiagoodev.blogapi.application.services;

import br.com.thiagoodev.blogapi.application.dto.CreateUserDto;
import br.com.thiagoodev.blogapi.application.dto.UpdateUserDto;
import br.com.thiagoodev.blogapi.domain.entities.Permission;
import br.com.thiagoodev.blogapi.domain.entities.User;
import br.com.thiagoodev.blogapi.domain.exceptions.InvalidUuidFormatException;
import br.com.thiagoodev.blogapi.domain.exceptions.UserNotExistsException;
import br.com.thiagoodev.blogapi.domain.helpers.EmailValidator;
import br.com.thiagoodev.blogapi.domain.helpers.PhoneValidator;
import br.com.thiagoodev.blogapi.domain.helpers.UUIDValidator;
import br.com.thiagoodev.blogapi.infrastructure.persistence.repositories.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    UserService(
        UsersRepository usersRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getByUuid(String uuid) {
        return this.usersRepository
            .findByUuid(this.parseAndValidateUuid(uuid))
            .filter(User::isEnabled)
            .orElseThrow(UserNotExistsException::new);
    }

    public User getByEmail(String email) {
        return this.usersRepository
            .findByEmail(this.validateEmail(email))
            .filter(User::isEnabled)
            .orElseThrow(UserNotExistsException::new);
    }

    public User getByPhone(String phone) {
        return this.usersRepository
            .findByPhone(this.validatePhone(phone))
            .filter(User::isEnabled)
            .orElseThrow(UserNotExistsException::new);
    }

    @Transactional
    public User create(CreateUserDto dto) {
        User user = User.builder()
            .name(dto.name())
            .username(dto.username())
            .permissions(new HashSet<>(Set.of(Permission.userRole())))
            .email(this.validateEmail(dto.email()))
            .password(this.passwordEncoder.encode(dto.password()))
            .phone(this.validatePhone(dto.phone()))
            .isVerified(false)
            .build();

        return this.usersRepository.saveAndFlush(user);
    }

    @Transactional
    public User update(String uuid, UpdateUserDto dto) {
        User user = this.usersRepository.findByUuid(this.parseAndValidateUuid(uuid))
                .filter(User::isEnabled)
                .orElseThrow(UserNotExistsException::new);

        user.setName(dto.name());
        user.setUsername(dto.username());
        user.setEmail(this.validateEmail(dto.email()));
        user.setPhone(this.validatePhone(dto.phone()));

        return this.usersRepository.saveAndFlush(user);
    }

    @Transactional
    public User delete(String uuid) {
        User user = this.usersRepository.findByUuid(this.parseAndValidateUuid(uuid))
                .filter(User::isEnabled)
                .orElseThrow(UserNotExistsException::new);

        user.delete();

        return this.usersRepository.saveAndFlush(user);
    }

    @Transactional
    public User verify(String uuid) {
        User user = this.usersRepository.findByUuid(this.parseAndValidateUuid(uuid))
                .orElseThrow(UserNotExistsException::new);

        user.verify();

        return this.usersRepository.saveAndFlush(user);
    }

    private UUID parseAndValidateUuid(String uuid) {
        if(!UUIDValidator.isValidUUID(uuid)) {
            throw new InvalidUuidFormatException("Invalid UUID format");
        }

        return UUID.fromString(uuid);
    }

    private String validateEmail(String email) {
        if(!EmailValidator.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        return email;
    }

    private String validatePhone(String phone) {
        if(!PhoneValidator.isValidPhoneNumber(phone)) {
            throw new IllegalArgumentException("Invalid phone format");
        }

        return phone;
    }
}
