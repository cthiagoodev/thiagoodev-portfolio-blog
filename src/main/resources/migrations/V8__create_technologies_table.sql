CREATE TABLE technologies
(
    uuid       UUID         NOT NULL,
    name       VARCHAR(255) NOT NULL,
    icon       VARCHAR(255) NOT NULL,
    url        VARCHAR(255) NOT NULL,
    color      VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_technologies PRIMARY KEY (uuid)
);