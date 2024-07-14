ALTER TABLE employees
    ADD CONSTRAINT fk_users_employees
        FOREIGN KEY (user_id)
            REFERENCES users (id);
