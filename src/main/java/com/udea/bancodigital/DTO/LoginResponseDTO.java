package com.udea.bancodigital.DTO;

// DTO de salida: lo que el backend responde tras un login exitoso.
// Nunca se expone la entidad Usuario directamente (evita filtrar passwordHash).
public class LoginResponseDTO {

    private String token;
    private String tipo;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, String tipo) {
        this.token = token;
        this.tipo = tipo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
