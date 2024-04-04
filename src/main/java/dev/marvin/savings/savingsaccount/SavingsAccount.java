package dev.marvin.savings.savingsaccount;

import dev.marvin.savings.customer.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private SavingsAccountType savingsAccountType;

    @Column(nullable = false, columnDefinition = "DECIMAL(15,2)")
    private BigDecimal balance;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false)
    private Long createdDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "customer_id_fk"))
    private Customer customer;
}
