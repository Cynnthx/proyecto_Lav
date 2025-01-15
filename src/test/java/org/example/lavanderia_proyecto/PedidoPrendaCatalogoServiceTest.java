package org.example.lavanderia_proyecto;

import jakarta.transaction.Transactional;
import org.example.lavanderia_proyecto.modelos.PedidoPrendaCatalogo;
import org.example.lavanderia_proyecto.modelos.Pedido;
import org.example.lavanderia_proyecto.modelos.Prenda;
import org.example.lavanderia_proyecto.modelos.Catalogo;
import org.example.lavanderia_proyecto.repositorios.PedidoPrendaCatalogoRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class PedidoPrendaCatalogoServiceTest {

    @Autowired
    private PedidoPrendaCatalogoRepositorio repository;

    private Pedido pedido;
    private Prenda prenda;
    private Catalogo catalogo;

    @BeforeEach
    public void setUp() {
        pedido = new Pedido();
        pedido.setFechaEntrega(LocalDate.now());
        pedido.setTotal(100.0);
        prenda = new Prenda();
        catalogo = new Catalogo();
    }

    @Test
    @DisplayName("Test 1 -> Guardar PedidoPrendaCatalogo con datos correctos")
    @Tag("PedidoPrendaCatalogo")
    public void testGuardarPedidoPrendaCatalogo() {
        PedidoPrendaCatalogo ppc = new PedidoPrendaCatalogo();
        ppc.setPrecio(100.0);
        ppc.setCantidad(2);
        ppc.setPedido(pedido);
        ppc.setPrenda(prenda);
        ppc.setCatalogo(catalogo);

        PedidoPrendaCatalogo savedPpc = repository.save(ppc);

        assertNotNull(savedPpc);
        assertEquals(100.0, savedPpc.getPrecio());
        assertEquals(2, savedPpc.getCantidad());
        assertEquals(pedido, savedPpc.getPedido());
        assertEquals(prenda, savedPpc.getPrenda());
        assertEquals(catalogo, savedPpc.getCatalogo());
    }

    @Test
    @DisplayName("Test 2 -> Buscar PedidoPrendaCatalogo por ID")
    @Tag("PedidoPrendaCatalogo")
    public void testBuscarPedidoPrendaCatalogoPorId() {
        PedidoPrendaCatalogo ppc = new PedidoPrendaCatalogo();
        ppc.setPrecio(100.0);
        ppc.setCantidad(2);
        ppc.setPedido(pedido);
        ppc.setPrenda(prenda);
        ppc.setCatalogo(catalogo);

        PedidoPrendaCatalogo savedPpc = repository.save(ppc);
        PedidoPrendaCatalogo foundPpc = repository.findById(savedPpc.getId()).orElse(null);

        assertNotNull(foundPpc);
        assertEquals(savedPpc.getId(), foundPpc.getId());
    }

    @Test
    @DisplayName("Test 3 -> Eliminar PedidoPrendaCatalogo")
    @Tag("PedidoPrendaCatalogo")
    public void testEliminarPedidoPrendaCatalogo() {
        PedidoPrendaCatalogo ppc = new PedidoPrendaCatalogo();
        ppc.setPrecio(100.0);
        ppc.setCantidad(2);
        ppc.setPedido(pedido);
        ppc.setPrenda(prenda);
        ppc.setCatalogo(catalogo);

        PedidoPrendaCatalogo savedPpc = repository.save(ppc);
        repository.delete(savedPpc);
        PedidoPrendaCatalogo foundPpc = repository.findById(savedPpc.getId()).orElse(null);

        assertNull(foundPpc);
    }
}