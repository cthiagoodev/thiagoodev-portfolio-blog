package br.com.thiagoodev.blogapi.application.services;

import br.com.thiagoodev.blogapi.domain.entities.User;
import br.com.thiagoodev.blogapi.domain.entities.UserPermission;
import br.com.thiagoodev.blogapi.domain.services.UserService;
import br.com.thiagoodev.blogapi.infrastructure.data.models.PermissionModel;
import br.com.thiagoodev.blogapi.infrastructure.data.models.UserModel;
import br.com.thiagoodev.blogapi.infrastructure.data.repositories.UsersJpaRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImp implements UserService {
    private final UsersJpaRepository usersJpaRepository;

    public UserServiceImp(UsersJpaRepository usersJpaRepository) {
        this.usersJpaRepository = usersJpaRepository;
    }

    @Override
    public Optional<User> getByUuid(String uuid) {
        try {
            UUID id = UUID.fromString(uuid);
            Optional<UserModel> userModel = usersJpaRepository.findByUuid(id);

            if(userModel.isEmpty()) {
                return Optional.empty();
            }


        } catch (Exception error) {

        }

        return Optional.empty();
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getByPhone(String email) {
        return Optional.empty();
    }

    @Override
    public User create(User newUser) {
        return null;
    }

    @Override
    public User update(User newUser) {
        return null;
    }

    @Override
    public boolean delete() {
        return false;
    }

    private User toDataModel(UserModel model) {
        return new User(
            model.getUuid().toString(),
            model.getName(),
            model.getUsername(),
            model.getPassword(),
            model.getEmail(),
            model.isVerified(),
            model.getPhone(),
            this.toUserPermissions(model.getPermissions()),
            model.getCreatedAt(),
            model.getUpdatedAt(),
            model.getDeletedAt()
        );
    }

    private List<UserPermission> toUserPermissions(Set<PermissionModel> model) {
        List<UserPermission> permissions = new ArrayList<>();



        return permissions;
    }
}
