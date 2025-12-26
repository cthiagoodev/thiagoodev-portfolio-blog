INSERT INTO profile (
    uuid,
    linkedin_url,
    github_url,
    x_url,
    community_url,
    created_at,
    updated_at
) VALUES (
     gen_random_uuid(),
     'https://www.linkedin.com/in/thiagoodev',
     'https://github.com/cthiagoodev',
     'https://x.com/thiagoodev',
     'https://chat.whatsapp.com/FrD3kbmvW5bB2hMqZBlwyA',
     now(),
     now()
 );