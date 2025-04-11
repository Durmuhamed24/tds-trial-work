package com.tds_trial_work.connfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TDSoftware Trial Task API")
                        .version("1.0")
                        .description("API to retrieve information about users, devices, and eSIMs"));
    }
}