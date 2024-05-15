package com.gooplanycol.gooplany.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Gooplany API",
                description = "Api for Gooplany application",
                version = "1.0.0",
                contact = @Contact(
                        name = "Gooplany",
                        email = "pruebagooplany@gmail.com"
                ),
                license = @License(
                        name = "",
                        url = ""
                )
        ),
        servers = {
                @Server(
                        description = "DEV SERVER",
                        url = "http://localhost:8001"
                ),
                @Server(
                        description = "PROD SERVER",
                        url = "https://pendiente.com"
                )
        }
)
public class SwaggerConfig {
}
