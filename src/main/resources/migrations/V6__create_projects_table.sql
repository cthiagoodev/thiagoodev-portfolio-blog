CREATE TABLE project_topics
(
    project_id BIGINT NOT NULL,
    topic      VARCHAR(255)
);

CREATE TABLE projects
(
    id          BIGINT                      NOT NULL,
    name        VARCHAR(255)                NOT NULL,
    full_name   VARCHAR(255)                NOT NULL,
    html_url    VARCHAR(255)                NOT NULL,
    description VARCHAR(255),
    language    VARCHAR(255),
    homepage    VARCHAR(255),
    pushed_at   TIMESTAMP WITHOUT TIME ZONE,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_projects PRIMARY KEY (id)
);

ALTER TABLE project_topics
    ADD CONSTRAINT uc_e59ec19ddde3ceffdf85bd5f4 UNIQUE (project_id, topic);

ALTER TABLE projects
    ADD CONSTRAINT uc_projects_fullname UNIQUE (full_name);

ALTER TABLE projects
    ADD CONSTRAINT uc_projects_name UNIQUE (name);

ALTER TABLE project_topics
    ADD CONSTRAINT fk_project_topics_on_project FOREIGN KEY (project_id) REFERENCES projects (id);