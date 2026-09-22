package com.udea.bancodigital.usuarios.mapper;

import com.udea.bancodigital.usuarios.dto.RegistroUsuarioRequestDTO;
import com.udea.bancodigital.usuarios.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    // id y fechaRegistro los fija la capa de servicio, no la peticion.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    Cliente toEntity(RegistroUsuarioRequestDTO dto);
}
