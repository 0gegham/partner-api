package com.sparkers.partner.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${swagger.info.title}")
    private String title;

    @Value("${swagger.info.description}")
    private String description;

    @Value("${swagger.info.version}")
    private String version;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .description(description)
                        .title(title)
                        .version(version));
    }

}
