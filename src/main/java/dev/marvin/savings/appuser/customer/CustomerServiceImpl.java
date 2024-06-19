package dev.marvin.savings.appuser.customer;

import dev.marvin.savings.appuser.AppUser;
import dev.marvin.savings.appuser.AppUserRepository;
import dev.marvin.savings.appuser.Role;
import dev.marvin.savings.appuser.confirmationtoken.ConfirmationTokenService;
import dev.marvin.savings.exception.DuplicateResourceException;
import dev.marvin.savings.exception.NotificationException;
import dev.marvin.savings.exception.RequestValidationException;
import dev.marvin.savings.exception.ResourceNotFoundException;
import dev.marvin.savings.notifications.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.MailSendException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

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
                .isNotLocked(true)
                .build();

        var savedAppUser = appUserRepository.save(appUser);

        var customer = CustomerMapper.mapToEntity(registrationRequest);
        customer.setMemberNumber(generateMemberNumber());
        customer.setDeleted(false);
        customer.setAppUser(savedAppUser);

        var savedCustomer = customerRepository.save(customer);

        generateConfirmationTokenAndSendToEmail(savedCustomer);

        return REGISTRATION_RESPONSE;
    }

    private String generateMemberNumber(){
        return UUID.randomUUID().toString();
    }

    public void confirmEmailToken(String token) {
        confirmationTokenService.validateAndConfirmToken(token);
    }

    @Override
    public void refreshConfirmationToken(String email) {
       var customer = customerRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("customer does not exist"));
       generateConfirmationTokenAndSendToEmail(customer);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        var customers = customerRepository.findAll();
        var response = new ArrayList<CustomerResponse>();
        customers.forEach(customer -> response.add(CustomerMapper.mapToDTO(customer)));

        return response;
    }

    @Override
    public CustomerResponse getCustomerByMemberNumber(String memberNumber) {
        var customer = getCustomer(memberNumber);
        return CustomerMapper.mapToDTO(customer);
    }

    @Override
    @Transactional
    public void updateCustomer(String memberNumber, CustomerUpdateRequest updateRequest) {
        var customer = getCustomer(memberNumber);

        var changes = false;

        if (StringUtils.isNotBlank(updateRequest.fullName()) && !updateRequest.fullName().equals(customer.getName())) {
            customer.setName(updateRequest.fullName());
            changes = true;
        }

        if (StringUtils.isNotBlank(updateRequest.email()) && !updateRequest.email().equals(customer.getEmail())) {
            if (customerRepository.existsByEmail(updateRequest.email())) {
                throw new DuplicateResourceException("email already taken");
            }
            customer.setEmail(updateRequest.email());
            changes = true;
        }

        if (StringUtils.isNotBlank(updateRequest.password())) {
            if (!passwordEncoder.matches(updateRequest.password(), customer.getAppUser().getPassword())) {
                String encodedPassword = passwordEncoder.encode(updateRequest.password());
                customer.getAppUser().setPassword(encodedPassword);
                changes = true;
            }
        }

        if (StringUtils.isNotBlank(updateRequest.mobileNumber()) && !updateRequest.mobileNumber().equals(customer.getMobileNumber())) {
            customer.setMobileNumber(updateRequest.mobileNumber());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("no data changes found");
        }

        customerRepository.save(customer);
    }

    @Override
    @Transactional
    public void deleteCustomer(String memberNumber) {
        var customer = getCustomer(memberNumber);
        var appUser = customer.getAppUser();

        if (!ObjectUtils.isEmpty(appUser)) {
            appUser.setEnabled(false);
            appUser.setNotLocked(false);
            var update = appUserRepository.save(appUser);
            customer.setAppUser(update);
            customer.setDeleted(true);
            customerRepository.save(customer);
        }

    }

    private Customer getCustomer(String memberNumber) {
        return customerRepository.findByMemberNumber(memberNumber)
                .orElseThrow(() -> new ResourceNotFoundException("customer does not exist"));
    }

    private void generateConfirmationTokenAndSendToEmail(Customer customer){
        //generate email confirmation token
        String emailConfirmationToken = confirmationTokenService.generateToken(customer);

        //build email template
        String emailTemplate = emailService.buildEmailTemplate(customer.getName(), emailConfirmationToken);

        //send email
        try {
            emailService.sendEmail(customer.getEmail(), emailTemplate);
        } catch (MailSendException e) {
            log.info(e.getMessage());
            throw new NotificationException("Mail server connection failed");
        }
    }
}