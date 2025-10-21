package br.com.thiagoodev.portfolio.infrastructure.persistence.repositories;

import br.com.thiagoodev.portfolio.domain.entities.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExperiencesRepository extends JpaRepository<Experience, UUID> {
    List<Experience> findByOrderByStartDateDesc();
}
