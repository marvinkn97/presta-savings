-- Use the presta_savings database
USE presta_savings;

-- Table structure for table t_customer
CREATE TABLE IF NOT EXISTS t_customer (
  member_number VARCHAR(25) NOT NULL,
  customer_name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  password TEXT NOT NULL,
  mobile_no VARCHAR(15),
  government_id INT,
  created_date BIGINT,
  role VARCHAR(6) NOT NULL,
  PRIMARY KEY (member_number),
  UNIQUE KEY email_UNIQUE (email)
) ENGINE=InnoDB;

-- Table structure for table t_savings_account
CREATE TABLE IF NOT EXISTS t_savings_account (
  account_number VARCHAR(25) NOT NULL,
  account_name VARCHAR(45) NOT NULL,
  account_type VARCHAR(45) NOT NULL,
  balance DOUBLE NOT NULL,
  created_date BIGINT NOT NULL,
  member_no VARCHAR(25) NOT NULL,
  PRIMARY KEY (account_number),
  CONSTRAINT member_no_fk FOREIGN KEY (member_no) REFERENCES t_customer (member_number)
) ENGINE=InnoDB;

-- Table structure for table t_transaction
CREATE TABLE IF NOT EXISTS t_transaction (
  transaction_code VARCHAR(25) NOT NULL,
  transaction_type VARCHAR(25) NOT NULL,
  payment_method VARCHAR(25) NOT NULL,
  amount DOUBLE NOT NULL,
  created_date BIGINT NOT NULL,
  account_no VARCHAR(25) NOT NULL,
  PRIMARY KEY (transaction_code),
  CONSTRAINT account_no_fk FOREIGN KEY (account_no) REFERENCES t_savings_account (account_number)
) ENGINE=InnoDB;
