package scheduler

import (
	"context"
	"log"

	"github.com/cthiagoodev/thiagoodev-portfolio/services/projects_sync/internal/domain/usecases"
	"github.com/cthiagoodev/thiagoodev-portfolio/services/projects_sync/internal/infrastructure/supabase"
	"github.com/robfig/cron/v3"
)

type SyncProjectsScheduler struct {
	syncProjectsUseCase usecases.SyncProjectsUseCase
	supabaseService     supabase.SupabaseService
}

func NewSyncProjectsScheduler(
	syncProjectsUseCase usecases.SyncProjectsUseCase,
	supabaseService supabase.SupabaseService,
) *SyncProjectsScheduler {
	return &SyncProjectsScheduler{syncProjectsUseCase, supabaseService}
}

func (s *SyncProjectsScheduler) Schedule() {
	ctx := context.Background()

	c := cron.New(cron.WithChain(
		cron.SkipIfStillRunning(cron.DefaultLogger),
	))

	id, err := c.AddFunc("0 3 * * *", func() {
		projects, err := s.syncProjectsUseCase.Execute(ctx)
		if err != nil {
			log.Printf("Failed to execute sync projects for projects: %v", err)
			return
		}

		sErr := s.supabaseService.SaveAndReplaceAll(projects)
		if sErr != nil {
			log.Printf("Failed to replace all projects: %v", sErr)
		}
	})

	if err != nil {
		c.Stop()
		c.Remove(id)
		return
	}

	c.Start()
}
