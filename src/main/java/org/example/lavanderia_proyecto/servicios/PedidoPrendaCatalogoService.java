package org.example.lavanderia_proyecto.servicios;

import lombok.AllArgsConstructor;
import org.example.lavanderia_proyecto.modelos.PedidoPrendaCatalogo;
import org.example.lavanderia_proyecto.repositorios.PedidoPrendaCatalogoRepositorio;
import org.example.lavanderia_proyecto.repositorios.PedidoRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    /**
     * Metodo guardar
     * @param pedidoPrendaCatalogo
     * @return
     */
    public PedidoPrendaCatalogo save(PedidoPrendaCatalogo pedidoPrendaCatalogo) {
        return pedidoPrendaCatalogoRepositorio.save(pedidoPrendaCatalogo);
    }


    /**
     * Metodo encontrar por ID
     * @param id
     * @return
     */
    public PedidoPrendaCatalogo getById(Integer id) {
        Optional<PedidoPrendaCatalogo> optionalPpc = pedidoPrendaCatalogoRepositorio.findById(id);
        return optionalPpc.orElse(null);
    }


}
