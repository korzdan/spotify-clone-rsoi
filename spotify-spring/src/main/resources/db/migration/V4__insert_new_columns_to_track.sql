ALTER TABLE track
    ADD COLUMN genre       varchar(64)  DEFAULT 'UNKNOWN',
    ADD COLUMN description varchar(512) DEFAULT 'Some description',
    ADD COLUMN is_liked    bool         DEFAULT false,
    ADD COLUMN is_blocked  bool         DEFAULT false,
    ADD COLUMN image_url   varchar(1012);
