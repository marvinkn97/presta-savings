package dev.marvin.savings.customer;

import dev.marvin.savings.exception.ResourceNotFoundException;
import dev.marvin.savings.notifications.SmsService;
import dev.marvin.savings.util.UniqueIDSupplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final SmsService smsService;

    @Override
    public void saveCustomer(Customer customer) {
        try {
            //generate member number
            UniqueIDSupplier<Customer> customerUniqueIDSupplier = new UniqueIDSupplier<>(Customer.class);
            customer.setMemberNumber(customerUniqueIDSupplier.get());
            customerRepository.save(customer);

            log.info("Customer saved: {}", customer);

        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("Error saving customer");
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


