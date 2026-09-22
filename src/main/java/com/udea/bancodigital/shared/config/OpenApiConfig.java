package com.udea.bancodigital.shared.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String BEARER_AUTH = "bearerAuth";

    @Bean
    public OpenAPI bancoDigitalOpenAPI() {
        return new OpenAPI()
                // Metadatos generales de la API
                .info(new Info()
                        .title("Banco Digital API")
                        .description("""
                                API REST del sistema Banco Digital.
                                
                                ## Autenticación
                                La mayoría de los endpoints requieren autenticación mediante **JWT Bearer Token**.
                                
                                Para obtener un token:
                                1. Crea un usuario usando `POST /api/usuarios/registro`
                                2. Inicia sesión con `POST /api/auth/login`
                                3. Copia el `token` de la respuesta
                                4. Haz clic en el botón **Authorize 🔒** e ingresa: `<tu_token>`
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo EAP07 — Fábrica Escuela UdeA")
                                .url("https://github.com/lu-perhaps/EAP07-banco-digital-backend"))
                        .license(new License()
                                .name("MIT License")))

                // Esquema de seguridad: JWT Bearer Token
                .addSecurityItem(new SecurityRequirement().addList(BEARER_AUTH))
                .components(new Components()
                        .addSecuritySchemes(BEARER_AUTH, new SecurityScheme()
                                .name(BEARER_AUTH)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Ingresa el token JWT obtenido al iniciar sesión (sin el prefijo 'Bearer')")));
    }
}
