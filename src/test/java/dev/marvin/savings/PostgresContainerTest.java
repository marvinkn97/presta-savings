package dev.marvin.savings;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class PostgresContainerTest extends AbstractTestContainersTest {

    @Test
    void canStartPostgresDB(){
        Assertions.assertThat(postgreSQLContainer.isCreated()).isTrue();
        Assertions.assertThat(postgreSQLContainer.isRunning()).isTrue();
    }
}
