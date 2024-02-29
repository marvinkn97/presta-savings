package dev.marvin.savings;

import org.testcontainers.containers.MySQLContainer;


public abstract class AbstractTestContainersTest {

    protected static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.3")
            .withDatabaseName("test_db")
            .withUsername("root")
            .withPassword("1234");
}
