package dev.marvin.savings;

import dev.marvin.savings.entity.RoleEnum;
import dev.marvin.savings.entity.UserEntity;
import dev.marvin.savings.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(scanBasePackages = "dev.marvin.savings")
@ComponentScan(basePackages = "dev.marvin.savings")
public class SavingsApplication {
    public static void main(String... args) {
        SpringApplication.run(SavingsApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            UserEntity userEntity = new UserEntity();
            userEntity.setMobileNumber("254707808236");
            userEntity.setFullName("Admin");
            userEntity.setPassword(passwordEncoder.encode("password"));
            userEntity.setRoleEnum(RoleEnum.ROLE_ADMIN);
            userRepository.save(userEntity);
        };
    }

}
