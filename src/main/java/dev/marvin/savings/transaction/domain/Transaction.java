package dev.marvin.savings.transaction.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private Integer id;
    private String transactionCode;
    private PaymentMethod paymentMethod;
    private LocalDateTime createdAt;
    private BigDecimal amount;


}
