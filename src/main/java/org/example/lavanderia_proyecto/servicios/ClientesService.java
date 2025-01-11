package org.example.lavanderia_proyecto.servicios;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.lavanderia_proyecto.dto.ClientesCrearDTO;
import org.example.lavanderia_proyecto.dto.ClientesDTO;
import org.example.lavanderia_proyecto.modelos.Cliente;
import org.example.lavanderia_proyecto.repositorios.ClientesRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Validated
public class ClientesService {
    private ClientesRepositorio clientesRepository;


//    /**
//     * Busca clientes por dni
//     *
//     * @param dni
//     * @return
//     */
//    public List<Cliente> getClientesPorDNI(String dni, String nombre){
//        List<Cliente> clientes = clientesRepository.findAllByDniEqualsAndNombreLike(dni, nombre);
//        return clientes;
//    }

    /**
     * primer metodo Luis, mostrar precios servicios y catalogo
     */
    public List<Cliente> getClientes (){
        return clientesRepository.findAll();
    }


    public List<ClientesDTO> getAll() {

        List<Cliente> clientes = clientesRepository.findAll();
        List<ClientesDTO> clientesDTOS = new ArrayList<>();
        for (Cliente c : clientes) {
            ClientesDTO dto = new ClientesDTO();
            dto.setNombre(c.getNombre());
            dto.setApellidos(c.getApellidos());
            dto.setDireccion(c.getDireccion());
            clientesDTOS.add(dto);
        }
        return clientesDTOS;
    }

    /**
     * Crea un nuevo cliente
     *
     * @param clientesCrearDTO
     * @return
     */
    public Cliente crearNuevo(@Valid ClientesCrearDTO clientesCrearDTO){
        Cliente entity = new Cliente();
        entity.setNombre(clientesCrearDTO.getNombre());
        entity.setApellidos(clientesCrearDTO.getApellidos());
        entity.setDireccion(clientesCrearDTO.getDireccion());
        entity.setDni(clientesCrearDTO.getDni());
        entity.setTelefono(clientesCrearDTO.getTelefono());

        return clientesRepository.save(entity);


    }

    /**
     * Edita un cliente
     *
     * @param dto
     * @param id
     * @return
     */
    public Cliente editar (ClientesCrearDTO dto, Integer id){
        Cliente entity =clientesRepository.getReferenceById(id);
        entity.setNombre(dto.getNombre());
        entity.setApellidos(dto.getApellidos());
        entity.setDireccion(dto.getDireccion());
        entity.setDni(dto.getDni());
        entity.setTelefono(dto.getTelefono());

        return clientesRepository.save(entity);
    }
    /**
     * Busca un cliente por id
     *
     * @param id
     * @return
     */
    public Cliente getById(Integer id) {
        return clientesRepository.findById(id).orElse(null);
    }


    /**
     * Crea un cliente nuevo o modifica uno existente
     * <p>
     * // * @param clientes
     *
     * @param dto
     * @return
     */
    public Cliente guardar(@Valid ClientesCrearDTO dto) throws Exception {
        Cliente clienteGuardado = new Cliente();
        clienteGuardado.setNombre(dto.getNombre());
        clienteGuardado.setApellidos(dto.getApellidos());
        clienteGuardado.setDireccion(dto.getDireccion());
        clienteGuardado.setDni(dto.getDni());
        clienteGuardado.setTelefono(dto.getTelefono());

        if(dto.getDni().length() != 9){
            throw new Exception("El DNI debe tener 9 caracteres");
        }
        clienteGuardado = clientesRepository.save(clienteGuardado);
        return clienteGuardado;
    }

    //FECHA NACIMIENTO (STRING) -> LOCALDATE
    // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    //LocalDate fechaNacimiento = LocalDate.parse(dto.getFechaNacimiento(), formatter);
    // clientesGuardado.setFechaNacimiento(fechaNacimiento);


    /**
     * Elimina un cliente por id
     *
     * @param id
     */
    public String eliminar(Integer id) {
        String mensaje;
        Cliente cliente = getById(id);
        if (cliente == null) {
            return "El cliente con el id indicado no existe";
        }
        try {
            clientesRepository.deleteById(id);
            cliente = getById(id);
            if (cliente != null) {
                mensaje = "No se ha podido eliminar el cliente";
            } else {
                mensaje = "Cliente eliminado correctamente";
            }
        } catch (Exception e) {
            mensaje = "No se ha podido eliminar el cliente";
        }
        return mensaje;


    }
}
