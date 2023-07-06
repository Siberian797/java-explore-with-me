DROP TABLE if EXISTS categories;

CREATE TABLE if NOT EXISTS categories
(
    id   serial PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);