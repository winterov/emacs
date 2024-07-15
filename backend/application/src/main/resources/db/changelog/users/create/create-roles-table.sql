CREATE EXTENSION IF NOT EXISTS ltree;

CREATE TABLE roles
(
    id          BIGSERIAL unique not null primary key,
    path        LTREE            not null unique,
    is_catalog  BOOLEAN          not null,
    title       VARCHAR(100)     not null,
    description VARCHAR(300)     not null,
    createdAt   TIMESTAMPTZ      not null,
    updatedAt   TIMESTAMPTZ default current_timestamp,
    status      VARCHAR(20)      not null,
    modifyBy    BIGSERIAL        not null
)