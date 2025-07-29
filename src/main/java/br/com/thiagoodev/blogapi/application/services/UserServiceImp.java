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
import org.springframework.stereotype.Service;

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

        try {
            id = UUID.fromString(uuid);
        } catch (IllegalArgumentException error) {
            throw new InvalidUuidFormatException(uuid + " is not a valid UUID");
        }

        UserModel userModel = usersRepository.findByUuid(id)
                .orElseThrow(UserNotExistsException::new);

        return this.fromDataModel(userModel);
    }

    @Override
    @Transactional
    public User getByEmail(String email) {
        if(email == null ||
            !email.contains("@")) {
            throw new InvalidEmailFormatException(email + " is not a valid E-mail");
        }

        UserModel userModel = usersRepository.findByEmail(email)
                .orElseThrow(UserNotExistsException::new);

        return this.fromDataModel(userModel);
    }

    @Override
    @Transactional
    public User getByPhone(String phone) {
        if(!PhoneValidator.isValidPhoneNumber(phone)) {
            throw new InvalidEmailFormatException(phone + " is not a valid phone");
        }

        UserModel userModel = usersRepository.findByPhone(phone)
                .orElseThrow(UserNotExistsException::new);

        return this.fromDataModel(userModel);
    }

    @Override
    @Transactional
    public User create(User newUser) {
        if(usersRepository.findByUuid(UUID.fromString(newUser.getUuid())).isPresent()) {
            throw new UserAlreadyExistsException(newUser.getUuid() + " already exists");
        }

        String username = newUser.getUsername();
        if(username == null || username.isEmpty()) {
            throw new InvalidUsernameFormatException(username + " is not a valid username");
        }

        if(usersRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException(username + " already exists");
        }

        String email = newUser.getEmail();
        if(email == null || email.isEmpty()) {
            throw new InvalidEmailFormatException(email + " is not a valid email");
        }

        if(usersRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException(email + " already exists");
        }

        String phone = newUser.getPhone();
        if(phone == null || phone.isEmpty()) {
            throw new InvalidPhoneFormatException(email + " is not a valid phone");
        }

        if(usersRepository.findByPhone(phone).isPresent()) {
            throw new UserAlreadyExistsException(phone + " already exists");
        }



        return null;
    }

    @Override
    @Transactional
    public User update(User user) {
        return null;
    }

    @Override
    @Transactional
    public boolean delete() {
        return false;
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
