package dev.marvin.savings.appuser.customer;

import dev.marvin.savings.appuser.AppUser;
import dev.marvin.savings.appuser.AppUserRepository;
import dev.marvin.savings.appuser.Role;
import dev.marvin.savings.appuser.confirmationtoken.ConfirmationTokenService;
import dev.marvin.savings.exception.DuplicateResourceException;
import dev.marvin.savings.exception.NotificationException;
import dev.marvin.savings.exception.ResourceNotFoundException;
import dev.marvin.savings.notifications.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSendException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService implements ICustomerService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final EmailService emailService;
    private final ConfirmationTokenService confirmationTokenService;


    @Override
    @Transactional
    public String registerCustomer(CustomerRegistrationRequest registrationRequest) {

        final String REGISTRATION_RESPONSE = "A verification email has been sent. Please verify email to activate account";

        //check if username exists
        if (appUserRepository.existsByUsername(registrationRequest.username())) {
            throw new DuplicateResourceException("username already taken");
        }

        //check if email exists
        if (customerRepository.existsByEmail(registrationRequest.email())) {
            throw new DuplicateResourceException("email already taken");
        }

        var appUser = AppUser.builder()
                .username(registrationRequest.username())
                .password(passwordEncoder.encode(registrationRequest.password()))
                .role(Role.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .isNotLocked(true)
                .build();

        var savedAppUser = appUserRepository.save(appUser);

        var customer = Customer.builder()
                .memberNumber(UUID.randomUUID().toString().substring(0, 7).toUpperCase())
                .name(registrationRequest.fullName())
                .email(registrationRequest.email())
                .appUser(savedAppUser)
                .build();

        var savedCustomer = customerRepository.save(customer);

        //generate email confirmation token
        String emailConfirmationToken = confirmationTokenService.generateToken(savedCustomer);

        //build email template
        String emailTemplate = emailService.buildEmailTemplate(registrationRequest.fullName(), emailConfirmationToken);

        //send email
        try {
            emailService.sendEmail(savedCustomer.getEmail(), emailTemplate);
        } catch (MailSendException e) {
            log.info(e.getMessage());
            throw new NotificationException("Mail could not be sent");
        }

        return REGISTRATION_RESPONSE;
    }

    public void confirmEmailToken(String token) {
        confirmationTokenService.validateAndConfirmToken(token);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        var customers = customerRepository.findAll();
        var response = new ArrayList<CustomerResponse>();
        customers.forEach(customer -> {
            var customerResponse = mapToDTO(customer);
            response.add(customerResponse);
        });

        return response;
    }

    @Override
    public CustomerResponse getCustomerByMemberNumber(String memberNumber) {
        var customer = getCustomer(memberNumber);
        return mapToDTO(customer);
    }

    @Override
    @Transactional
    public Customer updateCustomer(String memberNumber, CustomerUpdateRequest updateRequest) {
        var customer = getCustomer(memberNumber);
        return null;
    }

    @Override
    @Transactional
    public void deleteCustomer(String memberNumber) {
        var customer = getCustomer(memberNumber);
        var appUser = customer.getAppUser();
        customerRepository.delete(customer);
        appUserRepository.delete(appUser);
    }

    private Customer getCustomer(String memberNumber) {
        return customerRepository.findByMemberNumber(memberNumber)
                .orElseThrow(() -> new ResourceNotFoundException("customer with given details [%s] not found".formatted(memberNumber)));
    }

    private CustomerResponse mapToDTO(Customer customer) {
        return CustomerResponse.builder()
                .memberNumber(customer.getMemberNumber())
                .username(customer.getAppUser().getUsername())
                .createdAt(customer.getAppUser().getCreatedAt())
                .email(customer.getEmail())
                .emailConfirmed(customer.isEmailConfirmed())
                .mobileNumber(customer.getMobileNumber())
                .mobileConfirmed(customer.isMobileConfirmed())
                .kraPin(customer.getKraPin())
                .governmentId(customer.getGovernmentId())
                .build();
    }
}