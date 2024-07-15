ALTER TABLE bank_accounts
    ADD CONSTRAINT fk_bank_accounts_customers
        FOREIGN KEY (customer_id)
            REFERENCES bank_accounts (id)