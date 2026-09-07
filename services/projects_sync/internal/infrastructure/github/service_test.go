package github

import (
	"context"
	"net/http"
	"net/http/httptest"
	"net/url"
	"testing"

	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
)

func TestGithubServiceImpl_FetchRepositories(t *testing.T) {
	t.Run("should fetch repositories successfully", func(t *testing.T) {
		server := httptest.NewServer(
			http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
				assert.Equal(t, http.MethodGet, r.Method)
				assert.Equal(t, "/users/cthiagoodev/repos", r.URL.Path)

				w.Header().Set("Content-Type", "application/json")
				w.WriteHeader(http.StatusOK)

				_, _ = w.Write([]byte(`[
					{
						"id": 123,
						"name": "portfolio",
						"html_url": "https://github.com/cthiagoodev/portfolio"
					}
				]`))
			}),
		)

		defer server.Close()

		baseURL, err := url.Parse(server.URL)
		require.NoError(t, err)

		ctx := context.TODO()
		service := NewGithubServiceImpl(
			server.Client(),
			baseURL,
		)

		result, err := service.FetchRepositories(ctx)

		require.NoError(t, err)
		require.Len(t, result, 1)

		assert.Equal(t, int64(123), result[0].Id)
		assert.Equal(t, "portfolio", result[0].Name)
		assert.Equal(
			t,
			"https://github.com/cthiagoodev/portfolio",
			result[0].HtmlUrl,
		)
	})

	t.Run("should return error when github returns non 200", func(t *testing.T) {
		server := httptest.NewServer(
			http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
				w.WriteHeader(http.StatusInternalServerError)
			}),
		)
		defer server.Close()

		baseURL, err := url.Parse(server.URL)
		require.NoError(t, err)

		ctx := context.TODO()
		service := NewGithubServiceImpl(
			server.Client(),
			baseURL,
		)

		result, err := service.FetchRepositories(ctx)

		require.Error(t, err)
		assert.Nil(t, result)
		assert.EqualError(
			t,
			err,
			"unexpected status code: 500",
		)
	})

	t.Run("should return error when response contains invalid json", func(t *testing.T) {
		server := httptest.NewServer(
			http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
				w.WriteHeader(http.StatusOK)

				_, _ = w.Write([]byte(`invalid-json`))
			}),
		)
		defer server.Close()

		baseURL, err := url.Parse(server.URL)
		require.NoError(t, err)

		ctx := context.TODO()
		service := NewGithubServiceImpl(
			server.Client(),
			baseURL,
		)

		result, err := service.FetchRepositories(ctx)

		require.Error(t, err)
		assert.Nil(t, result)
	})

	t.Run("should return error when request fails", func(t *testing.T) {
		server := httptest.NewServer(
			http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {}),
		)

		baseURL, err := url.Parse(server.URL)
		require.NoError(t, err)

		client := server.Client()

		server.Close()

		ctx := context.TODO()
		service := NewGithubServiceImpl(
			client,
			baseURL,
		)

		result, err := service.FetchRepositories(ctx)

		require.Error(t, err)
		assert.Nil(t, result)
	})
}
