package org.example.lavanderia_proyecto;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.example.lavanderia_proyecto.dto.ClientesCrearDTO;
import org.example.lavanderia_proyecto.dto.ClientesDTO;
import org.example.lavanderia_proyecto.modelos.Cliente;
import org.example.lavanderia_proyecto.repositorios.ClientesRepositorio;
import org.example.lavanderia_proyecto.servicios.ClientesService;
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
public class ClienteServiceTest {

    @Autowired
    private ClientesService service;

    @Autowired
    private ClientesRepositorio repository;

    @BeforeEach
    public void inicializarBaseDatos() throws Exception {
        Cliente dto = new Cliente();
        dto.setNombre("Adán");
        dto.setApellidos("Castel");
        dto.setDireccion("Calle Amore");
        dto.setDni("13344678A");
        dto.setTelefono(777443310);

        repository.save(dto);

        ClientesCrearDTO dto1 = new ClientesCrearDTO();
        dto1.setNombre("Anna");
        dto1.setApellidos("González");
        dto1.setDireccion("Calle Mar");
        dto1.setDni("33344477C");
        dto1.setTelefono(111133311);

        ClientesCrearDTO dto2 = new ClientesCrearDTO();
        dto2.setNombre("Mía");
        dto2.setApellidos("Díaz");
        dto2.setDireccion("Calle Montaña");
        dto2.setDni("88833344G");
        dto2.setTelefono(444033310);

        service.guardar(dto1);
        service.guardar(dto2);

    }

    @Test
    @DisplayName("Crear cliente con datos incorrectos.")
    @Tag("Cliente")
    public void testCrearClienteNegativo() {

        //GIVEN
        ClientesCrearDTO dto = new ClientesCrearDTO();
        dto.setNombre(" ");
        dto.setApellidos(" ");
        dto.setDireccion(" ");
        dto.setDni(" ");
        dto.setTelefono(0);

        //WHEN && THEN
        Exception exception = assertThrows(Exception.class, () -> service.guardar(dto));

    }


    @Test
    @DisplayName("Test 2 -> Crear cliente con datos correctos.")
    @Tag("Cliente")
    public void testCrearClientePositivo() throws Exception {

        //GIVEN
        ClientesCrearDTO dto = new ClientesCrearDTO();
        dto.setNombre("Adán");
        dto.setApellidos("Castel");
        dto.setDireccion("Calle Amore");
        dto.setDni("13344678A");
        dto.setTelefono(777443310);

        //WHEN
        Cliente c =  service.guardar(dto);

        //THEN
        assertNotNull(c);
        assertEquals(dto.getNombre(), c.getNombre());

    }

    @Test
    @DisplayName("Test 3 -> Probar a buscar cliente inexistente.")
    public void testClienteInexistente() throws Exception {

        //GIVEN

        //WHEN
        List<ClientesDTO> clientesEncontrados = service.buscar("Benito");

        //THEN
        assertTrue(clientesEncontrados.isEmpty());

    }

    @Test
    @DisplayName("Test 4 -> Probar a buscar cliente por Nombre")
    public void testBuscarClienteNombre(){

        //GIVEN

        //WHEN
        List<ClientesDTO> clientesEncontrados = service.buscar("Anna");


        //THEN
        assertFalse(clientesEncontrados.isEmpty());
        assertEquals(1, clientesEncontrados.size());
        assertEquals("Anna", clientesEncontrados.get(0).getNombre());
    }


    @Test
    @DisplayName("Test 5 -> Crear cliente con apellido en blanco")
    public void testCrearClienteSinNombreNegativo() {

        //GIVEN
        ClientesCrearDTO dto = new ClientesCrearDTO();
        dto.setNombre("Cynnthi");
        dto.setApellidos("");
        dto.setDireccion("Calle Fresas");
        dto.setDni("33344347C");
        dto.setTelefono(777773330);

        //WHEN
        //THEN
        assertThrows(ConstraintViolationException.class, () -> service.guardar(dto));

    }
}
