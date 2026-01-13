package com.astropay.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {


  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("AstroPay Demo API")
            .description("REST API for AstroPay Demo Application - User, Account and Transaction Management")
            .version("1.0.0")
            .contact(new Contact()
                .name("AstroPay")
                .url("https://www.astropay.com")
                .email("support@astropay.com")));
  }
}