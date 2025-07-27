package br.com.thiagoodev.blogapi.domain.services;

import br.com.thiagoodev.blogapi.domain.entities.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getByUuid(String uuid);
    Optional<User> getByEmail(String email);
    Optional<User> getByPhone(String email);
    User create(User newUser);
    User update(User newUser);
    boolean delete();
}
