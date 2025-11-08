package br.com.thiagoodev.portfolio.presentation.controllers;

import br.com.thiagoodev.portfolio.application.services.ExperienceService;
import br.com.thiagoodev.portfolio.application.services.ProjectsService;
import br.com.thiagoodev.portfolio.application.services.TechnologyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
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

    @GetMapping("/")
    public String index() {
        return "portfolio/index";
    }
}
