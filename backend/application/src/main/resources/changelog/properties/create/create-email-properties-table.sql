CREATE TABLE email_properties
(
    id                                 BIGSERIAL unique not null primary key,
    type                               VARCHAR(20),
    is_enabled                         BOOLEAN          not null,
    description                        TEXT,
    email                              VARCHAR(100)     not null,
    password                           VARCHAR(250)     not null,
    smtp_host                          VARCHAR(300),
    smtp_require_auth                  BOOLEAN          not null,
    smtp_protocol                      VARCHAR(4)       not null,
    smtp_port_ssl                      INTEGER,
    smtp_port_tls                      INTEGER,
    smtp_connection_timeout            INTEGER,
    smtp_timeout                       INTEGER,
    incoming_server_type               VARCHAR(20),
    incoming_imap_server               VARCHAR(100),
    incoming_enabled_ssl               BOOLEAN,
    incoming_port_ssl                  INTEGER,
    incoming_server_connection_timeout INTEGER,
    incoming_server_timeout            INTEGER
)