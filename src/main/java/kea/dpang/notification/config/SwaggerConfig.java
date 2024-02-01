package kea.dpang.notification.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

/**
 * Swagger(Spring Docs) 설정
 */
@OpenAPIDefinition(
        info = @Info(title = "DPANG 알림 서버 API 명세서",
                description = "DPANG 알림 서버 API 명세서",
                version = "0.3.0")
)
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    public OpenApiCustomizer securityBudiler() {
        return openApi -> openApi.addSecurityItem(new SecurityRequirement().addList("Access Token"))
                .getComponents()
                .addSecuritySchemes("Access Token", new SecurityScheme()
                        .name(HttpHeaders.AUTHORIZATION)
                        .type(SecurityScheme.Type.HTTP)
                        .in(SecurityScheme.In.HEADER)
                        .bearerFormat("JWT")
                        .scheme("bearer"));
    }

    @Bean
    public GroupedOpenApi openApi() {
        String[] paths = {"/**"};

        return GroupedOpenApi.builder()
                .group("DPANG 알림 서비스 API")
                .pathsToMatch(paths)
                .addOpenApiCustomizer(securityBudiler())
                .build();
    }
}
