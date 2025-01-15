package org.example.lavanderia_proyecto;

import org.example.lavanderia_proyecto.enumerados.TipoCatalogo;
import org.example.lavanderia_proyecto.enumerados.TipoPrenda;
import org.example.lavanderia_proyecto.modelos.Catalogo;
import org.example.lavanderia_proyecto.repositorios.CatalogoRepositorio;
import org.example.lavanderia_proyecto.servicios.CatalogoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
public class CatalogoServiceTest {

    @Autowired
    private CatalogoService service;

    @Autowired
    private CatalogoRepositorio repository;


    @BeforeEach
    public void inicializarDatos(){

        Catalogo c1 = new Catalogo();
        c1.setTipoCatalogo(TipoCatalogo.LAVADO);
        Catalogo c2 = new Catalogo();
        c2.setTipoPrenda(TipoPrenda.CAMISA);

        repository.save(c1);
        repository.save(c2);

    }


    /**
     * TEST POSITIVO
     */
    @Test
    public void testFindAll(){

        //GIVEN

        //WHEN
        List<Catalogo> catalogos = service.getCatalogo();

        //THEN
        assertEquals(2, catalogos.size());
    }

    @Test
    public void testFindByIdNegativo() throws Exception {

        //GIVEN

        //WHEN
        //THEN
        Exception exception = assertThrows(Exception.class, ()-> service.getById(10));
        assertEquals("No existe ningún catálogo con el id indicado", exception.getMessage());

    }

    /**
     * TEST POSITIVO
     */
    @Test
    public  void testGuardarCatalogo() throws Exception {

        //GIVEN
        Catalogo catalogo = new Catalogo();
        catalogo.setTipoCatalogo(TipoCatalogo.LAVADO);
        catalogo.setTipoPrenda(TipoPrenda.CAMISA);
        catalogo.setPrecioServPrenda(100.0);

        //WHEN
        Catalogo catalogoGuardado = service.guardar(catalogo);

        //THEN
        assertNotNull(catalogoGuardado);
        assertNotNull(catalogoGuardado.getId());

    }


    @Test
    public  void testGuardarCatalogoNegativo() throws Exception {

        //GIVEN
        Catalogo catalogo = new Catalogo();
        catalogo.setTipoCatalogo(TipoCatalogo.LAVADO);
        catalogo.setTipoPrenda(TipoPrenda.CAMISA);
        catalogo.setPrecioServPrenda(0.0);
        //WHEN
        Exception exception = assertThrows(Exception.class, () -> service.guardar(catalogo));

        // THEN
        assertEquals("El precio del servicio debe ser mayor que 0", exception.getMessage());

    }
}
