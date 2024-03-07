package dev.marvin.savings.transaction.service;

import dev.marvin.savings.dao.savingsaccount.SavingsAccountDao;
import dev.marvin.savings.exception.InsufficientAmountException;
import dev.marvin.savings.model.savingsaccount.SavingsAccount;
import dev.marvin.savings.transaction.dao.TransactionDao;
import dev.marvin.savings.transaction.dto.TransactionRequest;
import dev.marvin.savings.transaction.dto.TransactionResponse;
import dev.marvin.savings.transaction.model.PaymentMethod;
import dev.marvin.savings.transaction.model.Transaction;
import dev.marvin.savings.transaction.model.TransactionType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final SavingsAccountDao savingsAccountDao;
    private final TransactionDao transactionDao;

    public TransactionServiceImpl(SavingsAccountDao savingsAccountDao, TransactionDao transactionDao) {
        this.savingsAccountDao = savingsAccountDao;
        this.transactionDao = transactionDao;
    }

    @Override
    public String makeTransaction(TransactionRequest transactionRequest) {
        SavingsAccount savingsAccount = savingsAccountDao.getAccountByAccountNumber(transactionRequest.accountNumber());
        PaymentMethod paymentMethod = PaymentMethod.valueOf(transactionRequest.paymentMethod().toUpperCase());
        TransactionType transactionType = TransactionType.valueOf(transactionRequest.transactionType().toUpperCase());
        Double amount = transactionRequest.amount();

        SavingsAccount update;

        if (amount <= 0) {
            throw new InsufficientAmountException("Amount should be greater than zero");
        }

        if (savingsAccount != null) {
            update = new SavingsAccount();
            update.setAccountNumber(savingsAccount.getAccountNumber());

            if (transactionType.equals(TransactionType.DEPOSIT)) {
                update.setBalance(savingsAccount.getBalance() + amount);
            } else if (transactionType.equals(TransactionType.WITHDRAW)) {

                if (savingsAccount.getBalance() - amount >= 0) {
                    update.setBalance(savingsAccount.getBalance() - amount);
                } else {
                    throw new InsufficientAmountException("Insufficient Account Balance");

                }
            }

            Transaction transaction = Transaction.builder()
                    .transactionCode(generateTransactionCode())
                    .transactionType(transactionType)
                    .paymentMethod(paymentMethod)
                    .amount(amount)
                    .createdDate(System.currentTimeMillis())
                    .savingsAccount(update)
                    .build();

            transactionDao.performTransaction(transaction);
            return "Transaction Successful";
        } else {
            return "Account not found";
        }

    }

    @Override
    public List<TransactionResponse> getAllTransactions() {
        List<Transaction> transactions = transactionDao.getAllTransactions().stream()
                .peek(transaction -> transaction.setSavingsAccount(savingsAccountDao.getAccountByAccountNumber(transaction.getSavingsAccount().getAccountNumber())))
                .toList();

        return transactions.stream().map(this::mapEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponse> getAllTransactionsByAccountNumber(String accountNumber) {
        List<Transaction> transactions = transactionDao.getAllTransactionsByAccountNumber(accountNumber).stream()
                .peek(transaction -> transaction.setSavingsAccount(savingsAccountDao.getAccountByAccountNumber(transaction.getSavingsAccount().getAccountNumber())))
                .toList();
        return transactions.stream().map(this::mapEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public TransactionResponse getTransactionByTransactionCode(String transactionCode) {
       Transaction transaction = transactionDao.getTransactionByTransactionCode(transactionCode);
       return mapEntityToDTO(transaction);
    }

    private String generateTransactionCode() {
        String transactionCode = "TX" + UUID.randomUUID().toString().substring(0, 6);
        return transactionCode.toUpperCase();
    }

    private TransactionResponse mapEntityToDTO(Transaction transaction) {
        return TransactionResponse.builder()
                .transactionCode(transaction.getTransactionCode())
                .transactionType(transaction.getTransactionType().name())
                .paymentMethod(transaction.getPaymentMethod().name())
                .amount(transaction.getAmount())
                .createdDate(transaction.getCreatedDate())
                .accountNumber(transaction.getSavingsAccount().getAccountNumber())
                .build();
    }
}
