create table playlist(
    id bigserial primary key,
    name varchar(255)
);

create table track(
    id bigserial primary key,
    name varchar(255)
);

create table track_history(
    id bigserial primary key,
    play_date timestamp(6),
    track_id bigint constraint FK_track_history_track references track
);

create table tracks_playlists(
    track_id bigint not null,
    playlist_id bigint not null
)
