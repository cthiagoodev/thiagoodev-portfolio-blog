package repositories

import (
	"context"

	"github.com/cthiagoodev/thiagoodev-portfolio/services/projects_sync/internal/domain/entities"
	"github.com/jackc/pgx/v5"
	"github.com/jackc/pgx/v5/pgxpool"
)

var projectColumns = []string{
	"external_id",
	"name",
	"description",
	"url",
	"languages",
	"updated_at",
}

type ProjectsDatabaseRepository struct {
	pool *pgxpool.Pool
}

func NewProjectsDatabaseRepository(pool *pgxpool.Pool) *ProjectsDatabaseRepository {
	return &ProjectsDatabaseRepository{pool: pool}
}

func (p *ProjectsDatabaseRepository) GetAll(ctx context.Context) ([]entities.Project, error) {
	rows, err := p.pool.Query(ctx, "SELECT * FROM projects")
	if err != nil {
		return nil, err
	}

	defer rows.Close()

	projects, err := pgx.CollectRows(
		rows,
		pgx.RowToStructByName[entities.Project],
	)
	if err != nil {
		return nil, err
	}

	return projects, nil
}

func (p *ProjectsDatabaseRepository) CreateAll(
	ctx context.Context,
	projects []entities.Project,
) error {
	_, err := p.pool.CopyFrom(
		ctx,
		pgx.Identifier{"projects"},
		projectColumns,
		pgx.CopyFromSlice(len(projects), func(i int) ([]any, error) {
			return p.projectToRow(projects[i]), nil
		}),
	)

	return err
}

func (p *ProjectsDatabaseRepository) DeleteAll(ctx context.Context) error {
	_, err := p.pool.Exec(ctx, "DELETE FROM projects")
	return err
}

func (p *ProjectsDatabaseRepository) projectToRow(project entities.Project) []any {
	return []any{
		project.ExternalId,
		project.Name,
		project.Description,
		project.Url,
		project.Languages,
		project.UpdatedAt,
	}
}
