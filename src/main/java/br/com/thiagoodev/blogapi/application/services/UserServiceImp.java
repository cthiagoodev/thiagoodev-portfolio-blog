package br.com.thiagoodev.blogapi.application.services;

import br.com.thiagoodev.blogapi.domain.entities.User;
import br.com.thiagoodev.blogapi.domain.entities.UserPermission;
import br.com.thiagoodev.blogapi.domain.exceptions.*;
import br.com.thiagoodev.blogapi.domain.helpers.PhoneValidator;
import br.com.thiagoodev.blogapi.domain.services.UserService;
import br.com.thiagoodev.blogapi.infrastructure.data.models.PermissionModel;
import br.com.thiagoodev.blogapi.infrastructure.data.models.UserModel;
import br.com.thiagoodev.blogapi.infrastructure.data.repositories.UsersRepository;
import jakarta.transaction.Transactional;
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
        UUID id = this.validateAndParseUuidFormat(uuid);
        UserModel userModel = usersRepository.findByUuid(id)
                .orElseThrow(UserNotExistsException::new);

        return this.fromDataModel(userModel);
    }

    @Override
    @Transactional
    public User getByEmail(String email) {
        this.validateEmailFormat(email);

        UserModel userModel = usersRepository.findByEmail(email)
                .orElseThrow(UserNotExistsException::new);

        return this.fromDataModel(userModel);
    }

    @Override
    @Transactional
    public User getByPhone(String phone) {
        this.validatePhoneFormat(phone);

        UserModel userModel = usersRepository.findByPhone(phone)
                .orElseThrow(UserNotExistsException::new);

        return this.fromDataModel(userModel);
    }

    @Override
    @Transactional
    public User create(User newUser) {
        UUID uuid = validateAndParseUuidFormat(newUser.getUuid());
        this.checkUuidAvailability(uuid);

        this.validateUsernameFormat(newUser.getUsername());
        this.checkUsernameAvailability(newUser.getUsername());

        this.validateEmailFormat(newUser.getEmail());
        this.checkEmailAvailability(newUser.getEmail());

        this.validatePhoneFormat(newUser.getPhone());
        this.checkPhoneAvailability(newUser.getPhone());

        newUser.setPassword(this.encryptPassword(newUser.getPassword()));
        UserModel createdUser = usersRepository.save(this.toDataModel(newUser));
        return this.fromDataModel(createdUser);
    }

    @Override
    @Transactional
    public User update(User updatedUser) {
        UUID uuid = this.validateAndParseUuidFormat(updatedUser.getUuid());

        UserModel userModel = usersRepository.findByUuid(uuid)
                .orElseThrow(UserNotExistsException::new);

        if(!userModel.isEnabled()) {
            throw new UserNotEnabledException("User " + userModel.getName() + " not enabled");
        }

        if(!updatedUser.getUsername().equals(userModel.getUsername())) {
            this.validateUsernameFormat(updatedUser.getUsername());
            this.checkUsernameAvailability(updatedUser.getUsername());
        }

        if(!updatedUser.getEmail().equals(userModel.getEmail())) {
            this.validateEmailFormat(updatedUser.getEmail());
            this.checkEmailAvailability(updatedUser.getEmail());
        }

        if(!updatedUser.getPhone().equals(userModel.getPhone())) {
            this.validatePhoneFormat(updatedUser.getPhone());
            this.checkPhoneAvailability(updatedUser.getPhone());
        }

        userModel.setName(updatedUser.getName());
        userModel.setUsername(updatedUser.getUsername());
        userModel.setEmail(updatedUser.getEmail());
        userModel.setPhone(updatedUser.getPhone());
        userModel.setUpdatedAt(LocalDateTime.now());

        UserModel updatedUserModel = usersRepository.save(userModel);
        return this.fromDataModel(updatedUserModel);
    }

    @Override
    @Transactional
    public boolean delete(String uuid) {
        UUID id = this.validateAndParseUuidFormat(uuid);

        UserModel userModel = usersRepository.findByUuid(id)
                .orElseThrow(UserNotExistsException::new);

        if(!userModel.isEnabled())
            throw new UserNotEnabledException("User " + userModel.getName() + " not enabled");

        usersRepository.deleteById(id);

        return true;
    }

    private String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private UUID validateAndParseUuidFormat(String uuidString) {
        if (uuidString == null || uuidString.isEmpty()) {
            throw new InvalidUuidFormatException("UUID cannot be null or empty.");
        }
        try {
            return UUID.fromString(uuidString);
        } catch (IllegalArgumentException e) {
            throw new InvalidUuidFormatException(uuidString + " is not a valid UUID format.");
        }
    }

    private void checkUuidAvailability(UUID uuid) {
        if (usersRepository.findByUuid(uuid).isPresent()) {
            throw new UserAlreadyExistsException("User with UUID '" + uuid + "' already exists.");
        }
    }

    private void validateUsernameFormat(String username) {
        if (username == null || username.isEmpty()) {
            throw new InvalidUsernameFormatException("Username cannot be null or empty.");
        }
    }

    private void checkUsernameAvailability(String username) {
        Optional<UserModel> existingUser = usersRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("Username '" + username + "' is already in use.");
        }
    }

    private void validateEmailFormat(String email) {
        if(email == null || email.isEmpty()) throw new InvalidEmailFormatException("Email cannot be null or empty.");
        if(!email.contains("@")) throw new InvalidEmailFormatException("Email '" + email + "' is not a valid email.");
    }

    private void checkEmailAvailability(String email) {
        Optional<UserModel> existingUser = usersRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("Email '" + email + "' is already in use.");
        }
    }

    private void validatePhoneFormat(String phone) {
        if (PhoneValidator.isValidPhoneNumber(phone)) {
            throw new InvalidPhoneFormatException("Phone cannot be null or empty.");
        }
    }

    private void checkPhoneAvailability(String phone) {
        Optional<UserModel> existingUser = usersRepository.findByPhone(phone);
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("Phone '" + phone + "' is already in use.");
        }
    }

    private User fromDataModel(UserModel model) {
        return new User(
            model.getUuid().toString(),
            model.getName(),
            model.getUsername(),
            model.getPassword(),
            model.getEmail(),
            model.isVerified(),
            model.getPhone(),
            this.fromUserPermissions(model.getPermissions()),
            model.getCreatedAt(),
            model.getUpdatedAt(),
            model.getDeletedAt()
        );
    }

    private List<UserPermission> fromUserPermissions(Set<PermissionModel> model) {
        List<UserPermission> permissions = new ArrayList<>();

        for(PermissionModel permission : model) {
            String name = permission.getAuthority().replace("ROLE_", "");
            permissions.add(UserPermission.valueOf(name));
        }

        return permissions;
    }

    public UserModel toDataModel(User user) {
        UserModel userModel = new UserModel();

        userModel.setUuid(UUID.fromString(user.getUuid()));
        userModel.setName(user.getName());
        userModel.setUsername(user.getUsername());
        userModel.setPassword(user.getPassword());
        userModel.setEmail(user.getEmail());
        userModel.setVerified(user.getIsVerified());
        userModel.setPhone(user.getPhone());
        userModel.setCreatedAt(user.getCreatedAt());
        userModel.setUpdatedAt(user.getUpdatedAt());
        userModel.setDeletedAt(user.getDeletedAt());
        userModel.setPermissions(this.toPermissionModels(user.getPermissions()));

        return userModel;
    }

    private Set<PermissionModel> toPermissionModels(List<UserPermission> permissions) {
        Set<PermissionModel> permissionModels = new HashSet<>();

        if (permissions == null) {
            return permissionModels;
        }

        for (UserPermission permission : permissions) {
            PermissionModel model = new PermissionModel();
            model.setAuthority("ROLE_" + permission.name());
            permissionModels.add(model);
        }

        return permissionModels;
    }
}
