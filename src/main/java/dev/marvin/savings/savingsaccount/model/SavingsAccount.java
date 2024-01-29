package dev.marvin.savings.savingsaccount.model;

import dev.marvin.savings.customer.model.Customer;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
@Builder
@Data
public class SavingsAccount implements Serializable {
    private String accountNumber;
    private String accountName;
    private SavingsAccountType savingsAccountType;
    private Double balance;
    private Long createdAt;
    private Customer customer;
}
