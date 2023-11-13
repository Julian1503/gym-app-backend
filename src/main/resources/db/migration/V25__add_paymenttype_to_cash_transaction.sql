ALTER TABLE cash_transaction
    ADD COLUMN payment_type_id BIGINT;

ALTER TABLE cash_transaction
    ADD CONSTRAINT fk_cash_transaction_payment_type
        FOREIGN KEY (payment_type_id)
            REFERENCES payment_type (payment_type_id);
