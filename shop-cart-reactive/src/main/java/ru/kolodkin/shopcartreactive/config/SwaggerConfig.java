package ru.kolodkin.shopcartreactive.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(
        title = "Spring WebFlux,PostgreSQL",
        description = "Spring WebFlux, PostgreSQL",
        contact = @Contact(
                name = "Konstantin Kolodkin",
                email = "kostya.kolodkin@inbox.ru",
                url = "https://github.com/kotolanchik"
        ),
        version = "1.0.0"
))
@Configuration
public class SwaggerConfig {
}
