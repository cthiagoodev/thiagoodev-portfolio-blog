package br.com.thiagoodev.blogapi.application.services;

import br.com.thiagoodev.blogapi.application.mappers.UserMapper;
import br.com.thiagoodev.blogapi.domain.entities.User;
import br.com.thiagoodev.blogapi.domain.exceptions.*;
import br.com.thiagoodev.blogapi.domain.helpers.EmailValidator;
import br.com.thiagoodev.blogapi.domain.helpers.PhoneValidator;
import br.com.thiagoodev.blogapi.domain.helpers.UUIDValidator;
import br.com.thiagoodev.blogapi.domain.services.UserService;
import br.com.thiagoodev.blogapi.infrastructure.data.helpers.PSQLExceptionConstraintDecode;
import br.com.thiagoodev.blogapi.infrastructure.data.models.UserModel;
import br.com.thiagoodev.blogapi.infrastructure.data.repositories.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImp implements UserService {
    private final UsersRepository usersRepository;

    public UserServiceImp(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    @Transactional
    public User getByUuid(String uuid) {
        UUID id;

        if(!UUIDValidator.isValidUUID(uuid)) {
            throw new InvalidUuidFormatException("UUID cannot be null or empty.");
        }

        try {
            id = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new InvalidUuidFormatException(uuid + " is not a valid UUID format.");
        }

        UserModel userModel = usersRepository.findByUuid(id)
                .orElseThrow(UserNotExistsException::new);

        return UserMapper.INSTANCE.userToUserModel(userModel);
    }

    @Override
    @Transactional
    public User getByEmail(String email) {
        if(!EmailValidator.isValidEmail(email)) {
            throw new IllegalArgumentException("Email '" + email + "' is not a valid email.");
        }

        UserModel userModel = usersRepository.findByEmail(email)
                .orElseThrow(UserNotExistsException::new);

        return UserMapper.INSTANCE.userToUserModel(userModel);
    }

    @Override
    @Transactional
    public User getByPhone(String phone) {
        if (!PhoneValidator.isValidPhoneNumber(phone)) {
            throw new IllegalArgumentException("Phone cannot be null or empty.");
        }

        UserModel userModel = usersRepository.findByPhone(phone)
                .orElseThrow(UserNotExistsException::new);

        return UserMapper.INSTANCE.userToUserModel(userModel);
    }

    @Override
    @Transactional
    public User create(User newUser) {
        try {
            if(!UUIDValidator.isValidUUID(newUser.getUuid())) {
                throw new InvalidUuidFormatException("UUID cannot be null or empty.");
            }

            newUser.setPassword(this.encryptPassword(newUser.getPassword()));
            UserMapper mapper = UserMapper.INSTANCE;

            UserModel createdUser = usersRepository.save(mapper.userModelToUser(newUser));
            return mapper.userToUserModel(createdUser);
        } catch(DataIntegrityViolationException error) {
            throw this.handleDataIntegrityViolationException(error, newUser);
        }
    }

    @Override
    @Transactional
    public User update(User updatedUser) {
        try {
            if(!UUIDValidator.isValidUUID(updatedUser.getUuid())) {
                throw new InvalidUuidFormatException("UUID cannot be null or empty.");
            }

            UUID uuid;

            try {
                uuid = UUID.fromString(updatedUser.getUuid());
            } catch (IllegalArgumentException e) {
                throw new InvalidUuidFormatException(updatedUser.getUuid() + " is not a valid UUID format.");
            }

            UserModel userModel = usersRepository.findByUuid(uuid)
                    .orElseThrow(UserNotExistsException::new);

            if(!userModel.isEnabled()) {
                throw new UserNotEnabledException("User " + userModel.getName() + " not enabled");
            }

            userModel.setName(updatedUser.getName());
            userModel.setUsername(updatedUser.getUsername());
            userModel.setEmail(updatedUser.getEmail());
            userModel.setPhone(updatedUser.getPhone());
            userModel.setUpdatedAt(LocalDateTime.now());

            userModel = usersRepository.save(userModel);
            UserMapper mapper = UserMapper.INSTANCE;
            return mapper.userToUserModel(userModel);
        } catch (DataIntegrityViolationException error) {
            throw this.handleDataIntegrityViolationException(error, updatedUser);
        }
    }

    @Override
    @Transactional
    public boolean delete(String uuid) {
        if(!UUIDValidator.isValidUUID(uuid)) {
            throw new InvalidUuidFormatException("UUID cannot be null or empty.");
        }

        UUID id;

        try {
            id = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new InvalidUuidFormatException(uuid + " is not a valid UUID format.");
        }

        UserModel userModel = usersRepository.findByUuid(id)
                .orElseThrow(UserNotExistsException::new);

        if(!userModel.isEnabled()) {
            throw new UserNotEnabledException("User " + userModel.getName() + " not enabled");
        }

        userModel.setDeletedAt(LocalDateTime.now());
        usersRepository.save(userModel);

        return true;
    }

    private String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private RuntimeException handleDataIntegrityViolationException(DataIntegrityViolationException error, User user) {
        String constraint = PSQLExceptionConstraintDecode.decode(error);
        if(constraint == null) return new RuntimeException("Error on create user");

        return switch (constraint) {
            case "uc_users_email" -> new UserAlreadyExistsException("User with email '" + user.getEmail() + "' already exists.");
            case "uc_users_phone" -> new UserAlreadyExistsException("User with phone '" + user.getPhone() + "' already exists.");
            case "uc_users_username" -> new UserAlreadyExistsException("User with username '" + user.getUsername() + "' already exists.");
            case "chk_users_name_not_empty" -> new IllegalArgumentException("Name cannot be empty.");
            case "chk_users_username_not_empty" -> new IllegalArgumentException("Username cannot be empty.");
            case "chk_users_password_not_empty" -> new IllegalArgumentException("Password cannot be empty.");
            case "chk_users_email_not_empty" -> new IllegalArgumentException("Email cannot be empty.");
            case "chk_users_phone_not_empty" -> new IllegalArgumentException("Phone cannot be empty.");
            default -> new RuntimeException(error.getMessage());
        };
    }
}
