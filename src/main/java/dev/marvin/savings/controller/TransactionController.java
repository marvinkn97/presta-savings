package dev.marvin.savings.controller;

import dev.marvin.savings.dto.TransactionRequest;
import dev.marvin.savings.entity.Transaction;
import dev.marvin.savings.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> makeTransaction(@Valid @RequestBody TransactionRequest transactionRequest){
        Transaction transaction = transactionService.performTransaction(transactionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }

    @GetMapping
    public List<Transaction> getAllTransaction(){
        return transactionService.getAllTransactions();
    }

    @GetMapping("/account/{accountNo}")
    public List<Transaction> getAllTransaction(@PathVariable("accountNo") String accountNumber){
        return transactionService.getAllTransactionsByAccountNumber(accountNumber);
    }

    @GetMapping("/{transactionCode}")
    public Transaction getTransactionByTransactionCode(@PathVariable("transactionCode") String transactionCode){
        return transactionService.getTransactionByTransactionCode(transactionCode);
    }
}
