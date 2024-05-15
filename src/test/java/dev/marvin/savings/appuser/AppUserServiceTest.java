package dev.marvin.savings.appuser;

import org.junit.jupiter.api.BeforeEach;
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

    AppUser admin;

    AppUser csr;

    AppUser customer;

    List<AppUser> users;

    @BeforeEach
    void setUp() {
        appUserRepository.deleteAll();

         admin = AppUser.builder()
                .username("admin@presta")
                .password("password")
                .role(Role.ADMIN)
                .build();

         csr = AppUser.builder()
                .username("csr@presta")
                .password("password")
                .role(Role.CSR)
                .build();

        customer = AppUser.builder()
                .username("customer@presta")
                .password("password")
                .role(Role.CUSTOMER)
                .build();

        users = List.of(admin, csr, customer);

        appUserRepository.saveAll(users);
    }

    @Test
    void givenUsername_whenLoadUserByUsername_thenReturnUser() {

        //given
        String username = "admin@presta";

        //when
        when(appUserRepository.findByUsername(username)).thenReturn(Optional.of(admin));

        var userDetails = underTest.loadUserByUsername(username);

        // then
        assertEquals(username, admin.getUsername());
        assertThat(userDetails).satisfies(u -> {
            assertEquals(u.getUsername(), admin.getUsername());
            assertEquals(u.getAuthorities(), admin.getAuthorities());
        });
    }

    @Test
    void givenNonExistentUsername_whenLoadUserByUsername_thenReturnUser() {
        var username = "000";

        assertThatThrownBy(() -> underTest.loadUserByUsername(username))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("user not found");

    }

    @Test
    void givenAppUserList_whenFindAll_thenReturnAppUserList() {

        when(appUserRepository.findAll()).thenReturn(users);

        //when
        var actual = underTest.getAllAppUsers();

        assertThat(actual).isNotEmpty();
        assertThat(actual.size()).isEqualTo(3);
    }
}