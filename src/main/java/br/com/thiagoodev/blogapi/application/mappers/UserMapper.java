package br.com.thiagoodev.blogapi.application.mappers;

import br.com.thiagoodev.blogapi.domain.entities.User;
import br.com.thiagoodev.blogapi.domain.entities.UserPermission;
import br.com.thiagoodev.blogapi.infrastructure.data.models.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {UserPermissionMapper.class})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    default User userToUserModel(UserModel model, UserPermissionMapper permissionMapper) {
        List<UserPermission> permissions = permissionMapper
                .userPermissionsToUserPermissionsModel(new ArrayList<>(model.getPermissions()));

        return new User(
            model.getUuid().toString(),
            model.getName(),
            model.getUsername(),
            model.getPassword(),
            model.getEmail(),
            model.isVerified(),
            model.getPhone(),
            permissions,
            model.getCreatedAt(),
            model.getUpdatedAt(),
            model.getDeletedAt()
        );
    }

    UserModel userModelToUser(User user);
}
