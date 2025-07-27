package br.com.thiagoodev.blogapi.domain.repositories;

import br.com.thiagoodev.blogapi.domain.entities.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUuid();
    Optional<User> findByEmail();
}
