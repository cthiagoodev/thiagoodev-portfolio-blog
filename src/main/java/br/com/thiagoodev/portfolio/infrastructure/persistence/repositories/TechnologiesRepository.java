package br.com.thiagoodev.portfolio.infrastructure.persistence.repositories;

import br.com.thiagoodev.portfolio.domain.entities.Technology;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TechnologiesRepository extends JpaRepository<Technology, UUID> { }
