CREATE TABLE contacts
(
    id BIGSERIAL not null primary key,
    full_name VARCHAR(100),
    phone VARCHAR(50),
    email VARCHAR(50),
    comment TEXT

)