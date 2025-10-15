package com.codcozapipostgres.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CodCoz - API PostgreSQL")
                        .version("1.0")
                        .description("API responsável por possibilitar o acesso às informações do banco SQL para o app mobile e página web do projeto CodCoz.")
                );
    }
}