CREATE TABLE payment_type (
                               payment_type_id SERIAL PRIMARY KEY,
                               name VARCHAR(255) NOT NULL,
                               is_deleted BOOLEAN DEFAULT false
);

CREATE TABLE cash_register (
                               cash_register_id SERIAL PRIMARY KEY,
                               initial_balance NUMERIC(10, 2) NOT NULL,
                               current_balance NUMERIC(10, 2) NOT NULL,
                               is_deleted BOOLEAN DEFAULT false
);

CREATE TABLE cash_transaction (
                                  cash_transaction_id SERIAL PRIMARY KEY,
                                  cash_register_id INT REFERENCES cash_register(cash_register_id),
                                  amount NUMERIC(10, 2) NOT NULL,
                                  transaction_date TIMESTAMP NOT NULL,
                                  description TEXT,
                                  is_deleted BOOLEAN DEFAULT false
);

CREATE TABLE invoice (
                          invoice_id SERIAL PRIMARY KEY,
                          invoice_number VARCHAR(255) UNIQUE NOT NULL,
                          invoice_date DATE NOT NULL,
                          client_id INT REFERENCES person(person_id),
                          tax_rate NUMERIC(5, 2),
                          total_amount NUMERIC(10, 2),
                          payment_type_id INT REFERENCES payment_type(payment_type_id),
                          is_deleted BOOLEAN DEFAULT false
);

CREATE TABLE invoice_item (
                               item_id SERIAL PRIMARY KEY,
                               invoice_id INT REFERENCES invoice(invoice_id),
                               description TEXT,
                               quantity INT,
                               unit_price NUMERIC(10, 2),
                               subtotal NUMERIC(10, 2),
                               is_deleted BOOLEAN DEFAULT false
);

CREATE TABLE payment (
                          payment_id SERIAL PRIMARY KEY,
                          invoice_id INT REFERENCES invoice(invoice_id),
                          payment_type_id INT REFERENCES payment_type(payment_type_id),
                          amount NUMERIC(10, 2),
                          payment_date DATE,
                          is_deleted BOOLEAN DEFAULT false
);

ALTER TABLE invoice_item ADD CONSTRAINT fk_invoice FOREIGN KEY (invoice_id) REFERENCES invoice(invoice_id);
ALTER TABLE invoice ADD CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES person(person_id);
ALTER TABLE invoice ADD CONSTRAINT fk_payment_type FOREIGN KEY (payment_type_id) REFERENCES payment_type(payment_type_id);
ALTER TABLE payment ADD CONSTRAINT fk_invoice FOREIGN KEY (invoice_id) REFERENCES invoice(invoice_id);
ALTER TABLE payment ADD CONSTRAINT fk_payment_type FOREIGN KEY (payment_type_id) REFERENCES payment_type(payment_type_id);
ALTER TABLE cash_transaction ADD CONSTRAINT fk_cash_register FOREIGN KEY (cash_register_id) REFERENCES cash_register(cash_register_id);