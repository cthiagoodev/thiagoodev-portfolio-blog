package entities

import "time"

type Project struct {
	Uuid        string    `json:"uuid" db:"uuid"`
	ExternalId  string    `json:"external_id" db:"external_id"`
	Name        string    `json:"name" db:"name"`
	Description *string   `json:"description" db:"description"`
	Url         string    `json:"url" db:"url"`
	Languages   []string  `json:"languages" db:"languages"`
	CreatedAt   time.Time `json:"created_at" db:"created_at"`
	UpdatedAt   time.Time `json:"updated_at" db:"updated_at"`
}
