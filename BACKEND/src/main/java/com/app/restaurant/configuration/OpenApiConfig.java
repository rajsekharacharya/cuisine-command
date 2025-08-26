package com.app.restaurant.configuration;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

/**
 * OpenAPI 3 configuration for the RannaSathi application.
 * 
 * Tagline:
 *  - Your Kitchen Companion, Anytime, Anywhere!
 * 
 * Features:
 *  - API metadata
 *  - JWT Bearer token security
 *  - Tags grouping
 *  - External documentation
 *  - OAuth2 placeholder for future expansion
 */
@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "RannaSathi API",
        version = "1.0",
        description = "API documentation for Restaurant — Your Kitchen Companion, Anytime, Anywhere! "
                + "A unique and powerful suite of end-to-end software solutions.",
        termsOfService = "https://vareli.co.in/about",
        contact = @Contact(
            name = "Vareli Team",
            email = "info@vareli.co.in",
            url = "https://vareli.co.in/"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "http://www.apache.org/licenses/LICENSE-2.0.html"
        )
    ),
    security = {
        @SecurityRequirement(name = "bearerAuth")
    },
    externalDocs = @ExternalDocumentation(
        description = "RannaSathi Additional Documentation",
        url = "https://vareli.co.in/docs"
    )
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    description = "JWT Bearer Token Authentication"
)
public class OpenApiConfig {
    // No explicit methods required; annotations configure SpringDoc automatically.
}
