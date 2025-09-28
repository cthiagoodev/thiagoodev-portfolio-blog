package br.com.thiagoodev.blogapi.application.services;

import br.com.thiagoodev.blogapi.infrastructure.persistence.repositories.ProjectsRepository;

public class ProjectsService {
    private final ProjectsRepository repository;

    public ProjectsService(ProjectsRepository repository) {
        this.repository = repository;
    }
}

