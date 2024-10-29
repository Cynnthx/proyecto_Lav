package org.example.lavanderia_proyecto.repositorios;

import org.example.lavanderia_proyecto.modelos.Catalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogoRepositorio extends JpaRepository <Catalogo, Integer> {
    boolean existsById(Integer id);
}
