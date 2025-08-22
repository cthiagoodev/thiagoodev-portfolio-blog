package br.com.thiagoodev.blogapi.application.mappers;

import br.com.thiagoodev.blogapi.domain.entities.User;
import br.com.thiagoodev.blogapi.domain.entities.UserPermission;
import br.com.thiagoodev.blogapi.infrastructure.data.models.PermissionModel;
import br.com.thiagoodev.blogapi.infrastructure.data.models.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.*;

@Mapper(componentModel = "spring", uses = {UserPermissionMapper.class})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserPermissionMapper permissionMapper = UserPermissionMapper.INSTANCE;

    default User userToUserModel(UserModel model) {
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

    default UserModel userModelToUser(User user) {
        UUID uuid = user.getUuid() != null
                ? UUID.fromString(user.getUuid())
                : null;

        Set<PermissionModel> permissions = new HashSet<>(permissionMapper
                .userPermissionsModelToUserPermissions(user.getPermissions()));

        return UserModel.builder()
                .uuid(uuid)
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .password(user.getPassword())
                .isVerified(user.getIsVerified())
                .permissions(permissions)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .deletedAt(user.getDeletedAt())
                .build();
    }
}
