package br.com.thiagoodev.blogapi.infrastructure.persistence.repositories;

import br.com.thiagoodev.blogapi.domain.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PermissionsRepository extends JpaRepository<Permission, UUID> {
    Optional<Permission> findByAuthority(String authority);
}
