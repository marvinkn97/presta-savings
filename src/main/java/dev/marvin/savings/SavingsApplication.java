package dev.marvin.savings;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@Slf4j
@SpringBootApplication
public class SavingsApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .profiles("dev")
                .sources(SavingsApplication.class)
                .run(args);

//		SpringApplication.run(SavingsApplication.class, args);
    }

}
