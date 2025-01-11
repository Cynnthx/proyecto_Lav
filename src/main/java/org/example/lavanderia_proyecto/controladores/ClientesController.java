package org.example.lavanderia_proyecto.controladores;


import lombok.AllArgsConstructor;
import org.example.lavanderia_proyecto.dto.ClientesCrearDTO;
import org.example.lavanderia_proyecto.modelos.Cliente;
import org.example.lavanderia_proyecto.repositorios.ClientesRepositorio;
import org.example.lavanderia_proyecto.servicios.ClientesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@AllArgsConstructor
public class ClientesController {

    private ClientesRepositorio clientesRepositorio;
    private ClientesService clientesService;

    @GetMapping("/listar")
    public List<Cliente> listarClientes(){
        List<Cliente> cliente = clientesService.getClientes();
        return cliente;
    }
    @PostMapping("/crear")
    public Cliente crearClientes(@RequestBody ClientesCrearDTO clientesCrearDTO){
        Cliente cliente = clientesService.crearNuevo(clientesCrearDTO);
        return cliente;
    }

    @PostMapping("/guardar")
    public Cliente guardar(@RequestBody ClientesCrearDTO cliente) throws Exception {
        return clientesService.guardar(cliente);
    }



}
