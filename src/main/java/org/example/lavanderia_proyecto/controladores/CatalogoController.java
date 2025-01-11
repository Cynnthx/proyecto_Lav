package org.example.lavanderia_proyecto.controladores;

import lombok.AllArgsConstructor;
import org.example.lavanderia_proyecto.dto.MensajeDTO;
import org.example.lavanderia_proyecto.enumerados.TipoCatalogo;
import org.example.lavanderia_proyecto.enumerados.TipoPrenda;
import org.example.lavanderia_proyecto.modelos.Catalogo;
import org.example.lavanderia_proyecto.servicios.CatalogoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogo")
@AllArgsConstructor
public class CatalogoController {

    private final CatalogoService catalogoService;

    @GetMapping("/listar")
    public List<Catalogo> listarCatalogo(){
        return catalogoService.getCatalogo();
    }

    @GetMapping("/servicios")
    public MensajeDTO getServicios(@RequestParam TipoCatalogo tc, @RequestParam TipoPrenda tp) throws Exception {
        return catalogoService.getServicios(tp, tc);


    }


    @DeleteMapping()
    public MensajeDTO eliminar(@RequestParam Integer id){
        return catalogoService.eliminarServicio(id);
    }
}