package br.com.thiagoodev.portfolio.application.schedules;

import br.com.thiagoodev.portfolio.application.services.ProjectsService;
import br.com.thiagoodev.portfolio.domain.entities.Project;
import br.com.thiagoodev.portfolio.infrastructure.services.github.GithubProject;
import br.com.thiagoodev.portfolio.infrastructure.services.github.GithubService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
public class ProjectsSchedule {
    private final GithubService githubService;
    private final ProjectsService projectsService;

    public ProjectsSchedule(
        GithubService githubService,
        ProjectsService projectsService
    ) {
        this.githubService = githubService;
        this.projectsService = projectsService;
    }

    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void findAndSaveProjects() {
        try {
            List<GithubProject> projects = githubService
                    .getProjects()
                    .timeout(Duration.ofSeconds(10))
                    .onErrorReturn(List.of())
                    .block();

            if(projects == null || projects.isEmpty()) {
                System.err.println("No projects found.");
                return;
            }

            List<Project> entities = projects.stream()
                .map(this::mapToEntity)
                .toList();

            projectsService.saveAll(entities);
        } catch (Exception error) {

        }
    }

    private Project mapToEntity(GithubProject githubProject) {
        return Project.builder()
            .id(githubProject.id())
            .name(githubProject.name())
            .fullName(githubProject.fullName())
            .htmlUrl(githubProject.htmlUrl())
            .description(githubProject.description())
            .language(githubProject.language())
            .topics(githubProject.topics())
            .homepage(githubProject.homepage())
            .createdAt(this.parseDate(githubProject.createdAt()))
            .updatedAt(this.parseDate(githubProject.updatedAt()))
            .pushedAt(this.parseDate(githubProject.pushedAt()))
            .build();
    }

    private LocalDateTime parseDate(String date) {
        return LocalDateTime.ofInstant(Instant.parse(date), ZoneId.systemDefault());
    }
}
