CREATE TABLE IF NOT EXISTS stats
(
    id        serial PRIMARY KEY,
    app       VARCHAR(255)                NOT NULL,
    uri       VARCHAR(512)                NOT NULL,
    ip        VARCHAR(255)                NOT NULL,
    timestamp TIMESTAMP without TIME ZONE NOT NULL
);