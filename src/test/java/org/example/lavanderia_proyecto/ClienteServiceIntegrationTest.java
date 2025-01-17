package org.example.lavanderia_proyecto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.example.lavanderia_proyecto.dto.ClientesCrearDTO;
import org.example.lavanderia_proyecto.dto.ClientesDTO;
import org.example.lavanderia_proyecto.mappers.ClienteMapper;
import org.example.lavanderia_proyecto.modelos.Cliente;
import org.example.lavanderia_proyecto.repositorios.ClientesRepositorio;
import org.example.lavanderia_proyecto.servicios.ClientesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceIntegrationTest {

    @InjectMocks
    private ClientesService clienteService; //REAL

    @Mock
    private ClientesRepositorio repository; //SIMULADO
    @Mock
    private ClienteMapper clienteMapper;


    @BeforeEach
    void setUp() {
//        // Crear una instancia real del validador
//        validator = Validation.buildDefaultValidatorFactory().getValidator();
//
//        // Usar la instancia real con Mockito
//        ReflectionTestUtils.setField(clienteService, "validator", validator);
    }


    @Test
    @DisplayName("Test de Integración Cliente Service 1 - Test crear perfil inválido.")
    public void testCrearClienteInvalidoIntegracion() {
        //GIVEN
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        ClientesCrearDTO dto = new ClientesCrearDTO();
        dto.setNombre("");
        dto.setApellidos("");
        dto.setDireccion("Calle París");
        dto.setDni("12345678A");
        dto.setTelefono(123456789);

        Set<ConstraintViolation<ClientesCrearDTO>> violations = validator.validate(dto);

        //WHEN && THEN
        assertEquals(2, violations.size());
        verifyNoInteractions(repository);
    }

    @Test
    @DisplayName("Test de Integración Cliente Service 2 - Test crear perfil válido")
    public void testCrearClientePositivoIntegracion() throws Exception {
        //GIVEN
        ClientesCrearDTO dto = new ClientesCrearDTO();
        dto.setNombre("Paula");
        dto.setApellidos("Moreno");
        dto.setDireccion("Calle París");
        dto.setDni("12345678A");
        dto.setTelefono(123456789);

        Cliente clienteGuardado = new Cliente();
        clienteGuardado.setNombre("Paula");
        when(repository.save(any(Cliente.class))).thenReturn(clienteGuardado);

        //WHEN
        Cliente cliente = clienteService.guardar(dto);

        //THEN
        assertEquals(clienteGuardado, cliente);
        verify(repository).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Test de Integración Cliente Service 3 - Test crear cliente por nombre positivo")
    public void testBuscarClientesConNombrePositivoIntegracion() {
        //GIVEN
        List<Cliente> clientes = new ArrayList<>();

        Cliente clienteEsperado1 = new Cliente();
        clienteEsperado1.setId(1);
        clienteEsperado1.setNombre("Paula");
        Cliente clienteEsperado2 = new Cliente();
        clienteEsperado2.setId(2);
        clienteEsperado2.setNombre("Antonia");

        clientes.add(clienteEsperado1);
        clientes.add(clienteEsperado2);
        when(repository.buscar(any(String.class))).thenReturn(clientes);
        when(clienteMapper.toDTO(any(List.class))).thenReturn(List.of(new ClientesDTO(), new ClientesDTO()));

        //WHEN
        List<ClientesDTO> clientesObtenidos = clienteService.buscar("Paula");
        //THEN
        assertEquals(2, clientesObtenidos.size());
        verify(repository).buscar(any(String.class));
        verify(clienteMapper).toDTO(any(List.class));

    }

    @Test
    @DisplayName("Test de Integración Cliente Service 4 - Test buscar cliente por nombre negativo")
    public void testBuscarClientesConNombreNegativoIntegracion() {
        // GIVEN
        List<Cliente> clientes = new ArrayList<>();

        when(repository.buscar("NombreNoExistente")).thenReturn(clientes);
        when(clienteMapper.toDTO(any(List.class))).thenReturn(new ArrayList<>());

        // WHEN
        List<ClientesDTO> clientesObtenidos = clienteService.buscar("NombreNoExistente");

        // THEN
        assertEquals(0, clientesObtenidos.size());
        verify(repository).buscar("NombreNoExistente");
        verify(clienteMapper).toDTO(any(List.class));
    }
}