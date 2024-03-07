package dev.marvin.savings.model.transaction;

import dev.marvin.savings.model.savingsaccount.SavingsAccount;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class Transaction implements Serializable {
    private String transactionCode;
    private TransactionType transactionType;
    private PaymentMethod paymentMethod;
    private Double amount;
    private Long createdDate;
    private SavingsAccount savingsAccount;
}
