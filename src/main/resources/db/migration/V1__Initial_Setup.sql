CREATE SCHEMA Savings IF NOT EXISTS;

-- Customer table
CREATE TABLE tbl_customer IF NOT EXISTS (
    customer_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    mobile INT NOT NULL,
    government_id INT NOT NULL,
    member_number VARCHAR (25) NOT NULL
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Account table
CREATE TABLE tbl_account IF NOT EXITS (
    account_id SERIAL PRIMARY KEY,
    customer_id INT REFERENCES Customer(customer_id),
    account_type VARCHAR(50) NOT NULL,
    balance DECIMAL(15, 2) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Transaction table
CREATE TABLE tbl_transaction IF NOT EXISTS (
    transaction_id SERIAL PRIMARY KEY,
    account_id INT REFERENCES Account(account_id),
    transaction_type VARCHAR(50) NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
