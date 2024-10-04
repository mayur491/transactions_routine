DROP TABLE IF EXISTS transactions;

DROP TABLE IF EXISTS operation_types;

DROP TABLE IF EXISTS accounts;

CREATE TABLE operation_types (
    operation_type_id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE accounts (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    document_number VARCHAR(255) NOT NULL
);

CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    operation_type_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    event_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id),
    FOREIGN KEY (operation_type_id) REFERENCES operation_types(operation_type_id)
);

INSERT INTO operation_types (description) VALUES ('Normal Purchase');
INSERT INTO operation_types (description) VALUES ('Purchase with installments');
INSERT INTO operation_types (description) VALUES ('Withdrawal');
INSERT INTO operation_types (description) VALUES ('Credit Voucher');

INSERT INTO accounts (document_number) VALUES ('10000001');
INSERT INTO accounts (document_number) VALUES ('10000002');
INSERT INTO accounts (document_number) VALUES ('10000003');
INSERT INTO accounts (document_number) VALUES ('10000004');
INSERT INTO accounts (document_number) VALUES ('10000005');
INSERT INTO accounts (document_number) VALUES ('10000006');
INSERT INTO accounts (document_number) VALUES ('10000007');
INSERT INTO accounts (document_number) VALUES ('10000008');
INSERT INTO accounts (document_number) VALUES ('10000009');
INSERT INTO accounts (document_number) VALUES ('10000010');

INSERT INTO transactions (account_id, operation_type_id, amount) VALUES (3, 1, -123.456);
INSERT INTO transactions (account_id, operation_type_id, amount) VALUES (7, 4, 987.7654);
