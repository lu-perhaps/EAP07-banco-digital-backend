package com.udea.bancodigital.usuarios.api;

/**
 * API pública del módulo de Usuarios.
 * <p>
 * Los demás módulos (cuentas, transferencias) solo deben depender de esta interfaz,
 * nunca de las clases internas del módulo (UsuarioService, UsuarioRepository, etc.).
 * Esto garantiza el encapsulamiento del módulo y permite que sus internos cambien
 * sin afectar a los consumidores.
 */
public interface UsuarioApi {

    /**
     * Obtiene el ID del cliente asociado al usuario autenticado dado su email.
     *
     * @param email email extraído del JWT
     * @return el clienteId asociado
     */
    Long obtenerIdClientePorEmail(String email);
}
