package br.com.thiagoodev.portfolio.presentation.controllers;

import br.com.thiagoodev.portfolio.application.services.ProjectsService;
import br.com.thiagoodev.portfolio.application.services.TechnologyService;
import br.com.thiagoodev.portfolio.domain.entities.Project;
import br.com.thiagoodev.portfolio.domain.entities.Technology;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
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
    public String techs(Model model) {
        List<Technology> techs = this.technologyService.getAll();
        model.addAttribute("techs", techs);

        return "fragments/tech-carousel :: tech-carousel";
    }

    @GetMapping("/projects")
    public String projects(Model model) {
        List<Project> projects = this.projectsService.getAll();
        model.addAttribute("projects", projects);

        return "fragments/projects :: projects";
    }
}
