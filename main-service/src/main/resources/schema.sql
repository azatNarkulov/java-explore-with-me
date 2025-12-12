DROP TABLE IF EXISTS compilation_events;
DROP TABLE IF EXISTS compilations;
DROP TABLE IF EXISTS requests;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;

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
    initiator_id       INTEGER NOT NULL REFERENCES users(id),
    category_id        INTEGER NOT NULL REFERENCES categories(id),
    event_date         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
--    confirmed_requests INTEGER,
    lat                FLOAT,
    lon                FLOAT,
    paid               BOOLEAN DEFAULT FALSE, -- NOT NULL ?
    participant_limit  INTEGER DEFAULT 0,
    request_moderation BOOLEAN DEFAULT TRUE,
    state              VARCHAR(255) NOT NULL,
--    views              INTEGER DEFAULT 0, -- нужно ли это тут, учитывая, что views берётся из сервиса статистики?
    created_on         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    published_on       TIMESTAMP WITHOUT TIME ZONE
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

CREATE TABLE IF NOT EXISTS compilation_events (
    compilation_id BIGINT NOT NULL REFERENCES compilations(id),
    event_id       BIGINT NOT NULL REFERENCES compilations(id),
    PRIMARY KEY (compilation_id, event_id)
);