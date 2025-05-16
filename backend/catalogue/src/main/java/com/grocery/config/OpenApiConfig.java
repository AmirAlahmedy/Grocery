package com.grocery.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Grocery App API",
                version = "1.0.0",
                description = "API documentation for Grocery Application",
                contact = @Contact(
                        name = "Development Team",
                        email = "ameer.alahmedy@gmail.com"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local development server"),
        }
)
public class OpenApiConfig {
}
