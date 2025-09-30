package br.com.thiagoodev.portfolio.application.services;

import br.com.thiagoodev.portfolio.infrastructure.persistence.repositories.ProjectsRepository;

public class ProjectsService {
    private final ProjectsRepository repository;

    public ProjectsService(ProjectsRepository repository) {
        this.repository = repository;
    }
}

