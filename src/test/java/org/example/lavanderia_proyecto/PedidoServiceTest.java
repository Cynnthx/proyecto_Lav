package org.example.lavanderia_proyecto;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.example.lavanderia_proyecto.dto.CrearPedidoDTO;
import org.example.lavanderia_proyecto.dto.PedidoDTO;
import org.example.lavanderia_proyecto.modelos.Cliente;
import org.example.lavanderia_proyecto.modelos.Pedido;
import org.example.lavanderia_proyecto.repositorios.ClientesRepositorio;
import org.example.lavanderia_proyecto.repositorios.PedidoRepositorio;
import org.example.lavanderia_proyecto.servicios.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class PedidoServiceTest {

    @Autowired
    private PedidoService service;
    @Autowired
    private PedidoRepositorio repository;
    @Autowired
    private ClientesRepositorio repositorio;

    @BeforeEach
    public void inicializarDatos() throws Exception {
        Cliente cliente1 = new Cliente();
        cliente1.setNombre("Anna");
        cliente1.setApellidos("González");
        cliente1.setDireccion("Calle Mar");
        cliente1.setDni("33344477C");
        cliente1.setTelefono(111133311);
        cliente1 = repositorio.save(cliente1);

        assertNotNull(cliente1.getId(), "El cliente1 no se guardó correctamente en la base de datos");


        Cliente cliente2 = new Cliente();
        cliente2.setNombre("Mía");
        cliente2.setApellidos("Díaz");
        cliente2.setDireccion("Calle Montaña");
        cliente2.setDni("88833344G");
        cliente2.setTelefono(444033310);
        cliente2 = repositorio.save(cliente2);

        assertNotNull(cliente2.getId(), "El cliente2 no se guardó correctamente en la base de datos");

        CrearPedidoDTO dto1 = new CrearPedidoDTO();
        dto1.setFechaEntrega(LocalDate.parse("2021-12-12"));
        dto1.setTotal(100.0);
        dto1.setIdCliente(cliente1.getId());

        Pedido pedido1 = service.guardar(dto1);
        assertNotNull(pedido1, "El pedido1 no se guardó correctamente en la base de datos");


        CrearPedidoDTO dto2 = new CrearPedidoDTO();
        dto2.setFechaEntrega(LocalDate.parse("2021-12-13"));
        dto2.setTotal(200.0);
        dto2.setIdCliente(cliente2.getId());

        Pedido pedido2 = service.guardar(dto2);
        assertNotNull(pedido2, "El pedido2 no se guardó correctamente en la base de datos");

//        service.guardar(dto1);
//        service.guardar(dto2);

    }

    @Test
    @DisplayName("Crear pedido con datos incorrectos.")
    @Tag("Pedido")
    public void testCrearPedidoNegativo() {

        // GIVEN
        CrearPedidoDTO dto = new CrearPedidoDTO();
        dto.setFechaEntrega(null);
        dto.setTotal(-100.0);
        dto.setIdCliente(null);

        // WHEN && THEN
        Exception exception = assertThrows(Exception.class, () -> service.guardar(dto));
    }

    @Test
    @DisplayName("Test 2 -> Crear pedido con datos correctos.")
    @Tag("Pedido")
    public void testCrearPedidoPositivo() throws Exception {

        // GIVEN
        CrearPedidoDTO dto = new CrearPedidoDTO();
        dto.setFechaEntrega(LocalDate.parse("2021-12-12"));
        dto.setTotal(100.0);
        dto.setIdCliente(1);

        // WHEN
        Pedido p = service.guardar(dto);

        // THEN
        assertNotNull(p);
        assertEquals(dto.getFechaEntrega(), p.getFechaEntrega());
        assertEquals(dto.getTotal(), p.getTotal());
        assertEquals(dto.getIdCliente(), p.getCliente().getId());
    }

    @Test
    @DisplayName("Test 3 -> Probar a buscar pedido inexistente.")
    public void testPedidoInexistente() throws Exception {

        // GIVEN

        // WHEN
        List<PedidoDTO> pedidosEncontrados = service.buscar("2019-12-12");

        // THEN
        assertTrue(pedidosEncontrados.isEmpty());
    }

    @Test
    @DisplayName("Test 4 -> Probar a buscar pedido por Fecha de Entrega")
    public void testBuscarPedidoFechaEntrega() {

        // GIVEN

        // WHEN
        List<PedidoDTO> pedidosEncontrados = service.buscar("2021-12-12");

        // THEN
        assertFalse(pedidosEncontrados.isEmpty());
        assertEquals(1, pedidosEncontrados.size());
        assertEquals(LocalDate.parse("2021-12-12"), pedidosEncontrados.get(0).getFechaEntrega());
    }

    @Test
    @DisplayName("Test 5 -> Crear pedido con total negativo")
    public void testCrearPedidoConTotalNegativo() {

        // GIVEN
        CrearPedidoDTO dto = new CrearPedidoDTO();
        dto.setFechaEntrega(LocalDate.parse("2021-12-12"));
        dto.setTotal(-100.0);
        dto.setIdCliente(1);

        // WHEN && THEN
        assertThrows(Exception.class, () -> service.guardar(dto));
    }
}
