package org.example.lavanderia_proyecto.repositorios;

import org.example.lavanderia_proyecto.modelos.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PedidoRepositorio extends JpaRepository <Pedido, Integer> {
    // List<Pedido> findByCriterio(String criterio);

    List<Pedido> findByFechaEntregaEquals(LocalDate fechaEntrega);
}
