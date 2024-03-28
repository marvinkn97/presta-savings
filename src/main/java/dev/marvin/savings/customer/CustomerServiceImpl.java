package dev.marvin.savings.customer;

import dev.marvin.savings.appuser.Role;
import dev.marvin.savings.appuser.User;
import dev.marvin.savings.appuser.UserRepository;
import dev.marvin.savings.exception.DuplicateResourceException;
import dev.marvin.savings.exception.GlobalException;
import dev.marvin.savings.model.dto.CustomerResponse;
import dev.marvin.savings.service.SmsService;
import dev.marvin.savings.util.UniqueIDSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final SmsService smsService;

    @Override
    public void createCustomer(CustomerRegistrationRequest registrationRequest) {

        if (userRepository.existsUserWithEmail(registrationRequest.email())) {
            throw new DuplicateResourceException("email already taken");
        }

        UniqueIDSupplier<Customer> customerUniqueIDSupplier = new UniqueIDSupplier<>(Customer.class);

        User user = User.builder()
                .name(registrationRequest.name())
                .email(registrationRequest.email())
                .password(passwordEncoder.encode(registrationRequest.password()))
                .isActive(false)
                .isNotLocked(true)
                .joinDate(System.currentTimeMillis())
                .role(Role.CUSTOMER)
                .build();

        User savedUser = userRepository.save(user);

        Customer customer = Customer.builder()
                .memberNumber(customerUniqueIDSupplier.get())
                .user(savedUser).
                build();

        customerRepository.saveCustomer(customer)
                .orElseThrow(() -> new GlobalException("Failed to create customer"));
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return null;
    }

    @Override
    public CustomerResponse getCustomerByMemberNumber(String memberNumber) {
        return null;
    }

    @Override
    public String updateCustomer(String memberNumber, CustomerUpdateRequest customerUpdateRequest) {
        return null;
    }

    @Override
    public String deleteCustomer(String memberNumber) {
        return null;
    }
}

//    @Override
//    public List<CustomerResponse> getAllCustomers() {
////
////        List<Customer> customerList = customerDao.getAllCustomers();
////        List<CustomerResponse> customerDTOList = null;
////
////        if (!customerList.isEmpty()) {
////            customerDTOList = new ArrayList<>();
////            for (Customer customer : customerList) {
////                CustomerResponse customerResponse = mapEntityToDTO(customer);
////                customerDTOList.add(customerResponse);
////            }
////        }
////        return customerDTOList;
//        return null;
//    }
//
//    @Override
//    public CustomerResponse getCustomerByMemberNumber(String memberNumber) {
////        Customer customer = customerDao.getCustomerByMemberNumber(memberNumber).orElseThrow(() -> new ResourceNotFoundException("customer does not exist"));
////        return mapEntityToDTO(customer);
//        return null;
//    }
//
//
//    @Override
//    public String updateCustomer(String memberNumber, CustomerUpdateRequest customerUpdateRequest) {
////
////        System.out.println(customerUpdateRequest.mobile());
////        Optional<Customer> customer = customerDao.getCustomerByMemberNumber(memberNumber);
////        boolean changes = false;
////
////        String emailUpdate = customerUpdateRequest.email();
////        String mobileUpdate = customerUpdateRequest.mobile();
////        String profileImageUpdate = customerUpdateRequest.profileImage();
////
////        Customer update = null;
////
////        if (customer.isPresent()) {
////            Customer c = customer.get();
////            update = new Customer();
////            update.setMemberNumber(memberNumber);
////
////            if (!customerUpdateRequest.name().isBlank() && !customerUpdateRequest.name().equalsIgnoreCase(c.getName())) {
////                update.setName(customerUpdateRequest.name());
////                changes = true;
////            }
////
//////            if (!emailUpdate.isBlank() && !emailUpdate.equalsIgnoreCase(c.getEmail())) {
//////                if (customerDao.existsCustomerWithEmail(emailUpdate)) {
//////                    throw new DuplicateResourceException("email already exists");
//////                } else {
//////                    update.setEmail(emailUpdate);
//////                    changes = true;
//////                }
//////            }
////
////            //TODO: include password, profileImageId with AWS s3
////            if (!mobileUpdate.isBlank() && !mobileUpdate.equals(c.getMobile())) {
////
////                int OTPLength = 5;
////                String OTP = generateOTP(OTPLength);
////
//////                smsService.sendSMS("TIARACONECT", mobileUpdate, "Verification code is " + OTP);
////
////                //TODO: Figure out how to implement AOP here!!!!
////
////                update.setMobile(mobileUpdate);
////                changes = true;
////            }
////
////            if (!profileImageUpdate.isBlank() && !profileImageUpdate.equals(c.getProfileImageId())) {
////                update.setProfileImageId(profileImageUpdate);
////                changes = true;
////            }
////        }
////
////        if (!changes) {
////            return "no data changes found";
////        }
////
////        Boolean result = customerDao.updateCustomer(update);
////        if (Boolean.TRUE.equals(result)) {
////            return "customer updated successfully";
////        } else {
////            throw new GlobalException("Failed to update customer");
////        }
//        return null;
//
//    }
//
//    @Override
//    public String deleteCustomer(String memberNumber) {
////        Customer existingCustomer = customerDao.getCustomerByMemberNumber(memberNumber)
////                .orElseThrow(() -> new ResourceNotFoundException(
////                        "customer with given member number [%s] not found".formatted(memberNumber)
////                ));
////
////        Boolean isDeleted = customerDao.deleteCustomer(existingCustomer);
////        if (isDeleted) {
////            return "Customer with given member number [%s] deleted successfully".formatted(memberNumber);
////        } else {
////            throw new RuntimeException("Failed to delete customer");
////        }
//        return null;
//
//    }
//
////    private CustomerResponse mapEntityToDTO(Customer customer) {
////        return CustomerResponse.builder()
////                .memberNumber(customer.getMemberNumber())
////                .name(customer.getName())
////                .mobile(customer.getMobile())
////                .governmentId(customer.getGovernmentId())
////                .build();
////    }
////
////    private String generateOTP(int length) {
////        SecureRandom secureRandom = new SecureRandom();
////        String charset = "0123456789"; // Define the character set
////        StringBuilder otp = new StringBuilder();
////        for (int i = 0; i < length; i++) {
////            int randomIndex = secureRandom.nextInt(charset.length());
////            otp.append(charset.charAt(randomIndex));
////        }
////        return otp.toString();
////    }
//
//
////    private void uploadProfileImage(MultipartFile multipartFile) {
////        //start off with savings file to drive and savings file link to db then integrate AWS S3 bucket
////    }
////
////    private void downloadProfileImage() {
////    }
//}
//
