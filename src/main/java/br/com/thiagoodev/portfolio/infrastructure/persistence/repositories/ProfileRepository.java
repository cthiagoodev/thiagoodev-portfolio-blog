package br.com.thiagoodev.portfolio.infrastructure.persistence.repositories;

import br.com.thiagoodev.portfolio.domain.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    Optional<Profile> findFirstByOrderByCreatedAtDesc();
}
