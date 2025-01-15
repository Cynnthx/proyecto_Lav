package org.example.lavanderia_proyecto.repositorios;

import org.example.lavanderia_proyecto.modelos.Prenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrendaRepositorio extends JpaRepository<Prenda, Integer> {
    List<Prenda> findByNombre(String nombre);
}