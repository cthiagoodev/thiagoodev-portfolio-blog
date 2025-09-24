package br.com.thiagoodev.blogapi.application.schedules;

import br.com.thiagoodev.blogapi.domain.entities.Project;
import br.com.thiagoodev.blogapi.infrastructure.persistence.repositories.ProjectsRepository;
import br.com.thiagoodev.blogapi.infrastructure.services.github.GithubProject;
import br.com.thiagoodev.blogapi.infrastructure.services.github.GithubService;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
public class ProjectsSchedule {
    private final GithubService githubService;
    private final ProjectsRepository repository;

    public ProjectsSchedule(
        GithubService githubService,
        ProjectsRepository repository
    ) {
        this.githubService = githubService;
        this.repository = repository;
    }

//    @Scheduled(fixedRate = 5 * 60 * 1000)
    @Transactional
    public void findAndSaveProjects() {
        List<GithubProject> projects = githubService.getProjects().block();

        if(projects == null || projects.isEmpty()) return;

        List<Project> entities = projects.stream()
            .map(this::mapToEntity)
            .toList();

        repository.saveAllAndFlush(entities);
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
