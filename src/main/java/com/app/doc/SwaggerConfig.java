package com.app.doc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Seguro/Cotação")
                        .version("1.0.0")
                        .description("Documentação da API REST para gerenciamento de contação de seguros")
                        .contact(new Contact()
                                .name("Ricardo Ludwig")
                                .email("dev@gmail.com")
                                .url("https://github.com/ricardoludwig/java-springboot")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Servidor Local")
                ));
    }
}