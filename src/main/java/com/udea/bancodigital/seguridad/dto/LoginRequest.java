package com.udea.bancodigital.seguridad.dto;

// DTO de entrada: lo que el frontend envia al hacer login
// Se usa un record porque es un dato inmutable, sin comportamiento propio
public record LoginRequest(String email, String password) {
}