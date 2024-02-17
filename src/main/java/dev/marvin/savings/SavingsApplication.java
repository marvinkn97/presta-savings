package dev.marvin.savings;

import dev.marvin.savings.notifications.sms.TiaraConnectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class SavingsApplication {

	@Autowired
	private TiaraConnectService tiaraConnectService;
	public static void main(String[] args) {
		SpringApplication.run(SavingsApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(){
		return args -> {
			try {
				tiaraConnectService.sendSMS("25492865297", "Integrated successfully to Tiara..i think");
			} catch (Exception e) {
				// Handle any exceptions that occur during SMS sending
				log.info(e.getMessage());
			}
		};
	}
}
