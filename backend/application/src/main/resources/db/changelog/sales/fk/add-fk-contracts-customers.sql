ALTER TABLE contracts
    ADD CONSTRAINT fk_contracts_customers
        FOREIGN KEY (customer_id)
            REFERENCES customers(id)