package br.com.thiagoodev.blogapi.infrastructure.data.repositories;

import br.com.thiagoodev.blogapi.infrastructure.data.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsersJpaRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByUuid(UUID uuid);
    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findByUsername(String username);
    Optional<UserModel> findByPhone(String phone);
}
