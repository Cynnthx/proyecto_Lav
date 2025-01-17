package org.example.lavanderia_proyecto;

import org.example.lavanderia_proyecto.dto.PrendaCrearDTO;
import org.example.lavanderia_proyecto.dto.PrendaDTO;
import org.example.lavanderia_proyecto.modelos.Prenda;
import org.example.lavanderia_proyecto.repositorios.PrendaRepositorio;
import org.example.lavanderia_proyecto.servicios.PrendaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrendaServiceIntegrationTest {

    @InjectMocks
    private PrendaService prendaService; //REAL

    @Mock
    private PrendaRepositorio repository; //SIMULADO

    @Test
    @DisplayName("Test de integración para crear una prenda inválida")
    public void testCrearPrendaInvalidaIntegracion() {
        //GIVEN
        PrendaCrearDTO prendaCrearDTO = new PrendaCrearDTO();
        prendaCrearDTO.setNombre("");
        prendaCrearDTO.setDescripcion("");

        //WHEN && THEN
        Exception exception = assertThrows(Exception.class, () -> prendaService.guardar(prendaCrearDTO));
        assertEquals("El nombre y la descripción no pueden estar en blanco", exception.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    @DisplayName("Test de Integración Prenda Service 2 - Test crear prenda válida")
    public void testCrearPrendaPositivaIntegracion() throws Exception {
        //GIVEN
        PrendaCrearDTO prendaCrearDTO = new PrendaCrearDTO();
        prendaCrearDTO.setNombre("Prenda de prueba");
        prendaCrearDTO.setDescripcion("Descripción de prueba");

        Prenda prendaGuardada = new Prenda();
        prendaGuardada.setNombre("Prenda de prueba");
        prendaGuardada.setDescripcion("Descripción de prueba");
        when(repository.save(any(Prenda.class))).thenReturn(prendaGuardada);

        //WHEN
        Prenda prendaResult = prendaService.guardar(prendaCrearDTO);

        //THEN
        assertEquals(prendaGuardada, prendaResult);
        verify(repository).save(any(Prenda.class));
    }

    @Test
    @DisplayName("Test de Integración Prenda Service 3 - Test buscar prenda por ID")
    public void testBuscarPrendaPorIdIntegracion() {
        //GIVEN
        Prenda prendaEsperada = new Prenda();
        prendaEsperada.setId(1);
        prendaEsperada.setNombre("Prenda de prueba");

        when(repository.findById(anyInt())).thenReturn(Optional.of(prendaEsperada));

        //WHEN
        Prenda prendaObtenida = prendaService.buscarPorId(1);

        //THEN
        assertNotNull(prendaObtenida);
        assertEquals(prendaEsperada, prendaObtenida);
        verify(repository).findById(1);
    }

    @Test
    @DisplayName("Test Negativo - Buscar prenda por ID inexistente")
    public void testBuscarPrendaPorIdInexistenteIntegracion() {
        // GIVEN
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        // WHEN
        Prenda prendaObtenida = prendaService.buscarPorId(444);

        // THEN
        assertNull(prendaObtenida, "La prenda obtenida debería ser null para un ID inexistente");
        verify(repository).findById(444);
    }


    @Test
    @DisplayName("Test de Integración Prenda Service 4 - Test buscar todas las prendas")
    public void testBuscarTodasLasPrendasIntegracion() {
        //GIVEN
        List<Prenda> prendas = new ArrayList<>();
        prendas.add(new Prenda());
        prendas.add(new Prenda());

        when(repository.findByNombre(anyString())).thenReturn(prendas);

        //WHEN
        List<PrendaDTO> prendasObtenidas = prendaService.buscar("camiseta");

        //THEN
        assertEquals(2, prendasObtenidas.size());
        verify(repository).findByNombre("camiseta");
    }

    @Test
    @DisplayName("Test Negativo - Buscar prendas con nombre inexistente")
    public void testBuscarTodasLasPrendasNombreInexistenteIntegracion() {
        // GIVEN
        when(repository.findByNombre(anyString())).thenReturn(new ArrayList<>());

        // WHEN
        List<PrendaDTO> prendasObtenidas = prendaService.buscar("Ed Sheeran");

        // THEN
        assertTrue(prendasObtenidas.isEmpty(), "La lista de prendas debería estar vacía para Ed Sheeran");
        verify(repository).findByNombre("Ed Sheeran");
    }

}