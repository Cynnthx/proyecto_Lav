package org.example.lavanderia_proyecto;


import org.example.lavanderia_proyecto.enumerados.TipoCatalogo;
import org.example.lavanderia_proyecto.enumerados.TipoPrenda;
import org.example.lavanderia_proyecto.modelos.Catalogo;
import org.example.lavanderia_proyecto.modelos.Cliente;
import org.example.lavanderia_proyecto.repositorios.CatalogoRepositorio;
import org.example.lavanderia_proyecto.repositorios.ClientesRepositorio;
import org.example.lavanderia_proyecto.servicios.CatalogoService;
import org.example.lavanderia_proyecto.servicios.ClientesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CatalogoServiceIntegration {

    @InjectMocks
    private CatalogoService service; //REAL

    @Mock
    private CatalogoRepositorio repository; //SIMULADO


    /**
     * T3ST D3 INT3GRACION POSITIVO
     */
    @Test
    public void testFindAllIntegracion(){
        //GIVEN
        List<Catalogo> catalogosPorDefectos = new ArrayList<>();
        Catalogo c1 = new Catalogo();
        c1.setTipoCatalogo(TipoCatalogo.LAVADO);

        Catalogo c2 = new Catalogo();
        c2.setTipoPrenda(TipoPrenda.CAMISA);

        catalogosPorDefectos.add(c1);
        catalogosPorDefectos.add(c2);

        Mockito.when(repository.findAll()).thenReturn(catalogosPorDefectos);

        //WHEN
        List<Catalogo> catalogos = service.getCatalogo();

        //THEN
        assertEquals(2, catalogos.size());
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

    /**
     * TEST DE INTEGR4CION NEG4TIVO
     */
    @Test
    public void testFindAllIntegracionNegativo() {
        // GIVEN
        List<Catalogo> catalogosPorDefectos = new ArrayList<>();

        Mockito.when(repository.findAll()).thenReturn(catalogosPorDefectos);

        // WHEN
        List<Catalogo> catalogos = service.getCatalogo();

        // THEN
        assertEquals(0, catalogos.size());
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

    /**
     * T3ST D3 INT3GRACION POSITIVO
     */
    @Test
    public void testBuscarPorIdIntegracion(){
        //GIVEN
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.ofNullable(null));

        //THEN && WHEN
       assertThrows(Exception.class, ()-> service.getById(3));
       Mockito.verify(repository, Mockito.times(1)).findById(3);
    }

    /**
     * TEST DE INTEGR4CION NEG4TIVO
     */
    @Test
    public void testBuscarPorIdIntegracionNegativo() {
        // GIVEN
        Mockito.when(repository.findById(3)).thenReturn(Optional.empty());

        // WHEN & THEN
        Exception exception = assertThrows(Exception.class, () -> service.getById(3));
        assertEquals("No existe ningún catálogo con el id indicado", exception.getMessage());
        Mockito.verify(repository, Mockito.times(1)).findById(3);
    }
}
