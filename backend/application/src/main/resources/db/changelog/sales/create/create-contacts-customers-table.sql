CREATE TABLE contacts_customers
(
    id BIGSERIAL not null primary key,
    contacts_id BIGINT,
    customers_id BIGINT
)