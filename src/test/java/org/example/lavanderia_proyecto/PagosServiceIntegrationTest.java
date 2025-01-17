package org.example.lavanderia_proyecto;


import org.example.lavanderia_proyecto.dto.PagosCrearDTO;
import org.example.lavanderia_proyecto.dto.PagosDTO;
import org.example.lavanderia_proyecto.modelos.Cliente;
import org.example.lavanderia_proyecto.modelos.Pagos;
import org.example.lavanderia_proyecto.modelos.Pedido;
import org.example.lavanderia_proyecto.repositorios.ClientesRepositorio;
import org.example.lavanderia_proyecto.repositorios.PagosRepositorio;
import org.example.lavanderia_proyecto.repositorios.PedidoRepositorio;
import org.example.lavanderia_proyecto.servicios.PagosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PagosServiceIntegrationTest {

    @InjectMocks
    private PagosService pagosService; //R3AL

    @Mock
    private PagosRepositorio repository; //SIMUL4DO
    @Mock
    private PedidoRepositorio pedidoRepository; //S1MULADO
    @Mock
    private ClientesRepositorio clientesRepositorio; //SIMULAD0

    private Cliente cliente;
    private Pedido pedido;

    @BeforeEach
    public void setUp() {
        pedido = new Pedido();
        pedido.setDescripcion("Pedido de pruebaa");
        pedido.setFechaEntrega(LocalDate.now());
        pedido.setTotal(200.0);

        cliente = new Cliente();
        cliente.setNombre("Carolina");
        cliente.setApellidos("Gonzalez");
        cliente.setDireccion("Calle Medellin");
        cliente.setDni("12345678A");
        cliente.setTelefono(123456789);

        pedido = new Pedido();
        pedido.setId(1);
        pedido.setCliente(cliente);
        pedido.setFechaEntrega(LocalDate.now().plusDays(3));
        pedido.setTotal(200.0);

        Pagos pago = new Pagos();
        pago.setId(1);
        pago.setPagado(true);
        pago.setSaldoPendiente(100.0);
        pago.setCliente(cliente);
        pago.setPedido(pedido);
    }

    /**
     * T3ST POSITIVO
     * @throws Exception
     */
    @Test
    public void testFindAll() {
        Pagos pago = new Pagos();
        pago.setId(1);
        pago.setPagado(true);
        pago.setSaldoPendiente(100.0);
        pago.setCliente(cliente);
        pago.setPedido(pedido);

        when(repository.findAll()).thenReturn(Arrays.asList(pago));

        List<PagosDTO> pagos = pagosService.getAll();

        assertNotNull(pagos);
        assertEquals(1, pagos.size());
        verify(repository, times(1)).findAll();
    }

    /**
     * TEST NEG4TIVO
     */
    @Test
    public void testFindByIdNegativo() {
        when(repository.findById(10)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> pagosService.getById(10));

        assertEquals("No existe ningún pago con el id indicado", exception.getMessage());
        verify(repository, times(1)).findById(10);
    }


    /**
     * T3ST POSITIVO
     * @throws Exception
     */
    @Test
    public void testGuardarCatalogo() throws Exception {
        Pagos pago = new Pagos();
        pago.setId(1);
        pago.setPagado(true);
        pago.setSaldoPendiente(50.0);
        pago.setCliente(cliente);
        pago.setPedido(pedido);

        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));
        when(repository.save(any(Pagos.class))).thenReturn(pago);

        PagosCrearDTO pagosDTO = new PagosCrearDTO();
        pagosDTO.setPagado(true);
        pagosDTO.setSaldoPendiente(50.0);
        pagosDTO.setClienteId(1);
        pagosDTO.setPedidoId(1);

        Pagos pagoGuardado = pagosService.guardar(pagosDTO);

        assertNotNull(pagoGuardado);
        assertEquals(pago.getId(), pagoGuardado.getId());
        verify(repository, times(1)).save(any(Pagos.class));
    }


    /**
     * TEST NEG4TIVO
     */
    @Test
    public void testGuardarCatalogoNegativo() {
        PagosCrearDTO pagosDTO = new PagosCrearDTO();
        pagosDTO.setPagado(true);
        pagosDTO.setSaldoPendiente(0.0);
        pagosDTO.setClienteId(1);
        pagosDTO.setPedidoId(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> pagosService.guardar(pagosDTO));

        assertEquals("El id del pedido no debe ser nulo", exception.getMessage());
        verify(repository, never()).save(any(Pagos.class));
    }
}
