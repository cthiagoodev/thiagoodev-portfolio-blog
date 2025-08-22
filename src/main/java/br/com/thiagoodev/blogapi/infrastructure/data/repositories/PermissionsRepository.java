package br.com.thiagoodev.blogapi.infrastructure.data.repositories;

import br.com.thiagoodev.blogapi.infrastructure.data.models.PermissionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PermissionsRepository extends JpaRepository<PermissionModel, UUID> {
    Optional<PermissionModel> findByAuthority(String authority);
}
