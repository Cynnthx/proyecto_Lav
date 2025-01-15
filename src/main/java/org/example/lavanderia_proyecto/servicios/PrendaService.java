package org.example.lavanderia_proyecto.servicios;

import jakarta.validation.ConstraintViolationException;
import org.example.lavanderia_proyecto.dto.PrendaCrearDTO;
import org.example.lavanderia_proyecto.dto.PrendaDTO;
import org.example.lavanderia_proyecto.modelos.Prenda;
import org.example.lavanderia_proyecto.repositorios.PrendaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrendaService {

    @Autowired
    private PrendaRepositorio repository;

    public Prenda guardar(PrendaCrearDTO dto) {
        if (dto.getNombre().isBlank() || dto.getDescripcion().isBlank()) {
            throw new ConstraintViolationException("El nombre y la descripción no pueden estar en blanco", null);
        }

        Prenda prenda = new Prenda();
        prenda.setNombre(dto.getNombre());
        prenda.setDescripcion(dto.getDescripcion());

        return repository.save(prenda);
    }

    public List<PrendaDTO> buscar(String nombre) {
        List<Prenda> prendas = repository.findByNombre(nombre);
        return prendas.stream()
                .map(prenda -> {
                    PrendaDTO dto = new PrendaDTO();
                    dto.setId(prenda.getId());
                    dto.setNombre(prenda.getNombre());
                    dto.setDescripcion(prenda.getDescripcion());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public Prenda buscarPorId(Integer id) {
        Optional<Prenda> prenda = repository.findById(id);
        return prenda.orElse(null);
    }
}