CREATE TABLE employees
(
    id      BIGSERIAL not null primary key,
    user_id BIGSERIAL not null,
    name    VARCHAR(50) not null ,
    surname VARCHAR(50) not null
)