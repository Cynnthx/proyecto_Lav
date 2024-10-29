package org.example.lavanderia_proyecto.servicios;

import lombok.AllArgsConstructor;
import org.example.lavanderia_proyecto.modelos.PedidoPrendaCatalogo;
import org.example.lavanderia_proyecto.repositorios.PedidoPrendaCatalogoRepositorio;
import org.example.lavanderia_proyecto.repositorios.PedidoRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PedidoPrendaCatalogoService {

    private PedidoPrendaCatalogoRepositorio pedidoPrendaCatalogoRepositorio;

    public List<PedidoPrendaCatalogo> getAll() {
        return pedidoPrendaCatalogoRepositorio.findAll();
    }

    public void delete(Integer id){
        pedidoPrendaCatalogoRepositorio.deleteById(id);
    }
}
