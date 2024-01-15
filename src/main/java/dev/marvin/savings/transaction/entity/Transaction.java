package dev.marvin.savings.transaction.entity;

import dev.marvin.savings.savingsaccount.entity.SavingsAccount;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
public class Transaction implements Serializable {
    private String transactionCode;
    private TransactionType transactionType;
    private PaymentMethod paymentMethod;
    private BigDecimal amount;
    private LocalDateTime createdDate;
    private SavingsAccount savingsAccount;
}
