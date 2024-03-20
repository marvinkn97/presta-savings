-- Use the presta_savings database
USE presta_savings;

CREATE TABLE t_user (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255),
    created_date TIMESTAMP
    UNIQUE KEY email_UNIQUE (email)
);

CREATE TABLE t_customer (
    member_number VARCHAR(25) NOT NULL,
    user_id INT,
    mobile VARCHAR(255),
    government_id INT,
    profile_image_id VARCHAR(255),
    PRIMARY KEY (member_number),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE t_savings_account (
  account_number VARCHAR(25) PRIMARY KEY,
  account_name VARCHAR(45) NOT NULL,
  account_type VARCHAR(45) NOT NULL,
  balance DECIMAL(10,2) NOT NULL,
  created_date BIGINT NOT NULL,
  member_no VARCHAR(25) NOT NULL,
  CONSTRAINT member_no_fk FOREIGN KEY (member_no) REFERENCES t_customer (member_number)
);

CREATE TABLE t_transaction (
  transaction_code VARCHAR(25) PRIMARY KEY,
  transaction_type VARCHAR(25) NOT NULL,
  payment_method VARCHAR(25) NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  created_date BIGINT NOT NULL,
  account_no VARCHAR(25) NOT NULL,
  CONSTRAINT account_no_fk FOREIGN KEY (account_no) REFERENCES t_savings_account (account_number)
);

