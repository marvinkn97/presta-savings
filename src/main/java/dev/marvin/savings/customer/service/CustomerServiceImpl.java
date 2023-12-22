package dev.marvin.savings.customer.service;

import dev.marvin.savings.customer.dao.CustomerDao;
import dev.marvin.savings.customer.dto.CustomerRegistrationRequest;
import dev.marvin.savings.customer.dto.CustomerUpdateRequest;
import dev.marvin.savings.customer.dto.CustomerVO;
import dev.marvin.savings.customer.entity.Customer;
import dev.marvin.savings.customer.util.CustomerUtil;
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
    public Integer insertCustomer(CustomerRegistrationRequest registrationRequest) {
        Integer generatedID = 0;

        if (registrationRequest != null) {
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

        if (!customerList.isEmpty()) {
            customerDTOList = new ArrayList<>();
            for (Customer customer : customerList) {
                CustomerVO customerVO = CustomerUtil.mapEntityToVO(customer);
                customerDTOList.add(customerVO);
            }
        }
        return customerDTOList;
    }

    @Override
    public CustomerVO getCustomerByMemberNumber(String memberNumber) {
        Customer customer = customerDao.getCustomerByMemberNumber(memberNumber);
        CustomerVO customerVO = null;
        if(customer!= null){
            customerVO = CustomerUtil.mapEntityToVO(customer);
        }
        return customerVO;
    }

    @Override
    public boolean updateCustomer(String memberNumber, CustomerUpdateRequest customerUpdateRequest) {
        Customer customer = customerDao.getCustomerByMemberNumber(memberNumber);
        boolean changes = false;

        if(customer != null){

            if(!customerUpdateRequest.name().isBlank() && !customerUpdateRequest.name().equalsIgnoreCase(customer.getName())){
               customer.setName(customerUpdateRequest.name());
               changes = true;
            }

            if(!customerUpdateRequest.email().isBlank() && !customerUpdateRequest.email().equalsIgnoreCase(customer.getEmail())){
                customer.setEmail(customerUpdateRequest.email());
                changes = true;
            }

            if(!customerUpdateRequest.mobile().isBlank() && !customerUpdateRequest.mobile().equals(customer.getMobile().toString())){
                customer.setMobile(Integer.parseInt(customerUpdateRequest.mobile()));
                changes = true;
            }

            return !changes;
        }
        return false;
    }
}
