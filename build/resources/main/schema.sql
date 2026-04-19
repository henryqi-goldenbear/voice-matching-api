create table if not exists profiles (
    id bigserial primary key,
    name text,
    current_emotional_state text,
    current_life_chapter text,
    current_social_energy text,
    last_check_in timestamptz
);

create table if not exists experiences (
    id bigserial primary key,
    title text,
    description text,
    container_type text not null
);

create table if not exists circles (
    id bigserial primary key,
    experience_id bigint not null references experiences(id),
    status text not null
);

create table if not exists matches (
    id bigserial primary key,
    profile_id bigint not null references profiles(id),
    circle_id bigint not null references circles(id),
    status text not null,
    match_reason text,
    unique(profile_id, circle_id)
);

create table if not exists voice_sessions (
    id bigserial primary key,
    profile_id bigint not null references profiles(id),
    transcript text,
    extracted_emotion text,
    extracted_chapter text,
    extracted_energy text,
    created_at timestamptz default now()
);

create index if not exists idx_profiles_last_check_in on profiles(last_check_in);
create index if not exists idx_experiences_container_type on experiences(container_type);
create index if not exists idx_circles_experience_status on circles(experience_id, status);
create index if not exists idx_matches_profile_id on matches(profile_id);
create index if not exists idx_voice_sessions_profile_created on voice_sessions(profile_id, created_at desc);
