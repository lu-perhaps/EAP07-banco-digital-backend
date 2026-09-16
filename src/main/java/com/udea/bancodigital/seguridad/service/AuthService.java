package com.udea.bancodigital.seguridad.service;

import com.udea.bancodigital.seguridad.dto.LoginRequest;
import com.udea.bancodigital.seguridad.dto.LoginResponse;
import com.udea.bancodigital.seguridad.jwt.JwtUtil;
import com.udea.bancodigital.usuario.entity.Usuario;
import com.udea.bancodigital.usuario.repository.UsuarioRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest request) {

        // Busca el usuario utilizando el email recibido en la solicitud.
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException("Credenciales invalidas"));

        // Compara la contraseña recibida con la contraseña almacenada
        // como hash en la base de datos.
        if (!passwordEncoder.matches(request.password(), usuario.getPasswordHash())) {
            throw new BadCredentialsException("Credenciales invalidas");
        }

        // Verifica que el usuario tenga un estado que permita iniciar sesion.
        if (!usuario.getEstado().getNombre().equalsIgnoreCase("ACTIVO")) {
            throw new BadCredentialsException("El usuario no esta activo");
        }

        // Obtiene el nombre del rol asociado al usuario.
        String rol = usuario.getRol().getNombre();

        // Genera el JWT utilizando el email y el rol del usuario.
        String token = jwtUtil.generarToken(usuario.getEmail(), rol);

        // Devuelve el token y el tipo de autenticacion esperado por el frontend.
        return new LoginResponse(token, "Bearer");
    }
}