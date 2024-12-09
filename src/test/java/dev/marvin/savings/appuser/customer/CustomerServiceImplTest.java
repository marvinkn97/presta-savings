package dev.marvin.savings.appuser.customer;

import dev.marvin.savings.appuser.AppUser;
import dev.marvin.savings.appuser.AppUserRepository;
import dev.marvin.savings.appuser.Role;
import dev.marvin.savings.appuser.confirmationtoken.ConfirmationToken;
import dev.marvin.savings.appuser.confirmationtoken.ConfirmationTokenService;
import dev.marvin.savings.exception.DuplicateResourceException;
import dev.marvin.savings.exception.RequestValidationException;
import dev.marvin.savings.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
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
    CustomerServiceImpl underTest;

    @Test
    void givenCustomerRegistrationRequest_whenRegisterCustomer_thenReturnEmailConfirmationToken() {
        //given
        var username = "marvin@presta";
        var name = "Marvin Nyingi";
        var email = "marvin@example.com";
        var password = "password";
        var mobileNumber = "254792865243";
        var governmentId = "22345313";
        var kraPin = "A0998349823C";

        final String REGISTRATION_RESPONSE = "A verification email has been sent. Please verify email to activate account";

        var request = new CustomerRegistrationRequest(username, name, email, password, mobileNumber, governmentId, kraPin);

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
    void givenTakenUsername_whenRegisterCustomer_thenWillThrowException() {

        //given
        var username = "marvin@presta";
        var name = "Marvin Nyingi";
        var email = "marvin@example.com";
        var password = "password";
        var mobileNumber = "254792865243";
        var governmentId = "22345313";
        var kraPin = "A0998349823C";

        var request = new CustomerRegistrationRequest(username, name, email, password, mobileNumber, governmentId, kraPin);

        given(appUserRepository.existsByUsername(request.username())).willReturn(true);

        assertThatThrownBy(() -> underTest.registerCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("username already taken");
    }

    @Test
    void givenTakenEmail_whenRegisterCustomer_thenWillThrowException() {

        //given
        var username = "marvin@presta";
        var name = "Marvin Nyingi";
        var email = "marvin@example.com";
        var password = "password";
        var mobileNumber = "254792865243";
        var governmentId = "22345313";
        var kraPin = "A0998349823C";

        var request = new CustomerRegistrationRequest(username, name, email, password, mobileNumber, governmentId, kraPin);

        given(customerRepository.existsByEmail(request.email())).willReturn(true);

        assertThatThrownBy(() -> underTest.registerCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("email already taken");
    }


    @Test
    void givenCustomerList_whenGetAllCustomers_thenReturnCustomerList() {

        var u1 = AppUser.builder()
                .username("customer@presta")
                .password("password")
                .role(Role.CUSTOMER)
                .build();

        var u2 = AppUser.builder()
                .username("customer@presta")
                .password("password")
                .role(Role.CUSTOMER)
                .build();

        var u3 = AppUser.builder()
                .username("customer@presta")
                .password("password")
                .role(Role.CUSTOMER)
                .build();

        var c1 = Customer.builder()
                .email("customer1@presta.com")
                .name("Customer One")
                .memberNumber("MEM123")
                .appUser(u1)
                .build();

        var c2 = Customer.builder()
                .email("customer2@presta.com")
                .name("Customer Two")
                .memberNumber("MEM456")
                .appUser(u2)
                .build();

        var c3 = Customer.builder()
                .email("customer3@presta.com")
                .name("Customer Three")
                .memberNumber("MEM789")
                .appUser(u3)
                .build();

        var customers = List.of(c1, c2, c3);
        customerRepository.saveAll(customers);

        when(customerRepository.findAll()).thenReturn(customers);

        //when
        var actual = underTest.getAllCustomers();

        //then
        assertThat(actual).isNotEmpty();
        assertThat(actual.size()).isEqualTo(3);
    }

    @Test
    void givenValidMemberNumber_whenGetCustomerByMemberNumber_thenReturnCustomer() {

        //given
        var memberNumber = "MEM456";

        var u1 = AppUser.builder()
                .username("customer@presta")
                .password("password")
                .role(Role.CUSTOMER)
                .build();

        var c1 = Customer.builder()
                .email("customer1@presta.com")
                .name("Customer One")
                .memberNumber(memberNumber)
                .appUser(u1)
                .build();

        when(customerRepository.findByMemberNumber(memberNumber)).thenReturn(Optional.of(c1));

        //when
        var actual = underTest.getCustomerByMemberNumber(memberNumber);

        //then
        assertThat(actual).satisfies(c -> {
            assertThat(c.memberNumber()).isEqualTo(memberNumber);
            assertThat(c.username()).isEqualTo(c1.getAppUser().getUsername());
            assertThat(c.email()).isEqualTo(c1.getEmail());
        });
    }

    @Test
    void givenNameUpdateRequest_whenUpdateCustomer_thenUpdateCustomerName() {

        String memberNumber = "123456";

        Customer existingCustomer = new Customer();

        var update = new CustomerUpdateRequest("New Name", null, null, null);

        when(customerRepository.findByMemberNumber(memberNumber)).thenReturn(Optional.of(existingCustomer));

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        // when
        underTest.updateCustomer(memberNumber, update);

        verify(customerRepository).save(customerArgumentCaptor.capture());

        assertThat(customerArgumentCaptor.getValue().getName()).isEqualTo(update.fullName());
    }

    @Test
    void givenEmailUpdateRequest_whenUpdateCustomer_thenUpdateCustomerEmail() {
        String memberNumber = "123456";

        Customer existingCustomer = new Customer();

        var update = new CustomerUpdateRequest(null,"newEmail@gmail.com", null, null);

        when(customerRepository.findByMemberNumber(memberNumber)).thenReturn(Optional.of(existingCustomer));


        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        // when
        underTest.updateCustomer(memberNumber, update);

        verify(customerRepository).save(customerArgumentCaptor.capture());

        assertThat(customerArgumentCaptor.getValue().getEmail()).isEqualTo(update.email());
    }

    @Test
    void givenPasswordUpdateRequest_whenUpdateCustomer_thenUpdateCustomer() {
        String memberNumber = "123456";

        AppUser appUser = new AppUser();
        Customer existingCustomer = new Customer();
        existingCustomer.setAppUser(appUser);

        String encodedPassword = passwordEncoder.encode("newPassword");

        var update = new CustomerUpdateRequest(null, null, "newPassword", null);

        when(customerRepository.findByMemberNumber(memberNumber)).thenReturn(Optional.of(existingCustomer));

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        // when
        underTest.updateCustomer(memberNumber, update);

        verify(customerRepository).save(customerArgumentCaptor.capture());

        assertThat(customerArgumentCaptor.getValue().getAppUser().getPassword()).isEqualTo(encodedPassword);

    }

    @Test
    void givenMobileNumberUpdateRequest_whenUpdateCustomer_thenUpdateCustomer() {
        String memberNumber = "123456";

        Customer existingCustomer = new Customer();

        var update = new CustomerUpdateRequest(null,null, null, "254792736452");

        when(customerRepository.findByMemberNumber(memberNumber)).thenReturn(Optional.of(existingCustomer));


        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        // when
        underTest.updateCustomer(memberNumber, update);

        verify(customerRepository).save(customerArgumentCaptor.capture());

        assertThat(customerArgumentCaptor.getValue().getMobileNumber()).isEqualTo(update.mobileNumber());
    }

    @Test
    void givenUpdateRequestWithNoChanges_whenUpdateCustomer_thenPerformNoChanges() {

        String memberNumber = "123456";

        Customer existingCustomer = new Customer();
        existingCustomer.setName("New Name");

        var update = new CustomerUpdateRequest("New Name", null, null, null);

        when(customerRepository.findByMemberNumber(memberNumber)).thenReturn(Optional.of(existingCustomer));

        // when
        assertThatThrownBy(() -> underTest.updateCustomer(memberNumber, update))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("no data changes found");
    }


    @Test
    void givenValidMemberNumber_whenDeleteCustomer_thenAppUserIsLockedAndCustomerIsMarkedAsDeleted() {
        var memberNumber = "MEM456";

        var user = AppUser.builder()
                .username("customer@presta")
                .password("password")
                .role(Role.CUSTOMER)
                .isNotLocked(true)
                .isEnabled(true)
                .build();

        var customer = Customer.builder()
                .email("customer@presta.com")
                .name("Customer Two")
                .memberNumber(memberNumber)
                .appUser(user)
                .isDeleted(false)
                .build();

        when(customerRepository.findByMemberNumber(any(String.class))).thenReturn(Optional.of(customer));

        //when
         underTest.deleteCustomer(memberNumber);

         //then
        assertThat(user.isEnabled()).isEqualTo(false);
        assertThat(user.isNotLocked()).isEqualTo(false);
        assertThat(customer.isDeleted()).isEqualTo(true);

    }

    @Test
    void givenValidMemberNumber_whenDeleteCustomer_thenThrowException(){
        var memberNumber = "0000";

        when(customerRepository.findByMemberNumber(memberNumber)).thenReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> underTest.deleteCustomer(memberNumber))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer does not exist");
    }

}