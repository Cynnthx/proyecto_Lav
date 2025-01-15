package org.example.lavanderia_proyecto;

import org.example.lavanderia_proyecto.modelos.Catalogo;
import org.example.lavanderia_proyecto.modelos.Pedido;
import org.example.lavanderia_proyecto.modelos.PedidoPrendaCatalogo;
import org.example.lavanderia_proyecto.modelos.Prenda;
import org.example.lavanderia_proyecto.repositorios.CatalogoRepositorio;
import org.example.lavanderia_proyecto.repositorios.PedidoPrendaCatalogoRepositorio;
import org.example.lavanderia_proyecto.repositorios.PedidoRepositorio;
import org.example.lavanderia_proyecto.repositorios.PrendaRepositorio;
import org.example.lavanderia_proyecto.servicios.PedidoPrendaCatalogoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoPrendaCatalogoIntegrationTest {

    @InjectMocks
    private PedidoPrendaCatalogoService pedidoPrendaCatalogoService; //REAL

    @Mock
    private PedidoPrendaCatalogoRepositorio repository; //SIMULADO
    @Mock
    private PedidoRepositorio pedidoRepository; //SIMULADO
    @Mock
    private PrendaRepositorio prendaRepository; //SIMULADO
    @Mock
    private CatalogoRepositorio catalogoRepository; //SIMULADO

    private Pedido pedido;
    private Prenda prenda;
    private Catalogo catalogo;

    @BeforeEach
    public void setUp() {
        pedido = new Pedido();
        pedido.setDescripcion("Pedido de pruebaa");
        pedido.setFechaEntrega(LocalDate.now());
        pedido.setTotal(200.0);

        prenda = new Prenda();
        prenda.setNombre("Camisa");
        prenda.setDescripcion("Camisa de prueba");

        catalogo = new Catalogo();
        catalogo.setPrecioServPrenda(200.0);
    }

    @Test
    @DisplayName("Test de integración para guardar PedidoPrendaCatalogo con datos correctos")
    public void testGuardarPedidoPrendaCatalogo() {
        //iniciamos y guardamos las entidades relacionadas
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
        when(prendaRepository.save(any(Prenda.class))).thenReturn(prenda);
        when(catalogoRepository.save(any(Catalogo.class))).thenReturn(catalogo);

        pedido = pedidoRepository.save(pedido);
        prenda = prendaRepository.save(prenda);
        catalogo = catalogoRepository.save(catalogo);

        //Creamos y guardamos la instancia de Ppc
        PedidoPrendaCatalogo ppc = new PedidoPrendaCatalogo();
        ppc.setPrecio(100.0);
        ppc.setCantidad(2);
        ppc.setPedido(pedido);
        ppc.setPrenda(prenda);
        ppc.setCatalogo(catalogo);

        when(repository.save(any(PedidoPrendaCatalogo.class))).thenReturn(ppc);

        PedidoPrendaCatalogo savedPpc = pedidoPrendaCatalogoService.save(ppc);

        assertNotNull(savedPpc);
        assertEquals(100.0, savedPpc.getPrecio());
        assertEquals(2, savedPpc.getCantidad());
        assertEquals(pedido, savedPpc.getPedido());
        assertEquals(prenda, savedPpc.getPrenda());
        assertEquals(catalogo, savedPpc.getCatalogo());
        verify(repository).save(any(PedidoPrendaCatalogo.class));
    }

    @Test
    @DisplayName("Test de integración para buscar PedidoPrendaCatalogo por ID")
    public void testBuscarPedidoPrendaCatalogoPorId() {
        PedidoPrendaCatalogo ppc = new PedidoPrendaCatalogo();
        ppc.setPrecio(100.0);
        ppc.setCantidad(2);
        ppc.setPedido(pedido);
        ppc.setPrenda(prenda);
        ppc.setCatalogo(catalogo);

        when(repository.findById(anyInt())).thenReturn(Optional.of(ppc));

        PedidoPrendaCatalogo foundPpc = pedidoPrendaCatalogoService.getById(1);

        assertNotNull(foundPpc);
        assertEquals(ppc.getId(), foundPpc.getId());
        verify(repository).findById(1);
    }

    @Test
    @DisplayName("Test de integración para eliminar PedidoPrendaCatalogo")
    public void testEliminarPedidoPrendaCatalogo() {
        PedidoPrendaCatalogo ppc = new PedidoPrendaCatalogo();
        ppc.setPrecio(100.0);
        ppc.setCantidad(2);
        ppc.setPedido(pedido);
        ppc.setPrenda(prenda);
        ppc.setCatalogo(catalogo);

        doNothing().when(repository).deleteById(anyInt());

        pedidoPrendaCatalogoService.delete(1);

        verify(repository).deleteById(1);
    }
}