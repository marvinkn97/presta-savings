package dev.marvin.savings.appuser.customer;

import dev.marvin.savings.savingsaccount.SavingsAccountMapper;
import dev.marvin.savings.savingsaccount.SavingsAccountResponse;

import java.util.ArrayList;

public class CustomerMapper {

    public static Customer mapToEntity(CustomerRegistrationRequest registrationRequest) {
        return Customer.builder()
                .name(registrationRequest.fullName())
                .email(registrationRequest.email())
                .kraPin(registrationRequest.kraPin())
                .governmentId(registrationRequest.governmentId())
                .mobileNumber(registrationRequest.mobileNumber())
                .build();
    }


    public static CustomerResponse mapToDTO(Customer customer) {
        var accounts = new ArrayList<SavingsAccountResponse>();

         customer.getSavingsAccounts().forEach(savingsAccount -> {
            accounts.add(SavingsAccountMapper.mapToDTO(savingsAccount));
        });


        return CustomerResponse.builder()
                .memberNumber(customer.getMemberNumber())
                .username(customer.getAppUser().getUsername())
                .name(customer.getName())
                .createdAt(customer.getAppUser().getCreatedAt())
                .email(customer.getEmail())
                .emailConfirmed(customer.isEmailConfirmed())
                .mobileNumber(customer.getMobileNumber())
                .mobileConfirmed(customer.isMobileConfirmed())
                .kraPin(customer.getKraPin())
                .governmentId(customer.getGovernmentId())
                .isDeleted(customer.isDeleted())
                .accounts(accounts)
                .build();
    }
}
