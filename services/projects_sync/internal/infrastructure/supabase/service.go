package supabase

import "github.com/cthiagoodev/thiagoodev-portfolio/services/projects_sync/internal/domain/entities"

type SupabaseService interface {
	SaveAndReplaceAll(projects []entities.Project) error
}
