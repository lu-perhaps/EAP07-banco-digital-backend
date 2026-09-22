package com.udea.bancodigital.service;

import com.udea.bancodigital.entity.Cliente;
import com.udea.bancodigital.repository.ClienteRepository;
import com.udea.bancodigital.entity.Estado;
import com.udea.bancodigital.repository.EstadoRepository;
import com.udea.bancodigital.DTO.ActualizarPerfilRequestDTO;
import com.udea.bancodigital.DTO.MensajesPerfil;
import com.udea.bancodigital.DTO.MensajesRegistro;
import com.udea.bancodigital.DTO.PerfilUsuarioDTO;
import com.udea.bancodigital.DTO.RegistroUsuarioRequestDTO;
import com.udea.bancodigital.DTO.RegistroUsuarioResponseDTO;
import com.udea.bancodigital.entity.Rol;
import com.udea.bancodigital.entity.Usuario;
import com.udea.bancodigital.repository.RolRepository;
import com.udea.bancodigital.mapper.ClienteMapper;
import com.udea.bancodigital.mapper.UsuarioMapper;
import com.udea.bancodigital.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private static final String ESTADO_INICIAL = "ACTIVO";

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final RolRepository rolRepository;
    private final EstadoRepository estadoRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;
    private final ClienteMapper clienteMapper;

    @Transactional
    public RegistroUsuarioResponseDTO registrar(RegistroUsuarioRequestDTO request) {

        String email = request.getEmail().trim();

        if (usuarioRepository.existsByEmailIgnoreCase(email)) {
            throw new NegocioException(
                    "EMAIL_ALREADY_REGISTERED",
                    MensajesRegistro.CORREO_DUPLICADO,
                    HttpStatus.CONFLICT
            );
        }

        Rol rol = rolRepository.findByNombreIgnoreCase(request.getRol().trim())
                .orElseThrow(() -> new NegocioException(
                        "INVALID_ROLE",
                        "El rol especificado no es válido: " + request.getRol(),
                        HttpStatus.BAD_REQUEST
                ));

        Estado estadoActivo = estadoRepository.findByNombreIgnoreCase(ESTADO_INICIAL)
                .orElseThrow(() -> new NegocioException(
                        "INTERNAL_ERROR",
                        "Estado '" + ESTADO_INICIAL + "' no configurado en el sistema",
                        HttpStatus.INTERNAL_SERVER_ERROR
                ));

        OffsetDateTime ahora = OffsetDateTime.now();

        Cliente cliente = clienteMapper.toEntity(request);
        cliente.setEmail(email);
        cliente.setFechaRegistro(ahora);
        Cliente clienteGuardado = clienteRepository.save(cliente);

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        usuario.setFechaCreacion(ahora);
        usuario.setFechaNacimiento(request.getFechaNacimiento());
        usuario.setClienteId(clienteGuardado.getId());
        usuario.setRol(rol);
        usuario.setEstado(estadoActivo);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return new RegistroUsuarioResponseDTO(
                MensajesRegistro.REGISTRO_EXITOSO,
                usuarioGuardado.getId(),
                usuarioGuardado.getEmail()
        );
    }

    @Transactional(readOnly = true)
    public PerfilUsuarioDTO consultarPerfil(String emailAutenticado) {
        Usuario usuario = buscarPorEmail(emailAutenticado);
        return usuarioMapper.toDTO(usuario, buscarClienteDe(usuario));
    }

    @Transactional
    public PerfilUsuarioDTO actualizarPerfil(
            String emailAutenticado, ActualizarPerfilRequestDTO request) {

        if (request.getDocumentoIdentidad() != null) {
            throw new NegocioException(
                    "FIELD_NOT_EDITABLE",
                    MensajesPerfil.DOCUMENTO_NO_EDITABLE,
                    HttpStatus.BAD_REQUEST
            );
        }

        Usuario usuario = buscarPorEmail(emailAutenticado);
        Cliente cliente = buscarClienteDe(usuario);

        String nuevoEmail = request.getEmail().trim();
        if (usuarioRepository.existsByEmailIgnoreCaseAndIdNot(nuevoEmail, usuario.getId())) {
            throw new NegocioException(
                    "DUPLICATE_EMAIL",
                    MensajesPerfil.CORREO_DE_OTRO_USUARIO,
                    HttpStatus.CONFLICT
            );
        }

        // El correo esta duplicado en las dos tablas desde el registro;
        // si solo se actualizara una, el login y el perfil dejarian de coincidir.
        usuario.setEmail(nuevoEmail);
        cliente.setEmail(nuevoEmail);
        cliente.setTelefono(request.getTelefono().trim());
        cliente.setDireccion(request.getDireccion().trim());

        usuarioRepository.save(usuario);
        clienteRepository.save(cliente);

        return usuarioMapper.toDTO(usuario, cliente);
    }

    @Transactional(readOnly = true)
    public Long obtenerIdClientePorEmail(String email) {
        return buscarPorEmail(email).getClienteId();
    }

    private Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NegocioException(
                        "USER_NOT_FOUND", "El usuario autenticado ya no existe",
                        HttpStatus.NOT_FOUND));
    }

    private Cliente buscarClienteDe(Usuario usuario) {
        return clienteRepository.findById(usuario.getClienteId())
                .orElseThrow(() -> new NegocioException(
                        "CLIENT_NOT_FOUND", "El usuario no tiene un cliente asociado",
                        HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
