package dev.marvin.savings;

import dev.marvin.savings.appuser.Role;
import dev.marvin.savings.appuser.User;
import dev.marvin.savings.appuser.UserRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootApplication(scanBasePackages = "dev.marvin.savings")
@OpenAPIDefinition(info = @Info(title = "Presta Savings App Documentation",
        description = "Spring Boot  REST API Documentation", version = "v1.0",
        contact = @Contact(name = "Marvin", email = "marvin.nyingi97@gmail.com")))
public class SavingsApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .profiles("dev")
                .sources(SavingsApplication.class)
                .run(args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return args -> {
            User admin = User.builder()
                    .userName("Admin")
                    .password(passwordEncoder.encode("admin@123"))
                    .role(Role.ADMIN)
                    .isActive(true)
                    .isNotLocked(true)
                    .joinDate(LocalDateTime.now())
                    .build();

            userRepository.save(admin);
        };
    }

}
