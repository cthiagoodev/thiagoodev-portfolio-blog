package br.com.thiagoodev.portfolio.application.services;

import br.com.thiagoodev.portfolio.domain.entities.Project;
import br.com.thiagoodev.portfolio.infrastructure.persistence.repositories.ProjectsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectsService {
    private final ProjectsRepository repository;

    public ProjectsService(ProjectsRepository repository) {
        this.repository = repository;
    }

    public List<Project> getAll() {
        return this.repository.findAll();
    }

    @Transactional
    public void saveAll(List<Project> projects) {
        this.repository.saveAllAndFlush(projects);
    }
}

