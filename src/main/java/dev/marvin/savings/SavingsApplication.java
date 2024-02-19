package dev.marvin.savings;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SavingsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SavingsApplication.class, args);
	}

}
