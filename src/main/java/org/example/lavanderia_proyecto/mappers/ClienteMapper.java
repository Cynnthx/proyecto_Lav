package org.example.lavanderia_proyecto.mappers;

import org.example.lavanderia_proyecto.dto.ClientesDTO;
import org.example.lavanderia_proyecto.modelos.Cliente;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ClienteMapper {

    /**
     * Este método sirve para pasar a Entity un DTO de Cliente
     * @param dto
     * @return
     */
    Cliente toEntity(ClientesDTO dto);


    /**
     * Este método sirve para pasar a DTO un Entity de Cliente
     * @param entity
     * @return
     */
    ClientesDTO toDTO(Cliente entity);



    List<Cliente> toEntity(List<ClientesDTO> dtos);

    List<ClientesDTO> toDTO(List<Cliente> entities);



}
