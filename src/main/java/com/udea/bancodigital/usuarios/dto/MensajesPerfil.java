package com.udea.bancodigital.usuarios.dto;

// Textos de las HU4 y HU7. Separados de MensajesRegistro porque pertenecen
// al flujo de perfil, no al de alta de usuarios.
public final class MensajesPerfil {

    public static final String CAMPO_VACIO = "El campo no puede estar vacío.";
    public static final String DOCUMENTO_NO_EDITABLE =
            "El documento de identidad no se puede modificar.";
    public static final String CORREO_DE_OTRO_USUARIO =
            "El correo ingresado ya está registrado.";

    private MensajesPerfil() {
    }
}
