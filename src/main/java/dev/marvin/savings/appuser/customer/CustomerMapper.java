package dev.marvin.savings.appuser.customer;

import java.util.UUID;

public class CustomerMapper {

    public static Customer mapToEntity(CustomerRegistrationRequest registrationRequest) {
        return Customer.builder()
                .memberNumber(UUID.randomUUID().toString().substring(0, 7).toUpperCase())
                .name(registrationRequest.fullName())
                .email(registrationRequest.email())
                .kraPin(registrationRequest.kraPin())
                .governmentId(registrationRequest.governmentId())
                .mobileNumber(registrationRequest.mobileNumber())
                .isDeleted(false)
                .build();
    }


    public static CustomerResponse mapToDTO(Customer customer) {
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
                .build();
    }
}
