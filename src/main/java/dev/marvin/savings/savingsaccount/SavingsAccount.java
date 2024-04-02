package dev.marvin.savings.savingsaccount;

import dev.marvin.savings.customer.Customer;
import dev.marvin.savings.model.enums.SavingsAccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_savings_accounts")
public class SavingsAccount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "account_name", nullable = false)
    private String accountName;
    private SavingsAccountType savingsAccountType;
    private Double balance;
    private Long createdDate;
    private Customer customer;
}
