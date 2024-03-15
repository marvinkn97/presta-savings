package dev.marvin.savings.controller;

import dev.marvin.savings.model.dto.TransactionRequest;
import dev.marvin.savings.model.dto.TransactionResponse;
import dev.marvin.savings.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/perform")
    public String makeTransaction(@Valid @RequestBody TransactionRequest transactionRequest){
        return transactionService.makeTransaction(transactionRequest);
    }

    @GetMapping("/all")
    public List<TransactionResponse> getAllTransaction(){
        return transactionService.getAllTransactions();
    }

    @GetMapping("/account/{accountNo}")
    public List<TransactionResponse> getAllTransaction(@PathVariable("accountNo") String accountNumber){
        return transactionService.getAllTransactionsByAccountNumber(accountNumber);
    }

    @GetMapping("/{transactionCode}")
    public TransactionResponse getTransactionByTransactionCode(@PathVariable("transactionCode") String transactionCode){
        return transactionService.getTransactionByTransactionCode(transactionCode);
    }
}
