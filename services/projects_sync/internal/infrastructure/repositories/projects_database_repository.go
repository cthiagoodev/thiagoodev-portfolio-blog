package repositories

import (
	"github.com/cthiagoodev/thiagoodev-portfolio/services/projects_sync/internal/domain/entities"
	"github.com/jackc/pgx/v5/pgxpool"
)

type ProjectsDatabaseRepository struct {
	pool *pgxpool.Pool
}

func (p ProjectsDatabaseRepository) GetAll() ([]entities.Project, error) {
	//TODO implement me
	panic("implement me")
}

func (p ProjectsDatabaseRepository) CreateAll(projects []entities.Project) error {
	//TODO implement me
	panic("implement me")
}

func (p ProjectsDatabaseRepository) DeleteAll() error {
	//TODO implement me
	panic("implement me")
}
