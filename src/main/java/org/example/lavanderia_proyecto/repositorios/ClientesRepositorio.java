package org.example.lavanderia_proyecto.repositorios;

import org.example.lavanderia_proyecto.modelos.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientesRepositorio extends JpaRepository <Cliente, Integer> {

    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%')) " +
            "OR LOWER(c.apellidos) LIKE LOWER(CONCAT('%', :busqueda, '%')) ")
    List<Cliente> buscar(@Param("busqueda") String busqueda);
}
