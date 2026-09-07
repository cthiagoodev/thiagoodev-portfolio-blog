package usecases

import (
	"context"
	"errors"
	"testing"
	"time"

	"github.com/cthiagoodev/thiagoodev-portfolio/services/projects_sync/internal/domain/entities"
	"github.com/cthiagoodev/thiagoodev-portfolio/services/projects_sync/internal/infrastructure/github"
	githubmocks "github.com/cthiagoodev/thiagoodev-portfolio/services/projects_sync/internal/test/mocks/github"
	repositoriesmocks "github.com/cthiagoodev/thiagoodev-portfolio/services/projects_sync/internal/test/mocks/repositories"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
)

func TestSyncProjectsUseCaseImpl_Execute(t *testing.T) {
	t.Run("should fetch, map, save and return projects", func(t *testing.T) {
		ctx := context.TODO()
		repository := repositoriesmocks.NewMockProjectsRepository(t)
		service := githubmocks.NewMockGithubService(t)

		now := time.Now()

		githubProjects := []github.Project{
			{
				Id:        123,
				Name:      "portfolio",
				HtmlUrl:   "https://github.com/cthiagoodev/portfolio",
				CreatedAt: now,
				UpdatedAt: now,
			},
		}

		expectedProjects := []entities.Project{
			{
				ExternalId: "123",
				Name:       "portfolio",
				Url:        "https://github.com/cthiagoodev/portfolio",
				CreatedAt:  now,
				UpdatedAt:  now,
			},
		}

		mapper := func(repos []github.Project) []entities.Project {
			assert.Equal(t, githubProjects, repos)
			return expectedProjects
		}

		service.
			EXPECT().
			FetchRepositories(ctx).
			Return(githubProjects, nil)

		repository.
			EXPECT().
			DeleteAll(ctx).
			Return(nil)

		repository.
			EXPECT().
			CreateAll(ctx, expectedProjects).
			Return(nil)

		repository.
			EXPECT().
			GetAll(ctx).
			Return(expectedProjects, nil)

		useCase := NewSyncProjectsUseCaseImpl(
			repository,
			service,
			mapper,
		)

		result, err := useCase.Execute(ctx)

		require.NoError(t, err)
		assert.Equal(t, expectedProjects, result)
	})

	t.Run("should return empty list when github returns no repositories", func(t *testing.T) {
		ctx := context.TODO()
		repository := repositoriesmocks.NewMockProjectsRepository(t)
		service := githubmocks.NewMockGithubService(t)

		mapper := func(repos []github.Project) []entities.Project {
			t.Fatal("mapper should not be called")
			return nil
		}

		service.
			EXPECT().
			FetchRepositories(ctx).
			Return([]github.Project{}, nil)

		useCase := NewSyncProjectsUseCaseImpl(
			repository,
			service,
			mapper,
		)

		result, err := useCase.Execute(ctx)

		require.NoError(t, err)
		assert.Empty(t, result)
	})

	t.Run("should return error when github fetch fails", func(t *testing.T) {
		ctx := context.TODO()
		repository := repositoriesmocks.NewMockProjectsRepository(t)
		service := githubmocks.NewMockGithubService(t)

		expectedErr := errors.New("github error")

		mapper := func(repos []github.Project) []entities.Project {
			t.Fatal("mapper should not be called")
			return nil
		}

		service.
			EXPECT().
			FetchRepositories(ctx).
			Return(nil, expectedErr)

		useCase := NewSyncProjectsUseCaseImpl(
			repository,
			service,
			mapper,
		)

		result, err := useCase.Execute(ctx)

		require.Error(t, err)
		assert.ErrorIs(t, err, expectedErr)
		assert.Nil(t, result)
	})

	t.Run("should return error when delete all fails", func(t *testing.T) {
		ctx := context.TODO()
		repository := repositoriesmocks.NewMockProjectsRepository(t)
		service := githubmocks.NewMockGithubService(t)

		expectedErr := errors.New("delete error")

		githubProjects := []github.Project{
			{
				Id:      123,
				Name:    "portfolio",
				HtmlUrl: "https://github.com/cthiagoodev/portfolio",
			},
		}

		expectedProjects := []entities.Project{
			{
				ExternalId: "123",
				Name:       "portfolio",
				Url:        "https://github.com/cthiagoodev/portfolio",
			},
		}

		mapper := func(repos []github.Project) []entities.Project {
			return expectedProjects
		}

		service.
			EXPECT().
			FetchRepositories(ctx).
			Return(githubProjects, nil)

		repository.
			EXPECT().
			DeleteAll(ctx).
			Return(expectedErr)

		useCase := NewSyncProjectsUseCaseImpl(
			repository,
			service,
			mapper,
		)

		result, err := useCase.Execute(ctx)

		require.Error(t, err)
		assert.ErrorIs(t, err, expectedErr)
		assert.Nil(t, result)
	})

	t.Run("should return error when create all fails", func(t *testing.T) {
		ctx := context.TODO()
		repository := repositoriesmocks.NewMockProjectsRepository(t)
		service := githubmocks.NewMockGithubService(t)

		expectedErr := errors.New("create error")

		githubProjects := []github.Project{
			{
				Id:      123,
				Name:    "portfolio",
				HtmlUrl: "https://github.com/cthiagoodev/portfolio",
			},
		}

		expectedProjects := []entities.Project{
			{
				ExternalId: "123",
				Name:       "portfolio",
				Url:        "https://github.com/cthiagoodev/portfolio",
			},
		}

		mapper := func(repos []github.Project) []entities.Project {
			return expectedProjects
		}

		service.
			EXPECT().
			FetchRepositories(ctx).
			Return(githubProjects, nil)

		repository.
			EXPECT().
			DeleteAll(ctx).
			Return(nil)

		repository.
			EXPECT().
			CreateAll(ctx, expectedProjects).
			Return(expectedErr)

		useCase := NewSyncProjectsUseCaseImpl(
			repository,
			service,
			mapper,
		)

		result, err := useCase.Execute(ctx)

		require.Error(t, err)
		assert.ErrorIs(t, err, expectedErr)
		assert.Nil(t, result)
	})

	t.Run("should return error when get all fails", func(t *testing.T) {
		ctx := context.TODO()
		repository := repositoriesmocks.NewMockProjectsRepository(t)
		service := githubmocks.NewMockGithubService(t)

		expectedErr := errors.New("get all error")

		githubProjects := []github.Project{
			{
				Id:      123,
				Name:    "portfolio",
				HtmlUrl: "https://github.com/cthiagoodev/portfolio",
			},
		}

		expectedProjects := []entities.Project{
			{
				ExternalId: "123",
				Name:       "portfolio",
				Url:        "https://github.com/cthiagoodev/portfolio",
			},
		}

		mapper := func(repos []github.Project) []entities.Project {
			return expectedProjects
		}

		service.
			EXPECT().
			FetchRepositories(ctx).
			Return(githubProjects, nil)

		repository.
			EXPECT().
			DeleteAll(ctx).
			Return(nil)

		repository.
			EXPECT().
			CreateAll(ctx, expectedProjects).
			Return(nil)

		repository.
			EXPECT().
			GetAll(ctx).
			Return(nil, expectedErr)

		useCase := NewSyncProjectsUseCaseImpl(
			repository,
			service,
			mapper,
		)

		result, err := useCase.Execute(ctx)

		require.Error(t, err)
		assert.ErrorIs(t, err, expectedErr)
		assert.Nil(t, result)
	})
}
