package com.eroul.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Member API Documentation",
                description = "회원 정보 관리 API Documentation",
                version = "v1")
        , security = {
        @SecurityRequirement(name = "Authorization")
})
@SecuritySchemes({
        @SecurityScheme(name = "Authorization",
                type = SecuritySchemeType.HTTP,
                bearerFormat = "JWT",
                scheme = "bearer")
})
@Configuration
public class SwaggerConfig {
}
