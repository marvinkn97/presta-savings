package dev.marvin.savings.savingsaccount.controller;

import dev.marvin.savings.savingsaccount.dto.NewSavingsAccountRequest;
import dev.marvin.savings.savingsaccount.dto.SavingsAccountResponse;
import dev.marvin.savings.savingsaccount.service.SavingsAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/account")
public class SavingsAccountController {

    private final SavingsAccountService savingsAccountService;

    public SavingsAccountController(SavingsAccountService savingsAccountService) {
        this.savingsAccountService = savingsAccountService;
    }

    @PostMapping("/create")
    public String createAccount(@RequestBody NewSavingsAccountRequest accountRequest){
        return savingsAccountService.createAccount(accountRequest);
    }

    @GetMapping("/all")
    public List<SavingsAccountResponse> getAllAccounts(){
        return savingsAccountService.getAllAccounts();
    }

    @GetMapping("/member/{memberNo}")
    public List<SavingsAccountResponse> getAccountsByMemberNumber(@PathVariable("memberNo") String memberNumber){
       return savingsAccountService.getAccountsByMemberNumber(memberNumber);
    }

    @GetMapping("/type/{accountType}")
    public List<SavingsAccountResponse> getAccountsByAccountType(@PathVariable("accountType") String accountType){
        return savingsAccountService.getAccountsByAccountType(accountType);
    }
}
