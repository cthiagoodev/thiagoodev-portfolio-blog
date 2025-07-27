CREATE TABLE permissions
(
    uuid      UUID         NOT NULL,
    authority VARCHAR(255) NOT NULL,
    CONSTRAINT pk_permissions PRIMARY KEY (uuid)
);

ALTER TABLE permissions
    ADD CONSTRAINT uc_permissions_authority UNIQUE (authority);