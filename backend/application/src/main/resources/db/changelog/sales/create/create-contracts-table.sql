CREATE TABLE contracts
(
    id          BIGSERIAL not null primary key,
    number      VARCHAR(50)      not null,
    date        DATE             not null,
    customer_id BIGINT           not null,
    createdAt   TIMESTAMPTZ      not null,
    updatedAt   TIMESTAMPTZ default current_timestamp
)