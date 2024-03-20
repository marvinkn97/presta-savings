CREATE TABLE presta_savings.t_user (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password TEXT NOT NULL,
    role VARCHAR(25) NOT NULL,
    created_date BIGINT NOT NULL,
    UNIQUE KEY email_UNIQUE (email)
);

CREATE TABLE presta_savings.t_customer (
    member_number VARCHAR(25) PRIMARY KEY,
    mobile VARCHAR(25),
    government_id INT,
    profile_image_id VARCHAR(255),
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES t_user(user_id)
);

--CREATE TABLE t_savings_account (
--  account_number VARCHAR(25) PRIMARY KEY,
--  account_name VARCHAR(45) NOT NULL,
--  account_type VARCHAR(45) NOT NULL,
--  balance DECIMAL(10,2) NOT NULL,
--  created_date BIGINT NOT NULL,
--  member_no VARCHAR(25) NOT NULL,
--  CONSTRAINT member_no_fk FOREIGN KEY (member_no) REFERENCES t_customer (member_number)
--);
--
--CREATE TABLE t_transaction (
--  transaction_code VARCHAR(25) PRIMARY KEY,
--  transaction_type VARCHAR(25) NOT NULL,
--  payment_method VARCHAR(25) NOT NULL,
--  amount DECIMAL(10,2) NOT NULL,
--  created_date BIGINT NOT NULL,
--  account_no VARCHAR(25) NOT NULL,
--  CONSTRAINT account_no_fk FOREIGN KEY (account_no) REFERENCES t_savings_account (account_number)
--);

