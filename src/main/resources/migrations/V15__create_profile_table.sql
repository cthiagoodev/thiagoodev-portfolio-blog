CREATE TABLE IF NOT EXISTS profile (
    uuid UUID NOT NULL,
    curriculum VARCHAR(255),
    profile_image VARCHAR(255),
    linkedin_url VARCHAR(255),
    github_url VARCHAR(255),
    x_url VARCHAR(255),
    community_url VARCHAR(255),
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_profile PRIMARY KEY (uuid)
)