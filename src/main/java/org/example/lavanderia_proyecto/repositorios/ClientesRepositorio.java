package org.example.lavanderia_proyecto.repositorios;

import org.example.lavanderia_proyecto.modelos.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientesRepositorio extends JpaRepository <Cliente, Integer> {
}
