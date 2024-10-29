package org.example.lavanderia_proyecto.repositorios;

import org.example.lavanderia_proyecto.modelos.Prenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrendaRepositorio extends JpaRepository <Prenda, Integer> {
}
