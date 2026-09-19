package com.udea.bancodigital.controller;

import com.udea.bancodigital.DTO.LoginRequestDTO;
import com.udea.bancodigital.DTO.LoginResponseDTO;
import com.udea.bancodigital.service.AuthService;
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
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }
}