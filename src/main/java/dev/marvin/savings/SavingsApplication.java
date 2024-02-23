package dev.marvin.savings;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class SavingsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SavingsApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(){
		return args -> {
		    try{

				String to = "254792865297";
				String message = "Testing ATK SMS API";
				String from = "PRESTA";

			}catch (Exception e){
				log.info(e.getMessage());
			}
		};
	}

}
