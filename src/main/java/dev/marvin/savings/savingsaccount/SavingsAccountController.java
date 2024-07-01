package dev.marvin.savings.savingsaccount;

import dev.marvin.savings.appuser.AppUser;
import dev.marvin.savings.config.AppResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/v1/accounts")
@RequiredArgsConstructor
@Tag(name = "Savings Account Resource", description = "SavingsAccount Management")
public class SavingsAccountController {
    private final SavingsAccountService savingsAccountService;

    @PreAuthorize(value = "hasAuthority('CUSTOMER')")
    @PostMapping
    @Operation(method = "POST", description = "Create Savings Account")
    public ResponseEntity<AppResponse> createAccount(@RequestBody SavingsAccountRequest accountRequest) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var appUser = (AppUser) authentication.getPrincipal();

        AppResponse appResponse = AppResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.CREATED.value())
                .reason(HttpStatus.CREATED.getReasonPhrase())
                .data("Savings Account Created Successfully")
                .build();

         savingsAccountService.createAccount(accountRequest, appUser.getCustomer());

        return ResponseEntity.status(HttpStatus.CREATED).body(appResponse);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('CSR')")
    public ResponseEntity<AppResponse> getAllAccounts() {
        var accounts = savingsAccountService.getAllAccounts();

        AppResponse appResponse = AppResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.OK.value())
                .reason(HttpStatus.OK.getReasonPhrase())
                .data(accounts)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(appResponse);
    }

    @GetMapping("/member/{memberNo}")
    @PreAuthorize(value = "hasAnyAuthority('CUSTOMER', 'CSR')")
    public ResponseEntity<AppResponse> getAllAccountsByMemberNumber(@PathVariable("memberNo") String memberNumber){
        var accounts = savingsAccountService.getAccountsByMemberNumber(memberNumber);

        AppResponse appResponse = AppResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.OK.value())
                .reason(HttpStatus.OK.getReasonPhrase())
                .data(accounts)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(appResponse);
    }

    @DeleteMapping("/{accountNo}")
    public String deleteAccount(@PathVariable("accountNo") String accountNumber) {
        savingsAccountService.deleteAccount(accountNumber);
        return null;
    }

    @GetMapping("/member/{memberNo}/balance")
    public Double getAllCustomerAccountsTotalBalance(@PathVariable("memberNo") String memberNumber) {
        return savingsAccountService.getAllCustomerAccountsTotalBalance(memberNumber);
    }

    @GetMapping("/all/total")
    public Double getAllCustomerAccountsTotalBalance() {
        return savingsAccountService.getAllCustomersAccountTotalBalance();
    }
}
