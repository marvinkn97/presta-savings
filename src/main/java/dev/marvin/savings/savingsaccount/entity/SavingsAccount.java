package dev.marvin.savings.savingsaccount.entity;

import dev.marvin.savings.customer.model.Customer;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@Data
public class SavingsAccount implements Serializable {
    private String accountNumber;
    private String accountName;
    private SavingsAccountType savingsAccountType;
    private BigDecimal balance;
    private Long createdAt;
    private Deleted isDeleted;
    private Customer customer;
}
