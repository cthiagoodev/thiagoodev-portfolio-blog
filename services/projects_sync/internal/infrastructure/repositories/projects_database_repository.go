package repositories

import (
	"context"

	"github.com/cthiagoodev/thiagoodev-portfolio/services/projects_sync/internal/domain/entities"
	"github.com/jackc/pgx/v5/pgxpool"
)

type ProjectsDatabaseRepository struct {
	pool *pgxpool.Pool
}

func (p *ProjectsDatabaseRepository) GetAll(ctx context.Context) ([]entities.Project, error) {
	//TODO implement me
	panic("implement me")
}

func (p *ProjectsDatabaseRepository) CreateAll(ctx context.Context, projects []entities.Project) error {
	//TODO implement me
	panic("implement me")
}

func (p *ProjectsDatabaseRepository) DeleteAll(ctx context.Context) error {
	//TODO implement me
	panic("implement me")
}
