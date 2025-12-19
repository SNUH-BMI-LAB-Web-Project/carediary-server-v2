package kr.io.snuhbmilab.carediaryserverv2.common.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(info = Info(title = "CareDiary API", description = "돌봄일기 서비스", version = "v1"))
class SwaggerConfig(
    @Value($$"${service.swagger.production-url}")
    private var productionUrl: String = "",
) {
    @Bean
    fun openAPI(): OpenAPI {
        val securityRequirement = SecurityRequirement().addList("JWT")
        val components = Components().addSecuritySchemes(
            "JWT", SecurityScheme()
                .name("JWT")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
        )
        return OpenAPI()
            .components(Components())
            .servers(
                listOf(
                    Server()
                        .url(productionUrl)
                        .description("Production"),
                    Server()
                        .url("http://localhost:8080")
                        .description("Local")
                )
            )
            .addSecurityItem(securityRequirement)
            .components(components)
    }
}
