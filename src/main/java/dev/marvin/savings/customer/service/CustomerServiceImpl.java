package dev.marvin.savings.customer.service;

import dev.marvin.savings.customer.dao.CustomerDao;
import dev.marvin.savings.customer.dto.CustomerRegistrationRequest;
import dev.marvin.savings.customer.dto.CustomerResponse;
import dev.marvin.savings.customer.dto.CustomerUpdateRequest;
import dev.marvin.savings.customer.model.Customer;
import dev.marvin.savings.exception.DuplicateResourceException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerDao customerDao;

    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public String insertCustomer(CustomerRegistrationRequest registrationRequest) {

        //TODO: Use only email and password alternate route to user registration and populate rest of fields through profile update

        //Check if exists customer with given email
        if (customerDao.existsCustomerWithEmail(registrationRequest.email())) {
            throw new DuplicateResourceException("email already taken");
        }

        //Create New Customer
        Customer customer = Customer.builder()
                .memberNumber(generateCustomerMemberNumber())
                .name(registrationRequest.name())
                .email(registrationRequest.email())
                .password(registrationRequest.password())
                .mobile(registrationRequest.mobile())
                .governmentId(registrationRequest.governmentId())
                .createdDate(System.currentTimeMillis())
                .build();

        customerDao.insertCustomer(customer);

        return "customer saved successfully";
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customerList = customerDao.getAllCustomers();
        List<CustomerResponse> customerDTOList = null;

        if (!customerList.isEmpty()) {
            customerDTOList = new ArrayList<>();
            for (Customer customer : customerList) {
                CustomerResponse customerResponse = mapEntityToDTO(customer);
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
            customerResponse = mapEntityToDTO(customer);
        }
        return customerResponse;
    }

    //TODO: update customer update functionality
    @Override
    public String updateCustomer(String memberNumber, CustomerUpdateRequest customerUpdateRequest) {
        Customer customer = customerDao.getCustomerByMemberNumber(memberNumber);
        boolean changes = false;

        Customer update;

        if (customer != null) {
            update = new Customer();

            if (!customerUpdateRequest.name().isBlank() && !customerUpdateRequest.name().equalsIgnoreCase(customer.getName())) {
                customer.setName(customerUpdateRequest.name());
                changes = true;
            }

            if (!customerUpdateRequest.email().isBlank() && !customerUpdateRequest.email().equalsIgnoreCase(customer.getEmail())) {
                customer.setEmail(customerUpdateRequest.email());
                changes = true;
            }

            //TODO: include password

            if (!customerUpdateRequest.mobile().isBlank() && !customerUpdateRequest.mobile().equals(customer.getMobile())) {
                customer.setMobile(customerUpdateRequest.mobile());
                changes = true;
            }
        }

        if (changes) {
            customerDao.updateCustomer(customer);
            return "customer [%s] updated successfully".formatted(customer.getMemberNumber());
        } else {
            return "no data changes found";
        }

    }

    @Override
    public String deleteCustomer(String memberNumber) {
        Customer existingCustomer = customerDao.getCustomerByMemberNumber(memberNumber);

        if (existingCustomer != null) {
            customerDao.deleteCustomer(existingCustomer);
            return "customer [%s] deleted successfully".formatted(memberNumber);
        } else {
            return "customer not found";
        }
    }


    private CustomerResponse mapEntityToDTO(Customer customer) {
        return CustomerResponse.builder()
                .memberNumber(customer.getMemberNumber())
                .name(customer.getName())
                .email(customer.getEmail())
                .mobile(customer.getMobile())
                .governmentId(customer.getGovernmentId())
                .build();
    }

    private String generateCustomerMemberNumber() {
        String memberNumber = "MEM" + UUID.randomUUID().toString().substring(0, 6);
        return memberNumber.toUpperCase();
    }
}
