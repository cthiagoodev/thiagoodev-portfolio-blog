package br.com.thiagoodev.blogapi.presentation.mappers;

import br.com.thiagoodev.blogapi.domain.entities.User;
import br.com.thiagoodev.blogapi.presentation.dtos.CreateUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    UserDtoMapper INSTANCE = Mappers.getMapper(UserDtoMapper.class);

    default User userToCreateUserDto(CreateUserDto dto) {
        return new User(
            dto.getName(),
            dto.getUsername(),
            dto.getPassword(),
            dto.getEmail(),
            dto.getPhone()
        );
    }
}
