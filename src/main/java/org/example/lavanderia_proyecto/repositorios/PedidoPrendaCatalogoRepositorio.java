package org.example.lavanderia_proyecto.repositorios;

import org.example.lavanderia_proyecto.modelos.PedidoPrendaCatalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoPrendaCatalogoRepositorio extends JpaRepository <PedidoPrendaCatalogo, Integer> {
    boolean existsById(Integer id);
}
