package dev.marvin.savings.appuser.confirmationtoken;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConfirmationTokenServiceTest {

    @Mock
    ConfirmationTokenRepository confirmationTokenRepository;

    @InjectMocks
    ConfirmationTokenService underTest;

    @BeforeEach
    void setUp() {
    }

    @Test
    void generateToken() {
    }

    @Test
    void validateAndConfirmToken() {
    }
}