CREATE TABLE `savings_db`.`t_customer` (
  `member_number` VARCHAR(25) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `email` VARCHAR(150) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `mobile` INT NOT NULL,
  `government_id` INT NOT NULL,
  `created_date` BIGINT NOT NULL,
  `is_deleted` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`member_number`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE);


CREATE TABLE `savings_db`.`t_savings_account` (
  `account_number` VARCHAR(25) NOT NULL,
  `account_name` VARCHAR(50) NOT NULL,
  `account_type` VARCHAR(25) NOT NULL,
  `balance` DOUBLE NOT NULL,
  `created_date` BIGINT NOT NULL,
  `is_deleted` VARCHAR(5) NOT NULL,
  `customer_id` INT NOT NULL,
  PRIMARY KEY (`account_number`),
  INDEX `customer_id_fk_idx` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `customer_id_fk`
    FOREIGN KEY (`customer_id`)
    REFERENCES `savings_db`.`t_customer` (`member_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE `savings_db`.`t_transaction` (
  `transaction_code` VARCHAR(50) NOT NULL,
  `transaction_type` VARCHAR(15) NOT NULL,
  `created_date` BIGINT NOT NULL,
  `amount` DOUBLE NOT NULL,
  `account_id` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`transaction_code`),
  INDEX `account_id_fk_idx` (`account_id` ASC) VISIBLE,
  CONSTRAINT `account_id_fk`
    FOREIGN KEY (`account_id`)
    REFERENCES `savings_db`.`t_savings_account` (`account_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
