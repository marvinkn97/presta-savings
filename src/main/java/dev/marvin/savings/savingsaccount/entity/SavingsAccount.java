package dev.marvin.savings.savingsaccount.entity;

import dev.marvin.savings.customer.entity.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class SavingsAccount implements Serializable {
    private Integer id;
    private String accountNumber;
    private SavingsAccountType savingsAccountType;
    private BigDecimal balance;
    private LocalDateTime createdAt;
    private Customer customer;

}
