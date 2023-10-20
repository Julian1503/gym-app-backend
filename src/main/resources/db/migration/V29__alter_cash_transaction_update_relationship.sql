ALTER TABLE cash_transaction DROP CONSTRAINT fk_cash_transaction_member;
ALTER TABLE cash_transaction
    ADD CONSTRAINT fk_cash_transaction_member
        FOREIGN KEY (member_id) REFERENCES member (person_id);