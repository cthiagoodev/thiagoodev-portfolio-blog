package br.com.thiagoodev.blogapi.application.mappers;

import br.com.thiagoodev.blogapi.domain.entities.User;
import br.com.thiagoodev.blogapi.infrastructure.data.models.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(
    componentModel = "spring",
    uses = { UserPermissionMapper.class }
)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userToUserModel(UserModel model);
    UserModel userModelToUser(User user);
}
