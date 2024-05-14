package dev.marvin.savings.appuser.confirmationtoken;

import dev.marvin.savings.AbstractTestContainersTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ConfirmationTokenRepositoryTest extends AbstractTestContainersTest {

    @Autowired
    ConfirmationTokenRepository underTest;

    final String token = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {
        underTest.deleteAll();

        var confirmationToken = ConfirmationToken.builder()
                .token(token)
                .issuedAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .build();

        underTest.save(confirmationToken);
    }

    @Test
    void givenValidToken_whenFindByToken_thenReturnConfirmationToken() {
        var token = this.token;

        //when
        var actual = underTest.findByToken(token);

        //then
        Assertions.assertThat(actual).isPresent();
        actual.ifPresent(confirmationToken -> assertEquals(confirmationToken.getToken(), token));
    }
}