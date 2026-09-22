package com.udea.bancodigital.usuarios.dto;

import java.time.LocalDate;

public class PerfilUsuarioDTO {

    private Long id;
    private String nombre;
    private String email;
    private String documentoIdentidad;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String direccion;

    public PerfilUsuarioDTO() {
    }

    public PerfilUsuarioDTO(Long id, String nombre, String email, String documentoIdentidad,
                            LocalDate fechaNacimiento, String telefono, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.documentoIdentidad = documentoIdentidad;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDocumentoIdentidad() { return documentoIdentidad; }
    public void setDocumentoIdentidad(String documentoIdentidad) { this.documentoIdentidad = documentoIdentidad; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}
