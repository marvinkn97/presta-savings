package dev.marvin.savings.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Presta Savings API")
                        .summary("Backend API for a simple savings management system")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Marvin Nyingi")
                                .email("marvin.nyingi97@gmail.com"))
                );
    }
}
