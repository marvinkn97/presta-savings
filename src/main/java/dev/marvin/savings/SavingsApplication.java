package dev.marvin.savings;

import dev.marvin.savings.notifications.sms.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

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

    @Bean
    public CommandLineRunner commandLineRunner(SmsService smsService) {
        return args -> {
            try {

                String to = "254790982712";
                String message = "Testing Tiara Connect";
                String from = "TIARACONECT";

				smsService.sendSMS(from, to, message);

            } catch (Exception e) {
                log.info(e.getMessage());
            }
        };
    }

}
