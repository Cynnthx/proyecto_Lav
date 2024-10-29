package org.example.lavanderia_proyecto.controladores;


import lombok.AllArgsConstructor;
import org.example.lavanderia_proyecto.modelos.Cliente;
import org.example.lavanderia_proyecto.repositorios.ClientesRepositorio;
import org.example.lavanderia_proyecto.servicios.ClientesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
