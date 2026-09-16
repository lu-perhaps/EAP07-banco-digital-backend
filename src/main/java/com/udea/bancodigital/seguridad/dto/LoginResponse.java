package com.udea.bancodigital.seguridad.dto;

// DTO de salida: lo que el backend responde tras un login exitoso.
// Nunca se expone la entidad Usuario directamente (evita filtrar passwordHash).
public record LoginResponse(String token, String tipo) {
}