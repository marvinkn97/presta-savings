package dev.marvin.savings.repository;

import dev.marvin.savings.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query(value = "SELECT t FROM Transaction t WHERE t.savingsAccount.accountNumber = :accNo")
    List<Transaction> findByAccountNumber(@Param(value = "accNo") String accountNumber);

    @Query(value = "SELECT t FROM Transaction t WHERE t.transactionCode = :transactionCode")
    Optional<Transaction> findByTransactionCode(@Param(value = "transactionCode") String transactionCode);
}
