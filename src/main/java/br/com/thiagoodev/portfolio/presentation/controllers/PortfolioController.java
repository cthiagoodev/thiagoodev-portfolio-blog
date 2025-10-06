package br.com.thiagoodev.portfolio.presentation.controllers;

import br.com.thiagoodev.portfolio.application.services.ProjectsService;
import br.com.thiagoodev.portfolio.application.services.TechnologyService;
import br.com.thiagoodev.portfolio.domain.entities.Project;
import br.com.thiagoodev.portfolio.domain.entities.Technology;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {
    private final ProjectsService projectsService;
    private final TechnologyService technologyService;

    public PortfolioController(
        ProjectsService projectsService,
        TechnologyService technologyService
    ) {
        this.projectsService = projectsService;
        this.technologyService = technologyService;
    }

    @GetMapping("/techs")
    public ResponseEntity<List<Technology>> techs() {
        List<Technology> techs = this.technologyService.getAll();
        return ResponseEntity.ok(techs);
    }

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> projects() {
        List<Project> projects = this.projectsService.getAll();
        return ResponseEntity.ok(projects);
    }
}
