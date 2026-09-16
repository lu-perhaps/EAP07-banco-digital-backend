package com.udea.bancodigital.seguridad.controller;

import com.udea.bancodigital.seguridad.dto.LoginRequest;
import com.udea.bancodigital.seguridad.dto.LoginResponse;
import com.udea.bancodigital.seguridad.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}