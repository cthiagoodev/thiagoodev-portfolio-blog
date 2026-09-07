package github

import (
	"context"
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"net/url"
)

type GithubService interface {
	FetchRepositories(ctx context.Context) ([]Project, error)
}

type GithubServiceImpl struct {
	client  *http.Client
	baseURL *url.URL
}

func NewGithubServiceImpl(client *http.Client, baseURL *url.URL) *GithubServiceImpl {
	return &GithubServiceImpl{
		client,
		baseURL,
	}
}

func (s *GithubServiceImpl) FetchRepositories(ctx context.Context) ([]Project, error) {
	path := s.baseURL.String() + "/users/cthiagoodev/repos"

	req, err := http.NewRequestWithContext(ctx, http.MethodGet, path, nil)
	if err != nil {
		return nil, err
	}

	response, err := s.client.Do(req)
	if err != nil {
		return nil, err
	}
	defer response.Body.Close()

	if response.StatusCode != http.StatusOK {
		return nil, fmt.Errorf("unexpected status code: %d", response.StatusCode)
	}

	bytes, rErr := io.ReadAll(response.Body)

	if rErr != nil {
		return nil, rErr
	}

	projects := make([]Project, 0)

	jErr := json.Unmarshal(bytes, &projects)

	if jErr != nil {
		return nil, jErr
	}

	return projects, nil
}
