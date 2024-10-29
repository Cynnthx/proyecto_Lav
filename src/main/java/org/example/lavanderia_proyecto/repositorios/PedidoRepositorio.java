package org.example.lavanderia_proyecto.repositorios;

import org.example.lavanderia_proyecto.modelos.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepositorio extends JpaRepository <Pedido, Integer> {

}
