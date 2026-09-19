package com.udea.bancodigital.mapper;

import com.udea.bancodigital.DTO.PerfilUsuarioDTO;
import com.udea.bancodigital.entity.Cliente;
import com.udea.bancodigital.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    int DIGITOS_VISIBLES = 4;

    // El perfil se arma con dos entidades: la identidad de acceso vive en
    // Usuario y los datos personales en Cliente.
    @Mapping(target = "id", source = "usuario.id")
    @Mapping(target = "email", source = "usuario.email")
    @Mapping(target = "fechaNacimiento", source = "usuario.fechaNacimiento")
    @Mapping(target = "nombre", expression = "java(cliente.getNombres() + \" \" + cliente.getApellidos())")
    @Mapping(target = "documentoIdentidad", source = "cliente.numeroDocumento", qualifiedByName = "ofuscarDocumento")
    @Mapping(target = "telefono", source = "cliente.telefono")
    @Mapping(target = "direccion", source = "cliente.direccion")
    PerfilUsuarioDTO toDTO(Usuario usuario, Cliente cliente);

    // El documento nunca viaja completo: solo los ultimos digitos, suficientes
    // para que el titular se reconozca sin exponer el numero.
    @Named("ofuscarDocumento")
    static String ofuscarDocumento(String documento) {
        if (documento == null) {
            return null;
        }
        String limpio = documento.trim();
        if (limpio.length() <= DIGITOS_VISIBLES) {
            return "***" + limpio;
        }
        return "***" + limpio.substring(limpio.length() - DIGITOS_VISIBLES);
    }
}
