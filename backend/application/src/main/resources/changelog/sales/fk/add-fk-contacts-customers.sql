ALTER TABLE contacts_customers
    ADD CONSTRAINT fk_contacts_customers_contact
        FOREIGN KEY (contacts_id)
            REFERENCES contacts(id);
ALTER TABLE contacts_customers
    ADD CONSTRAINT fk_contacts_customers_customer
        FOREIGN KEY (customers_id)
            REFERENCES customers(id)