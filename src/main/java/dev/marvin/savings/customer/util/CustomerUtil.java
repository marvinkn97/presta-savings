package dev.marvin.savings.customer.util;

import dev.marvin.savings.customer.dto.CustomerResponse;
import dev.marvin.savings.customer.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerUtil {
    public static String generateCustomerMemberNumber() {
        return "presta_mem_" + UUID.randomUUID().toString().substring(0, 6);
    }

    public static CustomerResponse mapEntityToDTO(Customer customer) {
        return CustomerResponse.builder()
                .memberNumber(customer.getMemberNumber())
                .email(customer.getEmail())
                .mobile(customer.getMobile())
                .governmentId(customer.getGovernmentId())
                .build();
    }
}
