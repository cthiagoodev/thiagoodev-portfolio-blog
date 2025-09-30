package br.com.thiagoodev.portfolio.infrastructure.persistence.repositories;

import br.com.thiagoodev.portfolio.domain.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectsRepository extends JpaRepository<Project, Long> {}
