CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
INSERT INTO technologies (uuid, name, icon, url, color, created_at, updated_at) VALUES
    (gen_random_uuid(), 'Codemagic', '/codemagic.svg', 'https://codemagic.io', '#2396F3', NOW(), NOW()),
    (gen_random_uuid(), 'Dart', '/dart.svg', 'https://dart.dev', '#0175C2', NOW(), NOW()),
    (gen_random_uuid(), 'Docker', '/docker.svg', 'https://www.docker.com', '#0db7ed', NOW(), NOW()),
    (gen_random_uuid(), 'Firebase', '/firebase.svg', 'https://firebase.google.com', '#FFCA28', NOW(), NOW()),
    (gen_random_uuid(), 'Flutter', '/flutter.svg', 'https://flutter.dev', '#02569B', NOW(), NOW()),
    (gen_random_uuid(), 'Jetpack Compose', '/jetpackcompose.svg', 'https://developer.android.com/jetpack/compose', '#4285F4', NOW(), NOW()),
    (gen_random_uuid(), 'Kotlin', '/kotlin.svg', 'https://kotlinlang.org', '#7F52FF', NOW(), NOW()),
    (gen_random_uuid(), 'Linux', '/linux.svg', 'https://www.linux.org', '#FCC624', NOW(), NOW()),
    (gen_random_uuid(), 'macOS', '/macos.svg', 'https://www.apple.com/macos', '#000000', NOW(), NOW()),
    (gen_random_uuid(), 'Java', '/openjdk.svg', 'https://openjdk.org', '#5382A1', NOW(), NOW()),
    (gen_random_uuid(), 'Python', '/python.svg', 'https://www.python.org', '#3776AB', NOW(), NOW()),
    (gen_random_uuid(), 'Spring', '/spring.svg', 'https://spring.io', '#6DB33F', NOW(), NOW());