package org.example.lavanderia_proyecto;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.example.lavanderia_proyecto.dto.PrendaCrearDTO;
import org.example.lavanderia_proyecto.dto.PrendaDTO;
import org.example.lavanderia_proyecto.modelos.Prenda;
import org.example.lavanderia_proyecto.repositorios.PrendaRepositorio;
import org.example.lavanderia_proyecto.servicios.PrendaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class PrendaServiceTest {

    @Autowired
    private PrendaService service;

    @Autowired
    private PrendaRepositorio repository;

    @BeforeEach
    public void inicializarBaseDatos() throws Exception {
        Prenda prenda1 = new Prenda();
        prenda1.setNombre("Camisa");
        prenda1.setDescripcion("Algodón");

        repository.save(prenda1);

        PrendaCrearDTO dto1 = new PrendaCrearDTO();
        dto1.setNombre("Pantalón");
        dto1.setDescripcion("Seda");

        PrendaCrearDTO dto2 = new PrendaCrearDTO();
        dto2.setNombre("Chaqueta");
        dto2.setDescripcion("Cuero");

        service.guardar(dto1);
        service.guardar(dto2);
    }

    @Test
    @DisplayName("Crear prenda con datos incorrectos.")
    @Tag("Prenda")
    public void testCrearPrendaNegativo() {

        //GIVEN
        PrendaCrearDTO dto = new PrendaCrearDTO();
        dto.setNombre(" ");
        dto.setDescripcion(" ");

        //WHEN && THEN
        Exception exception = assertThrows(Exception.class, () -> service.guardar(dto));
    }

    @Test
    @DisplayName("Test 2 -> Crear prenda con datos correctos.")
    @Tag("Prenda")
    public void testCrearPrendaPositivo() throws Exception {

        //GIVEN
        PrendaCrearDTO dto = new PrendaCrearDTO();
        dto.setNombre("Camisa");
        dto.setDescripcion("Camisa de algodón");

        //WHEN
        Prenda p = service.guardar(dto);

        //THEN
        assertNotNull(p);
        assertEquals(dto.getNombre(), p.getNombre());
    }

    @Test
    @DisplayName("Test 3 -> Probar a buscar prenda inexistente.")
    public void testPrendaInexistente() throws Exception {

        //GIVEN

        //WHEN
        List<PrendaDTO> prendasEncontradas = service.buscar("Sombrero");

        //THEN
        assertTrue(prendasEncontradas.isEmpty());
    }

    @Test
    @DisplayName("Test 4 -> Probar a buscar prenda por Nombre")
    public void testBuscarPrendaNombre() {

        //GIVEN

        //WHEN
        List<PrendaDTO> prendasEncontradas = service.buscar("Pantalón");

        //THEN
        assertFalse(prendasEncontradas.isEmpty());
        assertEquals(1, prendasEncontradas.size());
        assertEquals("Pantalón", prendasEncontradas.get(0).getNombre());
    }

    @Test
    @DisplayName("Test 5 -> Crear prenda con nombre en blanco")
    public void testCrearPrendaSinNombreNegativo() {

        //GIVEN
        PrendaCrearDTO dto = new PrendaCrearDTO();
        dto.setNombre("");
        dto.setDescripcion("Descripción sin nombre");

        //WHEN
        //THEN
        assertThrows(ConstraintViolationException.class, () -> service.guardar(dto));
    }
}