INSERT INTO user_permissions(
     user_uuid,
     permission_uuid
) VALUES (
     (SELECT uuid FROM users WHERE username = 'johndoe'),
     (SELECT uuid FROM permissions WHERE authority = 'ROLE_USER')
);