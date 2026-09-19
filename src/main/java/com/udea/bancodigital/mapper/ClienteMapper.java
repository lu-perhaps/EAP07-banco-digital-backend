package com.udea.bancodigital.mapper;

import com.udea.bancodigital.DTO.RegistroUsuarioRequestDTO;
import com.udea.bancodigital.entity.Cliente;
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
