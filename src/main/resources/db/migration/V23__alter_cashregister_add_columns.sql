ALTER TABLE cash_register
ADD COLUMN closeDate DATE,
ADD COLUMN isOpen BOOLEAN NOT NULL DEFAULT FALSE;