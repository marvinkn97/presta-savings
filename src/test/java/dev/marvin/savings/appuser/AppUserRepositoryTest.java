package dev.marvin.savings.appuser;

import dev.marvin.savings.AbstractTestContainersTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AppUserRepositoryTest extends AbstractTestContainersTest {
    @Autowired
    AppUserRepository appUserRepository;

    private AppUser appUser;

    @BeforeEach
    void setUp() {
        appUser = AppUser.builder()
                .username("admin")
                .password("password")
                .role(Role.ADMIN)
                .isEnabled(true)
                .isNotLocked(true)
                .build();
    }

    @Test
    @DisplayName(value = "Test Case for Saving AppUser")
    void givenAppUserObject_whenSave_thenReturnSavedAppUser() {

        //when
        var actual = appUserRepository.save(appUser);

        //then
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(actual.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName(value = "Test Case for find all AppUsers")
    void givenAppUserList_whenFindAll_thenReturnAppUserList() {

        appUserRepository.deleteAll();

        var user = AppUser.builder()
                .username("csr")
                .password("password")
                .role(Role.CSR)
                .isEnabled(true)
                .isNotLocked(true)
                .build();

        appUserRepository.save(appUser);
        appUserRepository.save(user);

        //when
        var actual = appUserRepository.findAll();

        //then
        Assertions.assertThat(actual.size()).isGreaterThan(0);
        Assertions.assertThat(actual.size()).isEqualTo(2);
    }

    @Test
    @DisplayName(value = "Test Case for Positive find AppUser by username")
    void givenUsername_whenFindByUsername_thenReturnSavedAppUser() {
        //given
        appUserRepository.save(appUser);

        //when
        var actualOptional = appUserRepository.findByUsername(appUser.getUsername());
        var actual = actualOptional.get();

        //then
        Assertions.assertThat(actualOptional).isPresent();
        Assertions.assertThat(actual.getUsername()).isEqualTo(appUser.getUsername());
    }

    @Test
    @DisplayName(value = "Test Case for Negative find AppUser by username")
    void givenNonExistentUsername_whenFindByUsername_thenThrowUserNameNotFoundException() {
        //given
        String username = "000";

        //when
        var actualOptional = appUserRepository.findByUsername(username);

        //then
        Assertions.assertThat(actualOptional).isNotPresent();
        Assertions.assertThatThrownBy(actualOptional::get)
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No value present");

    }

    @Test
    @DisplayName(value = "Test Case for Positive Exists AppUser by Username ")
    void givenUsername_whenExistsByUsername_thenReturnTrue() {
        //given

        appUserRepository.save(appUser);

        //when
        var actual = appUserRepository.existsByUsername(appUser.getUsername());

        //then
        Assertions.assertThat(actual).isEqualTo(true);
    }

    @Test
    @DisplayName(value = "Test Case for Negative Exists AppUser by Username ")
    void givenNonExistentUsername_whenExistsByUsername_thenReturnFalse() {
        //given
        String username = "000";

        //when
        var actual = appUserRepository.existsByUsername(username);

        //then
        Assertions.assertThat(actual).isEqualTo(false);
    }
}