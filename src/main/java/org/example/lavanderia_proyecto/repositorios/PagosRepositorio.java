package org.example.lavanderia_proyecto.repositorios;

import org.example.lavanderia_proyecto.modelos.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagosRepositorio extends JpaRepository <Pagos, Integer> {
    Pagos findByPedidoId(Integer pedidoId);
}
