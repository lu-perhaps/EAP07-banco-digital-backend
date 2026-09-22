package com.udea.bancodigital.usuarios.controller;

import com.udea.bancodigital.usuarios.dto.LoginRequestDTO;
import com.udea.bancodigital.usuarios.dto.LoginResponseDTO;
import com.udea.bancodigital.usuarios.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints para la autenticación de usuarios (login)")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "Iniciar sesión",
            description = "Autentica las credenciales del usuario y devuelve un token JWT válido por 1 hora."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa. Retorna el token JWT.",
                    content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas (email o contraseña incorrectos).",
                    content = @Content)
    })
    @SecurityRequirements  // Este endpoint es público, no requiere token
    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }
}
