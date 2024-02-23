package dev.marvin.savings.customer.service;

import dev.marvin.savings.customer.dao.CustomerDao;
import dev.marvin.savings.customer.dto.CustomerRegistrationRequest;
import dev.marvin.savings.customer.dto.CustomerResponse;
import dev.marvin.savings.customer.dto.CustomerUpdateRequest;
import dev.marvin.savings.customer.model.Customer;
import dev.marvin.savings.exception.DatabaseOperationException;
import dev.marvin.savings.exception.DuplicateResourceException;
import dev.marvin.savings.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }


    @Override
    public String registerCustomer(CustomerRegistrationRequest registrationRequest) {

        // checks if a customer with the given email already exists in the system
        if (customerDao.existsCustomerWithEmail(registrationRequest.email())) {
            throw new DuplicateResourceException("email already taken");
        }

        String memberNumber = generateCustomerMemberNumber();

        //Register New Customer
        Customer customer = Customer.builder()
                .memberNumber(memberNumber)
                .name(registrationRequest.name())
                .email(registrationRequest.email())
                .password(registrationRequest.password())
                .createdDate(System.currentTimeMillis())
                .build();

        customerDao.insertCustomer(customer);

        return "Customer registered successfully";

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
    public Optional<CustomerResponse> getCustomerByMemberNumber(String memberNumber) {
        Optional<Customer> customer = customerDao.getCustomerByMemberNumber(memberNumber);
        CustomerResponse customerResponse;
        if (customer.isPresent()) {
            Customer c = customer.get();
            customerResponse = mapEntityToDTO(c);
            return Optional.ofNullable(customerResponse);
        } else {
            return Optional.empty();
        }
    }

    //TODO: update customer update functionality
    @Override
    public String updateCustomer(String memberNumber, CustomerUpdateRequest customerUpdateRequest) {
        Optional<Customer> customer = customerDao.getCustomerByMemberNumber(memberNumber);
        boolean changes = false;

        String emailUpdate = customerUpdateRequest.email();
        String nameUpdate = customerUpdateRequest.name();
        String mobileUpdate = customerUpdateRequest.mobile();
        String profileImageUpdate = customerUpdateRequest.profileImage();

        Customer update = null;

        if (customer.isPresent()) {
            Customer c = customer.get();
            update = new Customer();

            if (!nameUpdate.isBlank() && !nameUpdate.equalsIgnoreCase(c.getName())) {
                update.setName(nameUpdate);
                changes = true;
            }

            if (!emailUpdate.isBlank() && !emailUpdate.equalsIgnoreCase(c.getEmail())) {
                if (customerDao.existsCustomerWithEmail(emailUpdate)) {
                    throw new DuplicateResourceException("email already exists");
                } else {
                    update.setEmail(emailUpdate);
                    changes = true;
                }
            }

            //TODO: include password, profileImageId with AWS s3
            if (!mobileUpdate.isBlank() && !mobileUpdate.equals(c.getMobile())) {
                update.setMobile(mobileUpdate);
                changes = true;
            }

            if (!profileImageUpdate.isBlank() && !profileImageUpdate.equals(c.getProfileImageId())) {
                update.setProfileImageId(profileImageUpdate);
                changes = true;
            }
        }

        if (!changes) {
            return "no data changes found";
        }

        update.setUpdatedDate(System.currentTimeMillis());
        Boolean result = customerDao.updateCustomer(update);
        if (result) {
            return "customer [%s] updated successfully".formatted(memberNumber);
        } else {
            throw new DatabaseOperationException("Failed to update customer");
        }

    }

    @Override
    public String deleteCustomer(String memberNumber) {
        Customer existingCustomer = customerDao.getCustomerByMemberNumber(memberNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with given member number [%s] not found".formatted(memberNumber)
                ));

        Boolean isDeleted = customerDao.deleteCustomer(existingCustomer);
        if (isDeleted) {
            return "Customer with given member number [%s] deleted successfully".formatted(memberNumber);
        } else {
            throw new DatabaseOperationException("Failed to delete customer");
        }

    }

    private CustomerResponse mapEntityToDTO(Customer customer) {
        return CustomerResponse.builder()
                .memberNumber(customer.getMemberNumber())
                .name(customer.getName())
                .email(customer.getEmail())
                .mobile(customer.getMobile())
                .governmentId(customer.getGovernmentId())
                .createdDate(customer.getCreatedDate())
                .build();
    }

    private String generateCustomerMemberNumber() {
        String memberNumber = "MEM" + UUID.randomUUID().toString().substring(0, 6);
        return memberNumber.toUpperCase();
    }

    private void uploadProfileImage(MultipartFile multipartFile) {
        //start off with savings file to drive and savings file link to db then integrate AWS S3 bucket
    }

    private void downloadProfileImage() {
    }

}
