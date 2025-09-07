package br.com.thiagoodev.blogapi.presentation.mappers;

import br.com.thiagoodev.blogapi.application.dto.UserResponseDto;
import br.com.thiagoodev.blogapi.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserToUserResponseDto {
    UserToUserResponseDto INSTANCE = Mappers.getMapper(UserToUserResponseDto.class);

    UserResponseDto map(User user);
}
