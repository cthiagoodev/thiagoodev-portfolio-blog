package br.com.thiagoodev.blogapi.application.mappers;

import br.com.thiagoodev.blogapi.domain.entities.UserPermission;
import br.com.thiagoodev.blogapi.infrastructure.data.models.PermissionModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserPermissionMapper {
    UserPermissionMapper INSTANCE = Mappers.getMapper(UserPermissionMapper.class);

    default UserPermission userPermissionToUserPermissionModel(PermissionModel permission) {
        try {
            String authority = permission.getAuthority().replace("ROLE_", "");
            return UserPermission.valueOf(authority);
        } catch (IllegalArgumentException e) {
            return UserPermission.USER;
        }
    }

    default PermissionModel userPermissionModelToUserPermission(UserPermission permission) {
        return PermissionModel.builder()
                .authority("ROLE_" + permission.name())
                .build();
    }

    default List<UserPermission> userPermissionsToUserPermissionsModel(List<PermissionModel> permissions) {
        return permissions.stream()
                .map(this::userPermissionToUserPermissionModel)
                .toList();
    }

    default List<PermissionModel> userPermissionsModelToUserPermissions(List<UserPermission> permissions) {
        return permissions.stream()
                .map(this::userPermissionModelToUserPermission)
                .toList();
    }
}
