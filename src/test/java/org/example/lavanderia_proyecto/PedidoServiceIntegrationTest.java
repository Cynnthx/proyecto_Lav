package org.example.lavanderia_proyecto;

import org.example.lavanderia_proyecto.dto.CrearPedidoDTO;
import org.example.lavanderia_proyecto.dto.PedidoDTO;
import org.example.lavanderia_proyecto.modelos.Cliente;
import org.example.lavanderia_proyecto.modelos.Pedido;
import org.example.lavanderia_proyecto.repositorios.ClientesRepositorio;
import org.example.lavanderia_proyecto.repositorios.PedidoRepositorio;
import org.example.lavanderia_proyecto.servicios.PedidoService;
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
public class PedidoServiceIntegrationTest {

    @InjectMocks
    private PedidoService pedidoService; //REAL

    @Mock
    private PedidoRepositorio repository; //SIMULADO

    @Mock
    private ClientesRepositorio clientesRepositorio;

    @Test
    @DisplayName("Test de integración para crear un pedido inválido")
    public void testCrearPedidoInvalidoIntegracion() {
        //GIVEN
        CrearPedidoDTO dto = new CrearPedidoDTO();
        dto.setIdCliente(1);
        dto.setTotal(-100.0);

        //WHEN && THEN
        when(clientesRepositorio.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        Exception exception = assertThrows(Exception.class, () -> pedidoService.guardar(dto));
        assertEquals("El total no puede estar vacío.", exception.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    @DisplayName("Test de Integración Pedido Service 2 - Test crear pedido válido")
    public void testCrearPedidoPositivoIntegracion() throws Exception {
        //GIVEN
        CrearPedidoDTO dto = new CrearPedidoDTO();
        dto.setDescripcion("Pedido de prueba");
        dto.setTotal(100.0);

        Pedido pedidoGuardado = new Pedido();
        pedidoGuardado.setDescripcion("Pedido de prueba");
        when(repository.save(any(Pedido.class))).thenReturn(pedidoGuardado);

        //WHEN
        Pedido pedido = pedidoService.guardar(dto);

        //THEN
        assertEquals(pedidoGuardado, pedido);
        verify(repository).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Test de Integración Pedido Service 3 - Test buscar pedido por ID")
    public void testBuscarPedidoPorIdIntegracion() {
        //GIVEN
        Cliente cliente = new Cliente();
        cliente.setNombre("Cliente de prueba");

        Pedido pedidoEsperado = new Pedido();
        pedidoEsperado.setId(1);
        pedidoEsperado.setDescripcion("Pedido de prueba");
        pedidoEsperado.setCliente(cliente);

        when(repository.findById(anyInt())).thenReturn(Optional.of(pedidoEsperado));

        //WHEN
        PedidoDTO pedidoObtenido = pedidoService.getById(1);

        //THEN
        assertNotNull(pedidoObtenido);
        assertEquals(PedidoDTO.convertirPedidoAPedidoDTO(pedidoEsperado), pedidoObtenido);
        verify(repository).findById(1);
    }

    @Test
    @DisplayName("Test de Integración Pedido Service 4 - Test buscar todos los pedidos")
    public void testBuscarTodosLosPedidosIntegracion() {
        //GIVEN
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(new Pedido());
        pedidos.add(new Pedido());

        when(repository.findAll()).thenReturn(pedidos);

        //WHEN
        List<Pedido> pedidosObtenidos = pedidoService.getAll();

        //THEN
        assertEquals(2, pedidosObtenidos.size());
        verify(repository).findAll();
    }
}