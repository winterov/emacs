CREATE TABLE notification_template
(
    id              BIGSERIAL unique not null primary key,
    event_class     VARCHAR(440)     NOT NULL,
    handler_class   VARCHAR(440)     NOT NULL,
    template        TEXT             NOT NULL,
    is_enabled      BOOLEAN          NOT NULL default true,
    created_at      TIMESTAMPTZ      NOT NULL,
    updated_at      TIMESTAMPTZ      default current_timestamp
)