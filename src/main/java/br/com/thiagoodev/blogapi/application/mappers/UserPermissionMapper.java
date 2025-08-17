package br.com.thiagoodev.blogapi.application.mappers;

import br.com.thiagoodev.blogapi.domain.entities.UserPermission;
import br.com.thiagoodev.blogapi.infrastructure.data.models.PermissionModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserPermissionMapper {
    UserPermissionMapper INSTANCE = Mappers.getMapper(UserPermissionMapper.class);

    UserPermission userPermissionToUserPermissionModel(PermissionModel permission);
    PermissionModel userPermissionModelToUserPermission(UserPermission permission);
}
