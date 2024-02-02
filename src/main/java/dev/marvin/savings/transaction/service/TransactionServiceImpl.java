package dev.marvin.savings.transaction.service;

import dev.marvin.savings.savingsaccount.dao.SavingsAccountDao;
import dev.marvin.savings.savingsaccount.model.SavingsAccount;
import dev.marvin.savings.transaction.dao.TransactionDao;
import dev.marvin.savings.transaction.dto.TransactionRequest;
import dev.marvin.savings.transaction.model.PaymentMethod;
import dev.marvin.savings.transaction.model.Transaction;
import dev.marvin.savings.transaction.model.TransactionType;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

        if (amount < 0) {
            return "Deposit amount should be greater than zero";
        }

        if (savingsAccount != null) {
            update = new SavingsAccount();
            update.setAccountNumber(savingsAccount.getAccountNumber());

            if (transactionType.equals(TransactionType.DEPOSIT)) {
                update.setBalance(savingsAccount.getBalance() + amount);
            } else if (transactionType.equals(TransactionType.WITHDRAW)) {

                if (savingsAccount.getBalance() - amount != 0) {
                    update.setBalance(savingsAccount.getBalance() - amount);
                } else {
                    return "Insufficient amount";
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

    private String generateTransactionCode() {
        String transactionCode = "TX" + UUID.randomUUID().toString().substring(0, 6);
        return transactionCode.toUpperCase();
    }
}
