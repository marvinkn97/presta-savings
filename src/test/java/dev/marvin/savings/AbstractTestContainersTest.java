package dev.marvin.savings;

import org.testcontainers.containers.MySQLContainer;

public abstract class AbstractTestContainersTest {

    protected static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("test_db")
            .withUsername("root")
            .withPassword("1234");
}
