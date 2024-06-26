package dev.marvin.savings;

import dev.marvin.savings.appuser.AppUser;
import dev.marvin.savings.appuser.AppUserRepository;
import dev.marvin.savings.appuser.Role;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication(scanBasePackages = "dev.marvin.savings")
@OpenAPIDefinition(info = @Info(title = "Presta Savings App Documentation",
        description = "Spring Boot  REST API Documentation", version = "v1.0",
        contact = @Contact(name = "Marvin", email = "marvin.nyingi97@gmail.com")))
@ComponentScan(basePackages = "dev.marvin.savings")
public class SavingsApplication {
    public static void main(String... args) {
        new SpringApplicationBuilder()
                .profiles("dev")
                .sources(SavingsApplication.class)
                .run(args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            var admin = AppUser.builder()
                    .username("admin@presta")
                    .password(passwordEncoder.encode("password"))
                    .role(Role.ADMIN)
                    .isEnabled(true)
                    .isNotLocked(true)
                    .build();

            var CSR = AppUser.builder()
                    .username("csr@presta")
                    .password(passwordEncoder.encode("password"))
                    .role(Role.CSR)
                    .isEnabled(true)
                    .isNotLocked(true)
                    .build();

            var users = List.of(admin, CSR);

            appUserRepository.saveAll(users);

        };
    }

}
