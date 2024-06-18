package dev.marvin.savings.savingsaccount;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.marvin.savings.appuser.customer.Customer;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_savings_accounts")
public class SavingsAccount implements Serializable {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "account_name", nullable = false)
    private String accountName;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private SavingsAccountType savingsAccountType;

    @Column(nullable = false, columnDefinition = "DECIMAL(15,2)")
    private BigDecimal balance;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "customer_id_fk"))
    private Customer customer;
}
