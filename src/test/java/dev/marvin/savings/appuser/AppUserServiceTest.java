package dev.marvin.savings.appuser;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {

    @Mock
    AppUserRepository appUserRepository;

    @Autowired
    AppUserService appUserService;

    AppUser appUser;

    @BeforeEach
    void setUp() {
        appUserService = new AppUserService(appUserRepository);
        appUser = AppUser.builder()
                .username("marvinkn")
                .password("password")
                .role(Role.ADMIN)
                .isEnabled(true)
                .isNotLocked(true)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName(value = "Test Case for getting AppUser by Username")
    void givenUsername_whenLoadUserByUsername_thenReturnUser() {

        //given
        String username = "marvinkn";

        //capture the argument
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        //when
        Mockito.when(appUserRepository.findByUsername(username)).thenReturn(Optional.of(appUser));

        var userDetails = appUserService.loadUserByUsername(username);

        // then
        Mockito.verify(appUserRepository).findByUsername(stringArgumentCaptor.capture());
        assertEquals(username, stringArgumentCaptor.getValue());
        assertEquals(username, userDetails.getUsername());
        org.assertj.core.api.Assertions.assertThat(userDetails).satisfies(u -> {
            assertEquals(appUser.getUsername(), u.getUsername());
            assertEquals(appUser.getAuthorities(), u.getAuthorities());
        });
    }

    @Test
    @DisplayName(value = "Test Case for getting all AppUsers")
    void getAllAppUsers() {
        Mockito.when(appUserRepository.findAll()).thenReturn(List.of(appUser));

        var actual = appUserService.getAllAppUsers();

        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(actual.size()).isEqualTo(1);
        Assertions.assertThat(actual).contains(appUser);
    }
}