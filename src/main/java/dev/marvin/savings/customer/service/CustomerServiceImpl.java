package dev.marvin.savings.customer.service;

import dev.marvin.savings.customer.dao.CustomerDao;
import dev.marvin.savings.customer.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    public Customer getCustomerById(Integer customerId) {
        return customerDao.getCustomerById(customerId).orElse(null);
    }


}
