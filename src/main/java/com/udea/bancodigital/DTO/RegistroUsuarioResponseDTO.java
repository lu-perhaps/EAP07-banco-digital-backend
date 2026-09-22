package com.udea.bancodigital.DTO;

public class RegistroUsuarioResponseDTO {

    private String message;
    private Long usuarioId;
    private String email;

    public RegistroUsuarioResponseDTO() {
    }

    public RegistroUsuarioResponseDTO(String message, Long usuarioId, String email) {
        this.message = message;
        this.usuarioId = usuarioId;
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
