package dev.marvin.savings.appuser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {

    @Mock
    AppUserRepository appUserRepository;

    @InjectMocks
    AppUserService underTest;

    @BeforeEach
    void setUp() {
        appUserRepository.deleteAll();
    }

    @Test
    @DisplayName(value = "Test Case for Positive getting AppUser by Username")
    void givenUsername_whenLoadUserByUsername_thenReturnUser() {

        //given
        String username = "admin@presta";

        var appUser = AppUser.builder()
                .username(username)
                .password("password")
                .role(Role.ADMIN)
                .build();

        //when
        when(appUserRepository.findByUsername(username)).thenReturn(Optional.of(appUser));

        var userDetails = underTest.loadUserByUsername(username);

        // then
        assertEquals(username, userDetails.getUsername());
        assertThat(userDetails).satisfies(u -> {
            assertEquals(appUser.getUsername(), u.getUsername());
            assertEquals(appUser.getAuthorities(), u.getAuthorities());
        });
    }

    @Test
    @DisplayName(value = "Test Case for Negative getting AppUser by Username")
    void givenNonExistentUsername_whenLoadUserByUsername_thenReturnUser() {
        var username = "000";

        assertThatThrownBy(() -> underTest.loadUserByUsername(username))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("user not found");

    }

    @Test
    @DisplayName(value = "Test Case for getting all AppUsers")
    void givenAppUserList_whenFindAll_thenReturnAppUserList() {

        //given
        var admin = AppUser.builder()
                .username("admin@presta")
                .password("password")
                .role(Role.ADMIN)
                .build();

        var csr = AppUser.builder()
                .username("csr@presta")
                .password("password")
                .role(Role.CSR)
                .build();

        var customer = AppUser.builder()
                .username("customer@presta")
                .password("password")
                .role(Role.CUSTOMER)
                .build();

        var users = List.of(admin, csr, customer);

        appUserRepository.saveAll(users);


        when(appUserRepository.findAll()).thenReturn(users);

        //when
        var actual = underTest.getAllAppUsers();

        assertThat(actual).isNotEmpty();
        assertThat(actual.size()).isEqualTo(3);
    }
}