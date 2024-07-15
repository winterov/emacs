CREATE TABLE bank_accounts
(
    id                    BIGSERIAL    not null primary key,
    bankName              VARCHAR(200) not null,
    bik                   VARCHAR(9)   not null,
    payment_account       VARCHAR(20)  not null,
    correspondent_account VARCHAR(20)  not null,
    customer_id           BIGINT       not null,
    status                VARCHAR(30)  not null,
    created_at             TIMESTAMPTZ  not null,
    updated_at             TIMESTAMPTZ default current_timestamp
)