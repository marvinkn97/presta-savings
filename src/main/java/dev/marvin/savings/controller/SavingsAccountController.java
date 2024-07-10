package dev.marvin.savings.controller;

import dev.marvin.savings.appuser.AppUser;
import dev.marvin.savings.config.AppResponse;
import dev.marvin.savings.dto.SavingsAccountRequest;
import dev.marvin.savings.service.SavingsAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("api/v1/accounts")
@RequiredArgsConstructor
@Tag(name = "Savings Account Resource", description = "SavingsAccount Management")
public class SavingsAccountController {

    private final SavingsAccountService savingsAccountService;

    @PreAuthorize(value = "hasAuthority('CUSTOMER')")
    @PostMapping
    @Operation(method = "POST", description = "Create Savings Account")
    public ResponseEntity<?> createAccount(@RequestBody SavingsAccountRequest accountRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = (AppUser) authentication.getPrincipal();
        Map<String, Object> response = savingsAccountService.createAccount(accountRequest, appUser.getCustomer());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('CSR')")
    public ResponseEntity<AppResponse> getAllAccounts() {

        return null;
    }

    @GetMapping("/member/{memberNo}")
    @PreAuthorize(value = "hasAnyAuthority('CUSTOMER', 'CSR')")
    public ResponseEntity<AppResponse> getAllAccountsByMemberNumber(@PathVariable("memberNo") String memberNumber) {
        return null;
    }

    @DeleteMapping("/{accountNo}")
    public String deleteAccount(@PathVariable("accountNo") String accountNumber) {
        return null;
    }

    @GetMapping("/member/{memberNo}/balance")
    public Double getAllCustomerAccountsTotalBalance(@PathVariable("memberNo") String memberNumber) {
        return null;
    }

    @GetMapping("/all/total")
    public Double getAllCustomerAccountsTotalBalance() {
        return null;
    }
}
