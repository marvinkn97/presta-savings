package dev.marvin.savings.appuser;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;

@ExtendWith(value = SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AppUserRepositoryTest {
    @Autowired
    AppUserRepository appUserRepository;

    @Test
    @DisplayName(value = "Test Case for Saving AppUser")
    void givenAppUserObject_whenSave_thenReturnSavedAppUser(){
        //given
        var admin = AppUser.builder()
                .username("admin@presta")
                .password("password")
                .role(Role.ADMIN)
                .isEnabled(true)
                .isNotLocked(true)
                .build();

        //when
        var actual = appUserRepository.save(admin);

        //then
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(actual.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName(value = "Test Case for Positive find AppUser by username")
    void givenUsername_whenFindByUsername_thenReturnSavedAppUser() {
        //given
        String username = "newuser@presta";

        var user = AppUser.builder()
                .username(username)
                .password("password")
                .role(Role.ADMIN)
                .isEnabled(true)
                .isNotLocked(true)
                .build();

         appUserRepository.save(user);

        //when
        var actualOptional = appUserRepository.findByUsername(username);
        var actual = actualOptional.get();

        //then
        Assertions.assertThat(actualOptional).isPresent();
        Assertions.assertThat(actual.getUsername()).isEqualTo(username);
    }

    @Test
    @DisplayName(value = "Test Case for Negative find AppUser by username")
    void givenNonExistentUsername_whenFindByUsername_thenThrowUserNameNotFoundException() {
        //given
        String username = "username";

        //when
        var actualOptional = appUserRepository.findByUsername(username);

        //then
        Assertions.assertThat(actualOptional).isNotPresent();
        Assertions.assertThatThrownBy(actualOptional::get)
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No value present");

    }

    @Test
    void existsByUsername() {

    }
}