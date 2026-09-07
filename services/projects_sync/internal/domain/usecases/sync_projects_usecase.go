package usecases

import (
	"context"

	"github.com/cthiagoodev/thiagoodev-portfolio/services/projects_sync/internal/domain/entities"
)

type SyncProjectsUseCase interface {
	Execute(ctx context.Context) ([]entities.Project, error)
}
