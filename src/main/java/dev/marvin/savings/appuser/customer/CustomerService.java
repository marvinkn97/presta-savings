package dev.marvin.savings.appuser.customer;

import dev.marvin.savings.appuser.AppUser;
import dev.marvin.savings.appuser.AppUserRepository;
import dev.marvin.savings.appuser.Role;
import dev.marvin.savings.auth.RegistrationRequest;
import dev.marvin.savings.exception.DuplicateResourceException;
import dev.marvin.savings.exception.RequestValidationException;
import dev.marvin.savings.exception.ResourceNotFoundException;
import dev.marvin.savings.notifications.sms.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService implements ICustomerService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final SmsService smsService;


    @Override
    @Transactional
    public Customer registerCustomer(RegistrationRequest registrationRequest) {

        //check if username exists
        if (appUserRepository.existsByUsername(registrationRequest.username())) {
            throw new DuplicateResourceException("username already taken");
        }

        //check if email exists
        if (customerRepository.existsByEmail(registrationRequest.email())) {
            throw new DuplicateResourceException("email already taken");
        }

        try {
            var appUser = AppUser.builder()
                    .username(registrationRequest.username())
                    .password(passwordEncoder.encode(registrationRequest.password()))
                    .role(Role.CUSTOMER)
                    .isNotLocked(true)
                    .build();

            var savedAppUser = appUserRepository.save(appUser);

            var customer = Customer.builder()
                    .memberNumber(generateMemberNumber())
                    .name(registrationRequest.fullName())
                    .email(registrationRequest.email())
                    .appUser(savedAppUser)
                    .build();


            var savedCustomer = customerRepository.save(customer);
            log.info("Customer saved: {}", savedCustomer);
            return savedCustomer;

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RequestValidationException("Error saving customer");
        }

    }

    @Override
    public boolean existCustomerWithEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerByMemberNumber(String memberNumber) {
        return null;
    }

    @Override
    @Transactional
    public Customer updateCustomer(String memberNumber, CustomerUpdateRequest updateRequest) {
        String name = updateRequest.name();

        Customer customer = customerRepository.findByMemberNumber(memberNumber)
                .orElseThrow(() -> new ResourceNotFoundException("customer with given member number [%s] not found".formatted(memberNumber)));

        return null;
    }

    @Override
    @Transactional
    public void deleteCustomer(String memberNumber) {
    }

    private String generateMemberNumber() {
        return "MEM" + UUID.randomUUID().toString().substring(0, 7);
    }
}

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


