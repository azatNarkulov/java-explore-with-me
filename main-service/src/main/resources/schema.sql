DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS requests;
DROP TABLE IF EXISTS compilations;

CREATE TABLE IF NOT EXISTS users (
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS categories (
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS events (
    id                 BIGSERIAL PRIMARY KEY,
    title              VARCHAR(255) NOT NULL,
    annotation         VARCHAR(255) NOT NULL,
    description        VARCHAR(255) NOT NULL,
    initiator_id       INTEGER NOT NULL,
    category_id        INTEGER NOT NULL,
    event_date         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    confirmed_requests INTEGER,
    lat                FLOAT,
    lon                FLOAT,
    pain               BOOLEAN DEFAULT FALSE,
    participant_limit  INTEGER DEFAULT 0,
    request_moderation BOOLEAN DEFAULT TRUE,
    state              VARCHAR(255) NOT NULL,
    views              INTEGER DEFAULT 0,
    created_on         TIMESTAMP WITHOUT TIME ZONE NOT NULL
    published_on       TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS requests (
    id           BIGSERIAL PRIMARY KEY,
    event_id     INTEGER NOT NULL,
    requester_id INTEGER NOT NULL,
    status       VARCHAR(255) NOT NULL,
    created      TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS compilations (
    id     BIGSERIAL PRIMARY KEY,
    title  VARCHAR(255) NOT NULL,
    pinned BOOLEAN DEFAULT FALSE
);