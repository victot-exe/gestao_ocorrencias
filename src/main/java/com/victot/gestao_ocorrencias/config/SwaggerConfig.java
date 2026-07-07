package com.victot.gestao_ocorrencias.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Gestão de Ocorrências API")
                        .version("1.0.0")
                        .description("Documentação dos endpoints do backend de ocorrências."));
    }
}