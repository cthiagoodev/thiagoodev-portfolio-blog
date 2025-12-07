package br.com.thiagoodev.portfolio.presentation.controllers;

import br.com.thiagoodev.portfolio.application.services.ExperienceService;
import br.com.thiagoodev.portfolio.application.services.ProjectsService;
import br.com.thiagoodev.portfolio.application.services.TechnologyService;
import br.com.thiagoodev.portfolio.domain.entities.Experience;
import br.com.thiagoodev.portfolio.domain.entities.Project;
import br.com.thiagoodev.portfolio.domain.entities.Technology;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String index(Model model) {
        List<Technology> techs = technologyService.getAll();
        List<Experience> experiences = experienceService.getAll();
        List<Project> projects = projectsService.getAll();


        Map<String, Object> attributes = new HashMap<>();
        attributes.put("techs", techs);
        attributes.put("experiences", experiences);
        attributes.put("projects", projects);

        model.addAllAttributes(attributes);

        return "portfolio/index";
    }
}
