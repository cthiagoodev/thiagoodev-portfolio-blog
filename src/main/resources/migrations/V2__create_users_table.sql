CREATE TABLE user_permissions
(
    permission_uuid UUID NOT NULL,
    user_uuid       UUID NOT NULL,
    CONSTRAINT pk_user_permissions PRIMARY KEY (permission_uuid, user_uuid)
);

CREATE TABLE users
(
    uuid        UUID                        NOT NULL,
    name        VARCHAR(255)                NOT NULL,
    username    VARCHAR(255)                NOT NULL,
    password    VARCHAR(255)                NOT NULL,
    email       VARCHAR(255)                NOT NULL,
    is_verified BOOLEAN                     NOT NULL,
    phone       VARCHAR(255),
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    deleted_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_users PRIMARY KEY (uuid)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uc_users_phone UNIQUE (phone);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER TABLE user_permissions
    ADD CONSTRAINT fk_useper_on_permission_model FOREIGN KEY (permission_uuid) REFERENCES permissions (uuid);

ALTER TABLE user_permissions
    ADD CONSTRAINT fk_useper_on_user_model FOREIGN KEY (user_uuid) REFERENCES users (uuid);