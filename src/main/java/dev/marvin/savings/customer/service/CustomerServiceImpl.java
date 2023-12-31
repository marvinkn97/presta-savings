package dev.marvin.savings.customer.service;

import dev.marvin.savings.customer.dao.CustomerDao;
import dev.marvin.savings.customer.dto.CustomerRegistrationRequest;
import dev.marvin.savings.customer.dto.CustomerUpdateRequest;
import dev.marvin.savings.customer.dto.CustomerResponse;
import dev.marvin.savings.customer.entity.Customer;
import dev.marvin.savings.customer.entity.Deleted;
import dev.marvin.savings.customer.util.CustomerUtil;
import dev.marvin.savings.exception.DuplicateResourceException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerDao customerDao;

    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public String insertCustomer(CustomerRegistrationRequest registrationRequest) {
        Integer insertResult;
        String response = null;

        //Check if exists customer with given email
        if (customerDao.existsCustomerWithEmail(registrationRequest.email())) {
            throw new DuplicateResourceException("email already taken");
        }

        //Create New Customer
        Customer customer = Customer.builder()
                .memberNumber(CustomerUtil.generateCustomerMemberNumber())
                .name(registrationRequest.name())
                .email(registrationRequest.email())
                .password(registrationRequest.password())
                .mobile(registrationRequest.mobile())
                .governmentId(registrationRequest.governmentId())
                .createdDate(System.currentTimeMillis())
                .isDeleted(Deleted.NO)
                .build();

        insertResult = customerDao.insertCustomer(customer);
        if (insertResult == 1) {
            response = "Customer saved successfully";
        } else {
            response = "Error registering customer";
        }

        return response;
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customerList = customerDao.getAllCustomers();
        List<CustomerResponse> customerDTOList = null;

        if (!customerList.isEmpty()) {
            customerDTOList = new ArrayList<>();
            for (Customer customer : customerList) {
                CustomerResponse customerResponse = CustomerUtil.mapEntityToDTO(customer);
                customerDTOList.add(customerResponse);
            }
        }
        return customerDTOList;
    }

    @Override
    public CustomerResponse getCustomerByMemberNumber(String memberNumber) {
        Customer customer = customerDao.getCustomerByMemberNumber(memberNumber);
        CustomerResponse customerResponse = null;
        if (customer != null) {
            customerResponse = CustomerUtil.mapEntityToDTO(customer);
        }
        return customerResponse;
    }

    @Override
    public boolean updateCustomer(String memberNumber, CustomerUpdateRequest customerUpdateRequest) {
        Customer customer = customerDao.getCustomerByMemberNumber(memberNumber);
        boolean changes = false;

        if (customer != null) {

            if (!customerUpdateRequest.name().isBlank() && !customerUpdateRequest.name().equalsIgnoreCase(customer.getName())) {
                customer.setName(customerUpdateRequest.name());
                changes = true;
            }

            if (!customerUpdateRequest.email().isBlank() && !customerUpdateRequest.email().equalsIgnoreCase(customer.getEmail())) {
                customer.setEmail(customerUpdateRequest.email());
                changes = true;
            }

            if (!customerUpdateRequest.mobile().isBlank() && !customerUpdateRequest.mobile().equals(customer.getMobile().toString())) {
                customer.setMobile(Integer.parseInt(customerUpdateRequest.mobile()));
                changes = true;
            }

            return !changes;
        }
        return false;
    }
}
