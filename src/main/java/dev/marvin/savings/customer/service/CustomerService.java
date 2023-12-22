package dev.marvin.savings.customer.service;

import dev.marvin.savings.customer.dto.CustomerRegistrationRequest;
import dev.marvin.savings.customer.dto.CustomerUpdateRequest;
import dev.marvin.savings.customer.dto.CustomerVO;

import java.util.List;

public interface CustomerService {

    Integer insertCustomer(CustomerRegistrationRequest registrationRequest);

    List<CustomerVO> getAllCustomers();

    CustomerVO getCustomerByMemberNumber(String memberNumber);

    boolean updateCustomer(String memberNumber, CustomerUpdateRequest customerUpdateRequest);
}
