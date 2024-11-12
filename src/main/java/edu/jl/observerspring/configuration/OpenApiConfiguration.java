package edu.jl.observerspring.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI configureOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Simplified system for registering " +
                                "vaquejada rodeos and purchasing tickets")
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/license/mit"))
                        .version("1.0.0")
                );
    }
}
