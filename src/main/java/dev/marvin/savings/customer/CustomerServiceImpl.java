package dev.marvin.savings.customer;

import dev.marvin.savings.auth.Role;
import dev.marvin.savings.auth.User;
import dev.marvin.savings.auth.UserDao;
import dev.marvin.savings.exception.DuplicateResourceException;
import dev.marvin.savings.exception.GlobalException;
import dev.marvin.savings.exception.ResourceNotFoundException;
import dev.marvin.savings.model.dto.CustomerResponse;
import dev.marvin.savings.service.SmsService;
import dev.marvin.savings.util.UniqueIDSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;
    private final UserDao userDao;
    private final SmsService smsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String registerCustomer(CustomerRegistrationRequest registrationRequest) {

        if (Boolean.TRUE.equals(customerDao.existsCustomerWithEmail(registrationRequest.email()))) {
            throw new DuplicateResourceException("email already taken");
        }

        UniqueIDSupplier<Customer> customerUniqueIDSupplier = new UniqueIDSupplier<>(Customer.class);

        User user = new User();
        user.setName(registrationRequest.name());
        user.setEmail(registrationRequest.email());
        user.setPassword(passwordEncoder.encode(registrationRequest.password()));
        user.setRole(Role.CUSTOMER);
        user.setCreatedDate(System.currentTimeMillis());

        Boolean userInserted = userDao.insertUser(user);

        if(Boolean.TRUE.equals(userInserted)){
            Customer customer = new Customer(user);
            customer.setMemberNumber(customerUniqueIDSupplier.get());
            Boolean customerInserted = customerDao.insertCustomer(customer);

            if(Boolean.TRUE.equals(customerInserted)){
                return "Customer registered successfully";
            }else {
                throw new GlobalException("Failed to create user");
            }
        }else{
            throw new GlobalException("Failed to create customer");
        }
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
        Customer customer = customerDao.getCustomerByMemberNumber(memberNumber).orElseThrow(() -> new ResourceNotFoundException("customer does not exist"));
        return mapEntityToDTO(customer);
    }


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

        Boolean result = customerDao.updateCustomer(update);
        if (Boolean.TRUE.equals(result)) {
            return "customer updated successfully";
        } else {
            throw new GlobalException("Failed to update customer");
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
