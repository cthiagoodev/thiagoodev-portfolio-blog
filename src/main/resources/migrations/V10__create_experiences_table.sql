CREATE TABLE experiences
(
    uuid        UUID                        NOT NULL,
    position    VARCHAR(255)                NOT NULL,
    description VARCHAR(255),
    company     VARCHAR(255)                NOT NULL,
    start_date  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    end_date    TIMESTAMP WITHOUT TIME ZONE,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_experiences PRIMARY KEY (uuid)
);