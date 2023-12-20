package dev.marvin.savings.customer.service;

import dev.marvin.savings.customer.dao.CustomerDao;
import dev.marvin.savings.customer.domain.Customer;
import dev.marvin.savings.customer.dto.CustomerVO;
import dev.marvin.savings.customer.dto.NewCustomerRegistrationRequest;
import dev.marvin.savings.customer.util.CustomerUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public Integer insertCustomer(NewCustomerRegistrationRequest registrationRequest) {
        Integer generatedID = 0;

        if(registrationRequest != null){
            Customer customer = new Customer();
            customer.setName(registrationRequest.name());
            customer.setEmail(registrationRequest.email());
            customer.setMobile(Integer.parseInt(registrationRequest.mobile()));
            customer.setGovernmentId(Integer.parseInt(registrationRequest.governmentId()));
            customer.setMemberNumber(CustomerUtil.generateCustomerMemberNumber());

            generatedID = customerDao.insertCustomer(customer);
        }
        return generatedID;
    }

    @Override
    public List<CustomerVO> getAllCustomers() {
        List<Customer> customerList = customerDao.getAllCustomers();
        List<CustomerVO> customerDTOList = null;

        for (Customer customer: customerList) {

        }

        return null;
    }

    @Override
    public CustomerVO getCustomerById(Integer customerId) {
        return null;
    }
}
