package dev.marvin.savings.appuser.customer;

import dev.marvin.savings.appuser.AppUser;
import dev.marvin.savings.appuser.AppUserRepository;
import dev.marvin.savings.appuser.Role;
import dev.marvin.savings.auth.confirmationtoken.ConfirmationTokenService;
import dev.marvin.savings.notifications.email.EmailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    CustomerService customerService;

    @Test
    void givenCustomerRegistrationRequest_whenRegisterCustomer_thenReturnEmailConfirmationToken() {
        //given
        String username = "marvin@presta";
        String name = "Marvin Nyingi";
        String email = "marvin@example.com";
        String password = "password";

        var request = new CustomerRegistrationRequest(username, name, email, password);

        BDDMockito.given(appUserRepository.existsByUsername(request.username())).willReturn(false);
        BDDMockito.given(customerRepository.existsByEmail(request.email())).willReturn(false);

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

        BDDMockito.given(appUserRepository.save(Mockito.any(AppUser.class))).willReturn(appUser);

        BDDMockito.given(customerRepository.save(Mockito.any(Customer.class))).willReturn(customer);

        //when
        var actual = customerService.registerCustomer(request);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        BDDMockito.verify(customerRepository).save(customerArgumentCaptor.capture());

        Assertions.assertEquals(request.email(), customerArgumentCaptor.getValue().getEmail());

        org.assertj.core.api.Assertions.assertThat(actual).isNotBlank();
        org.assertj.core.api.Assertions.assertThat(actual).satisfies(s -> Assertions.assertEquals(s, "A verification email has been sent. Please verify email to activate account"));
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