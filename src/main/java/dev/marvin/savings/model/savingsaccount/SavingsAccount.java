package dev.marvin.savings.model.savingsaccount;

import dev.marvin.savings.model.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingsAccount implements Serializable {
    private String accountNumber;
    private String accountName;
    private SavingsAccountType savingsAccountType;
    private Double balance;
    private Long createdDate;
    private Customer customer;
}
