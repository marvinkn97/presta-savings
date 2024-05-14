package dev.marvin.savings.appuser;

import dev.marvin.savings.AbstractTestContainersTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AppUserRepositoryTest extends AbstractTestContainersTest {
    @Autowired
    AppUserRepository underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    @DisplayName(value = "Test Case for Saving AppUser")
    void givenAppUserObject_whenSave_thenReturnSavedAppUser() {
        //given
        var admin = AppUser.builder()
                .username("admin@presta")
                .password("password")
                .role(Role.ADMIN)
                .build();

        //when
        var actual = underTest.save(admin);

        //then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isGreaterThan(0);
        assertThat(actual).satisfies(appUser -> {
            assertThat(appUser.getUsername()).isEqualTo(admin.getUsername());
            assertThat(appUser.getRole()).isEqualTo(admin.getRole());
        });
    }

    @Test
    @DisplayName(value = "Test Case for find all AppUsers")
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

        underTest.saveAll(users);

        //when
        var actual = underTest.findAll();

        //then
        assertThat(actual.size()).isGreaterThan(0);
        assertThat(actual.size()).isEqualTo(3);
    }

    @Test
    @DisplayName(value = "Test Case for Positive find AppUser by username")
    void givenUsername_whenFindByUsername_thenReturnSavedAppUser() {
        //given
        String username = "customer@presta";

        AppUser customer = AppUser.builder()
                .username(username)
                .password("password")
                .role(Role.CUSTOMER)
                .build();

        underTest.save(customer);

        //when
        var actualOptional = underTest.findByUsername(username);

        //then
        actualOptional.ifPresent(appUser ->
                assertThat(appUser.getUsername()).isEqualTo(username)
        );
        assertThat(actualOptional).isPresent();
    }

    @Test
    @DisplayName(value = "Test Case for Negative find AppUser by username")
    void givenInvalidUsername_whenFindByUsername_thenThrowException() {
        //given
        String username = "000";

        //when
        var actualOptional = underTest.findByUsername(username);

        //then
        assertThatThrownBy(actualOptional::get)
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No value present");

    }
}