ALTER TABLE cash_transaction ADD COLUMN member_id BIGINT;
ALTER TABLE cash_transaction ADD COLUMN membership_id BIGINT;
ALTER TABLE cash_transaction ADD CONSTRAINT fk_cash_transaction_member FOREIGN KEY (member_id) REFERENCES member (member_id);
ALTER TABLE cash_transaction ADD CONSTRAINT fk_cash_transaction_membership FOREIGN KEY (membership_id) REFERENCES membership (membership_id);
