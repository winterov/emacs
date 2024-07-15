CREATE TABLE customers
(
    id               BIGSERIAL unique not null primary key,
    type             VARCHAR(30)      not null,
    full_name        VARCHAR(350)     not null,
    abbreviated_name VARCHAR(350)     not null,
    inn              VARCHAR(20),
    kpp              VARCHAR(20),
    ogrn             VARCHAR(20),
    okpo             VARCHAR(20),
    address          VARCHAR(400),
    chief_position   VARCHAR(200),
    chief_fio        VARCHAR(200),
    passport         VARCHAR(400),
    status           VARCHAR(30)      not null,
    createdAt        TIMESTAMPTZ      not null,
    updatedAt        TIMESTAMPTZ default current_timestamp
)