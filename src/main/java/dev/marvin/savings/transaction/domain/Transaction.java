package dev.marvin.savings.transaction.domain;

import dev.marvin.savings.savingsaccount.entity.SavingsAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class Transaction implements Serializable {
    private Integer id;
    private String transactionCode;
    private TransactionType transactionType;
    private PaymentMethod paymentMethod;
    private BigDecimal amount;
    private LocalDateTime createdDate;
    private SavingsAccount savingsAccount;
}
