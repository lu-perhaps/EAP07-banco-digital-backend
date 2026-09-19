package com.udea.bancodigital.controller;

import com.udea.bancodigital.DTO.ActualizarPerfilRequestDTO;
import com.udea.bancodigital.DTO.PerfilUsuarioDTO;
import com.udea.bancodigital.DTO.RegistroUsuarioRequestDTO;
import com.udea.bancodigital.DTO.RegistroUsuarioResponseDTO;
import com.udea.bancodigital.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    public ResponseEntity<RegistroUsuarioResponseDTO> registrar(
            @Valid @RequestBody RegistroUsuarioRequestDTO request) {

        RegistroUsuarioResponseDTO response = usuarioService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // La identidad sale del token, nunca de la URL: asi un cliente no puede
    // pedir ni editar el perfil de otro cambiando un id.
    @GetMapping("/me")
    public PerfilUsuarioDTO consultarPerfil(Principal principal) {
        return usuarioService.consultarPerfil(principal.getName());
    }

    @PutMapping("/me")
    public PerfilUsuarioDTO actualizarPerfil(
            Principal principal, @Valid @RequestBody ActualizarPerfilRequestDTO request) {

        return usuarioService.actualizarPerfil(principal.getName(), request);
    }

    @GetMapping("/prueba")
    public String prueba() {
        return "Usuario autenticado correctamente";
    }
}