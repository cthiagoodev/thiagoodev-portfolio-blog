package br.com.thiagoodev.portfolio.application.services;

import br.com.thiagoodev.portfolio.domain.entities.Experience;
import br.com.thiagoodev.portfolio.infrastructure.persistence.repositories.ExperiencesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceService {
    private final ExperiencesRepository repository;

    public ExperienceService(ExperiencesRepository repository) {
        this.repository = repository;
    }

    public List<Experience> getAll() {
        return repository.findByOrderByStartDateDesc();
    }
}
