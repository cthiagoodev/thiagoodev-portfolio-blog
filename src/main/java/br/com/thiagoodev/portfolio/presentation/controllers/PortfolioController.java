package br.com.thiagoodev.portfolio.presentation.controllers;

import br.com.thiagoodev.portfolio.application.services.ExperienceService;
import br.com.thiagoodev.portfolio.application.services.ProjectsService;
import br.com.thiagoodev.portfolio.application.services.TechnologyService;
import br.com.thiagoodev.portfolio.domain.entities.Experience;
import br.com.thiagoodev.portfolio.domain.entities.Project;
import br.com.thiagoodev.portfolio.domain.entities.Technology;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/portfolio")
public class PortfolioController {
    private final ProjectsService projectsService;
    private final TechnologyService technologyService;
    private final ExperienceService experienceService;

    public PortfolioController(
        ProjectsService projectsService,
        TechnologyService technologyService,
        ExperienceService experienceService
    ) {
        this.projectsService = projectsService;
        this.technologyService = technologyService;
        this.experienceService = experienceService;
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

    @GetMapping("/experiences")
    public ResponseEntity<List<Experience>> experiences() {
        List<Experience> experiences = experienceService.getAll();
        return ResponseEntity.ok(experiences);
    }
}
