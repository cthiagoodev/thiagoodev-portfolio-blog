CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO users
    (
     uuid,
     name,
     username,
     password,
     email,
     is_verified,
     phone,
     created_at
    ) VALUES (
      uuid_generate_v4(),
      'John Doe',
      'johndoe',
      '$2a$12$jVjvZmXNWAq9MB6HjVt6YeHaD36pHMJUomBgWO.jsQqZ64/1lvePK',
      'john@doe.com',
      true,
      '99999999999',
      '2025-07-27T21:29:03'
     )
