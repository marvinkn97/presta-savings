package dev.marvin.savings.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Bean
    public DataSource dataSource(){
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/savings")
                .username("root")
                .password("pass@123")
                .build();
    }
}
