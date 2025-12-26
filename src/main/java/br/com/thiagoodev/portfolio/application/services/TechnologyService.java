package br.com.thiagoodev.portfolio.application.services;

import br.com.thiagoodev.portfolio.domain.entities.Technology;
import br.com.thiagoodev.portfolio.infrastructure.persistence.repositories.TechnologiesRepository;
import br.com.thiagoodev.portfolio.infrastructure.services.storage.StorageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyService {
    private final TechnologiesRepository repository;
    private final StorageService storageService;

    public TechnologyService(
            TechnologiesRepository repository,
            StorageService storageService
    ) {
        this.repository = repository;
        this.storageService = storageService;
    }

    public List<Technology> getAll() {
        return this.repository.findAll()
                .stream()
                .map(tech -> tech.toBuilder()
                        .icon(storageService.storageUrlResolver(tech.getIcon()))
                        .build()
                )
                .toList();
    }
}
