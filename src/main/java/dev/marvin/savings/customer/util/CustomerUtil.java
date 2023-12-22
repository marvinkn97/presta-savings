package dev.marvin.savings.customer.util;

import dev.marvin.savings.customer.dto.CustomerVO;
import dev.marvin.savings.customer.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerUtil {

    public static String generateCustomerMemberNumber() {
        return "presta" + UUID.randomUUID().toString().substring(0, 6);
    }

    public static CustomerVO mapEntityToVO(Customer customer) {
        CustomerVO customerVO = new CustomerVO();
        customerVO.setName(customer.getName());
        customerVO.setEmail(customer.getEmail());
        customerVO.setMobile(customer.getMobile().toString());
        customerVO.setGovernmentId(customer.getGovernmentId().toString());
        customerVO.setMemberNumber(customer.getMemberNumber());

        return customerVO;
    }
}
