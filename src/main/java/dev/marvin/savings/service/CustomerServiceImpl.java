package dev.marvin.savings.service;

import dev.marvin.savings.dao.CustomerDao;
import dev.marvin.savings.model.dto.CustomerRegistrationRequest;
import dev.marvin.savings.model.dto.CustomerResponse;
import dev.marvin.savings.model.dto.CustomerUpdateRequest;
import dev.marvin.savings.exception.DuplicateResourceException;
import dev.marvin.savings.exception.ResourceNotFoundException;
import dev.marvin.savings.model.Customer;
import dev.marvin.savings.service.CustomerService;
import dev.marvin.savings.service.SmsService;
import dev.marvin.savings.util.UniqueIDSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;
    private final SmsService smsService;

    /*
     collecting only the necessary information from users while ensuring a seamless
      and secure registration experience.
    */
    @Override
    public String registerCustomer(CustomerRegistrationRequest registrationRequest) {

        // checks if a customer with the given email already exists in the system
        if (customerDao.existsCustomerWithEmail(registrationRequest.email())) {
            throw new DuplicateResourceException("email already taken");
        }

        UniqueIDSupplier<Customer> customerUniqueIDSupplier = new UniqueIDSupplier<>(Customer.class);

        //Register New Customer
        Customer customer = new Customer();
        customer.setMemberNumber(customerUniqueIDSupplier.get());
        customer.setName(registrationRequest.name());
        customer.setEmail(registrationRequest.email());
        customer.setPassword(registrationRequest.password());
        customer.setCreatedDate(System.currentTimeMillis());

        System.out.println(customer.getMemberNumber());

        customerDao.insertCustomer(customer);

//        CustomerRegistrationResponse response = new CustomerRegistrationResponse("Customer registered successfully", LocalDateTime.now());

        return "Customer registered successfully";

    }

    @Override
    public List<CustomerResponse> getAllCustomers(int pageNumber, int pageSize) {

        List<Customer> customerList = customerDao.getAllCustomers(pageNumber, pageSize);
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
        Customer customer = customerDao.getCustomerByMemberNumber(memberNumber).orElseThrow(() -> new ResourceNotFoundException("customer does not exist"));
        return mapEntityToDTO(customer);
    }

    //TODO: update customer update functionality
    @Override
    public String updateCustomer(String memberNumber, CustomerUpdateRequest customerUpdateRequest) {

        System.out.println(customerUpdateRequest.mobile());
        Optional<Customer> customer = customerDao.getCustomerByMemberNumber(memberNumber);
        boolean changes = false;

        String emailUpdate = customerUpdateRequest.email();
        String mobileUpdate = customerUpdateRequest.mobile();
        String profileImageUpdate = customerUpdateRequest.profileImage();

        Customer update = null;

        if (customer.isPresent()) {
            Customer c = customer.get();
            update = new Customer();
            update.setMemberNumber(memberNumber);

            if (!customerUpdateRequest.name().isBlank() && !customerUpdateRequest.name().equalsIgnoreCase(c.getName())) {
                update.setName(customerUpdateRequest.name());
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

                int OTPLength = 5;
                String OTP = generateOTP(OTPLength);

//                smsService.sendSMS("TIARACONECT", mobileUpdate, "Verification code is " + OTP);

                //TODO: Figure out how to implement AOP here!!!!

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
            return "customer updated successfully";
        } else {
            throw new RuntimeException("Failed to update customer");
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
            throw new RuntimeException("Failed to delete customer");
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

    private String generateOTP(int length) {
        SecureRandom secureRandom = new SecureRandom();
        String charset = "0123456789"; // Define the character set
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(charset.length());
            otp.append(charset.charAt(randomIndex));
        }
        return otp.toString();
    }


    private void uploadProfileImage(MultipartFile multipartFile) {
        //start off with savings file to drive and savings file link to db then integrate AWS S3 bucket
    }

    private void downloadProfileImage() {
    }

}
