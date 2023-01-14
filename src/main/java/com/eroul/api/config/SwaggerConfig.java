package com.eroul.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Member API Documentation",
                description = "회원 정보 관리 API Documentation",
                version = "v1"))
@Configuration
public class SwaggerConfig {

}
