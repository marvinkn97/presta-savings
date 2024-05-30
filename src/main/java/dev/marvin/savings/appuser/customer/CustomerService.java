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
                .isNotLocked(true)
                .build();

        var savedAppUser = appUserRepository.save(appUser);

        var customer = CustomerMapper.mapToEntity(registrationRequest);
        customer.setAppUser(savedAppUser);

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
            throw new NotificationException("Mail server connection failed");
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

        if(StringUtils.isNotEmpty(updateRequest.name()) && !updateRequest.name().equals(customer.getName())){
             customer.setName(updateRequest.name());
             changes = true;
        }

        if(StringUtils.isNotEmpty(updateRequest.email()) && updateRequest.email().equals(customer.getEmail())){
            if(customerRepository.existsByEmail(updateRequest.email())){
                throw new DuplicateResourceException("email already taken");
            }
            customer.setEmail(updateRequest.email());
            changes = true;
        }


        if(!changes){
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
}