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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase
public class PagosServiceTest {

    @Autowired
    private PagosService service;

    @Autowired
    private PagosRepositorio repository;
    @Autowired
    private ClientesRepositorio clientesRepositorio;
    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @BeforeEach
    public void inicializarDatos(){

        Cliente cliente = new Cliente();
        cliente.setNombre("Paula");
        cliente.setApellidos("Moreno");
        cliente.setDireccion("Calle París");
        cliente.setDni("12345678A");
        cliente.setTelefono(123456789);

        Pedido pedido = new Pedido();
        pedido.setClienteId(1);
        pedido.setFechaEntrega(LocalDate.now().plusDays(3)); // Fecha de entrega válida
        pedido.setTotal(100.0);
        pedidoRepositorio.save(pedido);

        cliente = clientesRepositorio.save(cliente);
        pedido = pedidoRepositorio.save(pedido);

        Pagos p = new Pagos();
        p.setPagado(true);
        p.setSaldoPendiente(50.0);
        p.setCliente(cliente);
        p.setPedido(pedido);

        repository.save(p);
    }

    @Test
    public void testFindAll(){

        //GIVEN

        //WHEN
        List<PagosDTO> pagos = service.getAll();

        //THEN
        assertEquals(1, pagos.size());
    }

    @Test
    public void testFindByIdNegativo() throws Exception {

        //GIVEN

        //WHEN
        //THEN
        Exception exception = assertThrows(Exception.class, ()-> service.getById(10));
        assertEquals("No existe ningún pago con el id indicado", exception.getMessage());

    }

    @Test
    public  void testGuardarCatalogo() throws Exception {

        //GIVEN
        PagosCrearDTO pagosDTO = new PagosCrearDTO();
        pagosDTO.setPagado(true);
        pagosDTO.setSaldoPendiente(50.0);
        pagosDTO.setClienteId(1);
        pagosDTO.setPedidoId(1);

        //WHEN
        Pagos pagoGuardado = service.guardar(pagosDTO);

        //THEN
        assertNotNull(pagoGuardado);
        assertNotNull(pagoGuardado.getId());

    }

    @Test
    public  void testGuardarCatalogoNegativo() throws Exception {

        //GIVEN
        Cliente cliente = clientesRepositorio.findAll().get(0);
        Pedido pedido = pedidoRepositorio.findAll().get(0);

        PagosCrearDTO pagosDTO = new PagosCrearDTO();
        pagosDTO.setPagado(true);
        pagosDTO.setSaldoPendiente(0.0);
        pagosDTO.setClienteId(cliente.getId());
        pagosDTO.setPedidoId(pedido.getId());

        //WHEN
        Exception exception = assertThrows(Exception.class, () -> service.guardar(pagosDTO));

        //THEN
        assertEquals("El pago debe ser mayor que 0", exception.getMessage());
    }
}
