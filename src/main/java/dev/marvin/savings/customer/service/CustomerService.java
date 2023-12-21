package dev.marvin.savings.customer.service;

import dev.marvin.savings.customer.dto.CustomerVO;
import dev.marvin.savings.customer.dto.NewCustomerRegistrationRequest;

import java.util.List;

public interface CustomerService {

    Integer insertCustomer(NewCustomerRegistrationRequest registrationRequest);

    List<CustomerVO> getAllCustomers();

    CustomerVO getCustomerByMemberNumber(String memberNumber);
}
