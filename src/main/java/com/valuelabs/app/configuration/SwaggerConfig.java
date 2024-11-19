package com.valuelabs.app.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${springdoc.module-name:tracking-number-generator}")
    private String moduleName;

    @Value("${springdoc.api-version:v1.0}")
    private String apiVersion;

    @Value("${management.endpoints.web.base-path}/**")
    private String path;

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().info(new Info().title(moduleName).version(apiVersion));
    }

}
