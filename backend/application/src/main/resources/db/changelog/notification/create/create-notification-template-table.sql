CREATE TABLE notification_template
(
    id         BIGSERIAL unique not null primary key,
    event      VARCHAR(40)      NOT NULL,
    template   TEXT             NOT NULL,
    is_enabled BOOLEAN          NOT NULL default true,
    createdAt  TIMESTAMPTZ      NOT NULL,
    updatedAt  TIMESTAMPTZ               default current_timestamp
)