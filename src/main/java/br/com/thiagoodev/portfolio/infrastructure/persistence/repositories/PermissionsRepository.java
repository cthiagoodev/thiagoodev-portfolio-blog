package br.com.thiagoodev.portfolio.infrastructure.persistence.repositories;

import br.com.thiagoodev.portfolio.domain.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PermissionsRepository extends JpaRepository<Permission, UUID> {
    Optional<Permission> findByAuthority(String authority);
}
