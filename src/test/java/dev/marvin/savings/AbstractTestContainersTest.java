package dev.marvin.savings;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AbstractTestContainersTest {

    @SuppressWarnings("resource")
    protected static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test_db")
            .withUsername("postgres")
            .withPassword("password");

    // Start the container when the class is loaded
    @BeforeAll
    static void startContainer() {
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    protected static void configureDatasourceProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    // Ensure the container is stopped after all tests are executed
    @AfterAll
    static void stopContainer() {
        postgreSQLContainer.stop();
    }
}
