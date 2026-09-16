package com.udea.bancodigital.seguridad.jwt;

import com.udea.bancodigital.usuario.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;

    public JwtAuthFilter(
            JwtUtil jwtUtil,
            UsuarioRepository usuarioRepository
    ) {
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);

        if (!jwtUtil.esTokenValido(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String email = jwtUtil.extraerEmail(token);

        usuarioRepository.findByEmail(email).ifPresent(usuario -> {

            String rol = usuario.getRol().getNombre();

            System.out.println("ROL DEL USUARIO: " + rol);

            var autoridad = new SimpleGrantedAuthority("ROLE_" + rol);

            System.out.println("AUTORIDAD: " + autoridad.getAuthority());

            var autenticacion = new UsernamePasswordAuthenticationToken(
                    usuario.getEmail(),
                    null,
                    List.of(autoridad)
            );

            SecurityContextHolder.getContext().setAuthentication(autenticacion);
        });

        filterChain.doFilter(request, response);
    }
}