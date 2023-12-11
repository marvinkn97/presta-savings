package dev.marvin.savings.customer;

import dev.marvin.savings.customer.dao.CustomerDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    public Optional<Customer> getCustomerById(Integer customerId) {
        return customerDao.getCustomerById(customerId);
    }


}
