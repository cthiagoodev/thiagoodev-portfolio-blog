package br.com.thiagoodev.portfolio.application.services;

import br.com.thiagoodev.portfolio.domain.entities.Technology;
import br.com.thiagoodev.portfolio.infrastructure.persistence.repositories.TechnologiesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyService {
    private final TechnologiesRepository repository;

    public TechnologyService(TechnologiesRepository repository) {
        this.repository = repository;
    }

    public List<Technology> getAll() {
        return this.repository.findAll();
    }
}
