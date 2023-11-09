package com.billlog.publicweatherapi.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${app.domain}")
    private String domain;

    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .version("v0.0.1")
                .title("Public Weather API V1")
                .description("Public Weathe Api Description");

        // SecuritySecheme명
        String jwtSchemeName = "jwt-token";
        // API 요청헤더에 인증정보 포함
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        // SecuritySchemes 등록
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer")
                        .bearerFormat("JWT")); // 토큰 형식을 지정하는 임의의 문자(Optional)

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .addServersItem(new Server().url(domain))
                .components(components);
    }

    // 그룹핑
    @Bean
    public GroupedOpenApi integrationGroup() {
        return GroupedOpenApi.builder()
                .group("1. 기상 데이터 통합 API")
                .pathsToMatch("/billlog-api/pub-weather/api/v*/**")
                .build();
    }
}
