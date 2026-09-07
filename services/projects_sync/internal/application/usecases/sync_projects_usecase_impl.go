package usecases

import (
	"context"

	"github.com/cthiagoodev/thiagoodev-portfolio/services/projects_sync/internal/domain/entities"
	"github.com/cthiagoodev/thiagoodev-portfolio/services/projects_sync/internal/domain/repositories"
	"github.com/cthiagoodev/thiagoodev-portfolio/services/projects_sync/internal/infrastructure/github"
)

type SyncProjectsUseCaseImpl struct {
	repository    repositories.ProjectsRepository
	githubService github.GithubService
	mapper        github.ProjectsMapperFunc
}

func NewSyncProjectsUseCaseImpl(
	repository repositories.ProjectsRepository,
	githubService github.GithubService,
	mapper github.ProjectsMapperFunc,
) *SyncProjectsUseCaseImpl {
	return &SyncProjectsUseCaseImpl{
		repository,
		githubService,
		mapper,
	}
}

func (s *SyncProjectsUseCaseImpl) Execute(ctx context.Context) ([]entities.Project, error) {
	repos, gErr := s.githubService.FetchRepositories(ctx)

	if gErr != nil {
		return nil, gErr
	}

	if len(repos) == 0 {
		return []entities.Project{}, nil
	}

	newProjects := s.mapper(repos)

	dErr := s.repository.DeleteAll(ctx)

	if dErr != nil {
		return nil, dErr
	}

	cErr := s.repository.CreateAll(ctx, newProjects)

	if cErr != nil {
		return nil, cErr
	}

	projects, pErr := s.repository.GetAll(ctx)

	if pErr != nil {
		return nil, pErr
	}

	return projects, nil
}
