package br.com.thiagoodev.blogapi.infrastructure.persistence.repositories;

import br.com.thiagoodev.blogapi.domain.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectsRepository extends JpaRepository<Project, Long> {}
