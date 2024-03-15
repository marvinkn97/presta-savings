package dev.marvin.savings;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
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

}
