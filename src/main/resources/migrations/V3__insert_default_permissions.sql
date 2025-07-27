CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
INSERT INTO permissions (uuid, authority) VALUES (uuid_generate_v4(), 'ROLE_ADMIN');
INSERT INTO permissions (uuid, authority) VALUES (uuid_generate_v4(), 'ROLE_USER');
INSERT INTO permissions (uuid, authority) VALUES (uuid_generate_v4(), 'ROLE_MODERATOR');
