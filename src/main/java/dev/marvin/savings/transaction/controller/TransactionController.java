package dev.marvin.savings.transaction.controller;

import dev.marvin.savings.transaction.dto.TransactionRequest;
import dev.marvin.savings.transaction.service.TransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/perform")
    public String makeTransaction(@RequestBody TransactionRequest transactionRequest){
        return transactionService.makeTransaction(transactionRequest);
    }
}
