package com.udea.bancodigital.service;

import com.udea.bancodigital.DTO.LoginRequestDTO;
import com.udea.bancodigital.DTO.LoginResponseDTO;
import com.udea.bancodigital.jwt.JwtService;
import com.udea.bancodigital.entity.Usuario;
import com.udea.bancodigital.repository.UsuarioRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {

        // Busca el usuario utilizando el email recibido en la solicitud.
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Credenciales invalidas"));

        // Compara la contraseña recibida con la contraseña almacenada
        // como hash en la base de datos.
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash())) {
            throw new BadCredentialsException("Credenciales invalidas");
        }

        // Verifica que el usuario tenga un estado que permita iniciar sesion.
        if (!usuario.getEstado().getNombre().equalsIgnoreCase("ACTIVO")) {
            throw new BadCredentialsException("El usuario no esta activo");
        }

        // Obtiene el nombre del rol asociado al usuario.
        String rol = usuario.getRol().getNombre();

        // Genera el JWT utilizando el email y el rol del usuario.
        String token = jwtService.generarToken(usuario.getEmail(), rol);

        // Devuelve el token y el tipo de autenticacion esperado por el frontend.
        return new LoginResponseDTO(token, "Bearer");
    }
}