package dev.marvin.savings.customer.util;

import dev.marvin.savings.customer.dto.CustomerResponse;
import dev.marvin.savings.customer.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerUtil {

    private CustomerUtil(){}

    public static String generateCustomerMemberNumber() {
        return "presta" + UUID.randomUUID().toString().substring(0, 6);
    }

    public static CustomerResponse mapEntityToDTO(Customer customer) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setName(customer.getName());
        customerResponse.setEmail(customer.getEmail());
        customerResponse.setMobile(customer.getMobile().toString());
        customerResponse.setGovernmentId(customer.getGovernmentId().toString());
        customerResponse.setMemberNumber(customer.getMemberNumber());

        return customerResponse;
    }
}
