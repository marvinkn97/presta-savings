package dev.marvin.savings.serviceImpl;

import dev.marvin.savings.dto.TransactionRequest;
import dev.marvin.savings.entity.PaymentMethod;
import dev.marvin.savings.entity.SavingsAccount;
import dev.marvin.savings.entity.Transaction;
import dev.marvin.savings.entity.TransactionType;
import dev.marvin.savings.exception.RequestValidationException;
import dev.marvin.savings.exception.ResourceNotFoundException;
import dev.marvin.savings.repository.TransactionRepository;
import dev.marvin.savings.repository.SavingsAccountRepository;
import dev.marvin.savings.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final SavingsAccountRepository savingsAccountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction performTransaction(TransactionRequest transactionRequest) {
        SavingsAccount savingsAccount = savingsAccountRepository.findByAccountNumber(transactionRequest.accountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Savings Account does not exist"));

        PaymentMethod paymentMethod = PaymentMethod.valueOf(transactionRequest.paymentMethod().toUpperCase());
        TransactionType transactionType = TransactionType.valueOf(transactionRequest.transactionType().toUpperCase());
        BigDecimal amount = transactionRequest.amount();

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new RequestValidationException("Amount should be greater than zero");
        }

        if (transactionType.equals(TransactionType.DEPOSIT)) {
            savingsAccount.setBalance(savingsAccount.getBalance().add(amount));
        } else if (transactionType.equals(TransactionType.WITHDRAW)) {
            if (savingsAccount.getBalance().compareTo(amount) >= 0) {
                savingsAccount.setBalance(savingsAccount.getBalance().subtract(amount));
            } else {
                throw new RequestValidationException("Insufficient Account Balance");

            }
        }

        Transaction transaction = Transaction.builder()
                .transactionCode(generateTransactionCode())
                .transactionType(transactionType)
                .paymentMethod(paymentMethod)
                .amount(amount)
                .createdDate(System.currentTimeMillis())
                .savingsAccount(savingsAccount)
                .build();

        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getAllTransactionsByAccountNumber(String accountNumber) {
       return transactionRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public Transaction getTransactionByTransactionCode(String transactionCode) {
        return transactionRepository.findByTransactionCode(transactionCode)
                .orElseThrow(()-> new ResourceNotFoundException("transaction with given code [%s] does not exit".formatted(transactionCode)));
    }

    private String generateTransactionCode() {
        return "TX" + UUID.randomUUID().toString().substring(0, 7).toUpperCase();
    }


}
