package dev.marvin.savings.appuser.customer;

import dev.marvin.savings.appuser.AppUser;
import dev.marvin.savings.appuser.AppUserRepository;
import dev.marvin.savings.appuser.Role;
import dev.marvin.savings.appuser.confirmationtoken.ConfirmationToken;
import dev.marvin.savings.appuser.confirmationtoken.ConfirmationTokenService;
import dev.marvin.savings.exception.DuplicateResourceException;
import dev.marvin.savings.notifications.email.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    CustomerRepository customerRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    AppUserRepository appUserRepository;

    @Mock
    ConfirmationTokenService confirmationTokenService;

    @Mock
    EmailService emailService;

    @InjectMocks
    CustomerService underTest;



    @Test
    void givenCustomerRegistrationRequest_whenRegisterCustomer_thenReturnEmailConfirmationToken() {
        //given
        var username = "marvin@presta";
        var name = "Marvin Nyingi";
        var email = "marvin@example.com";
        var password = "password";

        final String REGISTRATION_RESPONSE = "A verification email has been sent. Please verify email to activate account";

        var request = new CustomerRegistrationRequest(username, name, email, password);

        given(appUserRepository.existsByUsername(request.username())).willReturn(false);
        given(customerRepository.existsByEmail(request.email())).willReturn(false);

        var appUser = AppUser.builder()
                .username(username)
                .role(Role.CUSTOMER)
                .password(passwordEncoder.encode(password))
                .build();

        var customer = Customer.builder()
                .email(email)
                .name(name)
                .appUser(appUser)
                .build();

        var confirmationToken = ConfirmationToken.builder()
                .token(UUID.randomUUID().toString())
                .issuedAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .customer(customer)
                .build();

        given(appUserRepository.save(any(AppUser.class))).willReturn(appUser);

        given(customerRepository.save(any(Customer.class))).willReturn(customer);

        given(confirmationTokenService.generateToken(customer)).willReturn(confirmationToken.getToken());

        var emailTemplate = "emailTemplate";

        given(emailService.buildEmailTemplate(any(String.class), any(String.class))).willReturn(emailTemplate);

        doNothing().when(emailService).sendEmail(customer.getEmail(), emailTemplate);

        //when
        var actual = underTest.registerCustomer(request);


        //then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerRepository).save(customerArgumentCaptor.capture());

        assertEquals(request.email(), customerArgumentCaptor.getValue().getEmail());
        assertEquals(request.fullName(), customerArgumentCaptor.getValue().getName());
        assertEquals(confirmationToken.getCustomer().getEmail(), customer.getEmail());

        verify(emailService).buildEmailTemplate(any(String.class), any(String.class));
        verify(emailService).sendEmail(any(String.class), any(String.class));

        assertThat(actual).isNotBlank();
        assertThat(actual).satisfies(s -> assertEquals(s, REGISTRATION_RESPONSE));
    }

    @Test
    void givenExistentUsername_whenRegisterCustomer_thenWillThrowException() {

        //given
        var username = "marvin@presta";
        var name = "Marvin Nyingi";
        var email = "marvin@example.com";
        var password = "password";

        var request = new CustomerRegistrationRequest(username, name, email, password);

       given(appUserRepository.existsByUsername(request.username())).willReturn(true);

        assertThatThrownBy(() -> underTest.registerCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("username already taken");
    }

    @Test
    void givenExistentEmail_whenRegisterCustomer_thenWillThrowException() {

        //given
        var username = "marvin@presta";
        var name = "Marvin Nyingi";
        var email = "marvin@example.com";
        var password = "password";

        var request = new CustomerRegistrationRequest(username, name, email, password);

        given(customerRepository.existsByEmail(request.email())).willReturn(true);

        assertThatThrownBy(() -> underTest.registerCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("email already taken");
    }


    @Test
    void existCustomerWithEmail() {
    }

    @Test
    void getAllCustomers() {
    }

    @Test
    void getCustomerByMemberNumber() {
    }

    @Test
    void updateCustomer() {
    }

    @Test
    void deleteCustomer() {
    }
}