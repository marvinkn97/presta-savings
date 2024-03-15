package dev.marvin.savings.model;

import dev.marvin.savings.model.enums.PaymentMethod;
import dev.marvin.savings.model.enums.TransactionType;
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
