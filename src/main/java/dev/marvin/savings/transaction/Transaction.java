package dev.marvin.savings.transaction;

import dev.marvin.savings.savingsaccount.SavingsAccount;
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
@Table(name = "t_transactions")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;

    @Column(name = "transaction_code", unique = true, nullable = false)
    private String transactionCode;

    @Column(name = "transaction_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(nullable = false, columnDefinition = "DECIMAL(15,2)")
    private BigDecimal amount;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false)
    private Long createdDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "savings_account_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "savings_account_id_fk"))
    private SavingsAccount savingsAccount;
}
