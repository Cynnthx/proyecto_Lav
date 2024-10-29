package org.example.lavanderia_proyecto.servicios;

import lombok.RequiredArgsConstructor;
import org.example.lavanderia_proyecto.dto.MensajeDTO;
import org.example.lavanderia_proyecto.enumerados.TipoCatalogo;
import org.example.lavanderia_proyecto.enumerados.TipoPrenda;
import org.example.lavanderia_proyecto.modelos.Catalogo;
import org.example.lavanderia_proyecto.modelos.PedidoPrendaCatalogo;
import org.example.lavanderia_proyecto.repositorios.CatalogoRepositorio;
import org.example.lavanderia_proyecto.repositorios.PagosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogoService {

    private final PagosRepositorio pagosRepositorio;
    private CatalogoRepositorio catalogoRepositorio;
    @Autowired
    private PedidoPrendaCatalogoService ppcservice;

    @Autowired
    public CatalogoService(CatalogoRepositorio catalogoRepositorio, PagosRepositorio pagosRepositorio) {
        this.catalogoRepositorio = catalogoRepositorio;
        this.pagosRepositorio = pagosRepositorio;
    }

    public List<Catalogo> getCatalogo() {
        return catalogoRepositorio.findAll();
    }

    /**
     * segundo metodos Luis, devolver enumerados
     */

    public MensajeDTO getServicios(TipoPrenda tp, TipoCatalogo tc) {

        MensajeDTO mensajeDTO = new MensajeDTO();

        List<Catalogo> catalogos = catalogoRepositorio.findAll();
        for (Catalogo c : catalogos) {
            if (c.getTipoPrenda().equals(tp) && c.getTipoCatalogo().equals(tc)) {
                mensajeDTO.setMensaje("Servicio Disponible");
                return mensajeDTO;
            }
        }
        mensajeDTO.setMensaje("Servicio no Disponible");
        return mensajeDTO;

    }

    /**
     * ejercicio Luis eliminar servicio
     */
    //comprobar si el servicio existe
    //si servicio esta vinculado a un pedido cuyo pago es el estado no es pagado te devuelve un mensaje
    //de que no se ha podido eliminar

    //si no cumple lo anterior elimina tod0
    public MensajeDTO eliminarServicio(Integer id) {
        MensajeDTO mensaje = new MensajeDTO();
        Catalogo catalogo = catalogoRepositorio.findById(id).orElse(null);

        boolean relacion = pagosRepositorio.existsById(id);

        if (catalogo == null) {
            mensaje.setMensaje("El servicio que está buscando no existe.");
            return mensaje;
        } else if (relacion) {
            List<PedidoPrendaCatalogo> ppclist = ppcservice.getAll();
            ppclist.forEach(ppc -> {
                if (ppc.getCatalogo().getId() == catalogo.getId()){
            ppc.setCatalogo(null);
            }
                catalogoRepositorio.delete(catalogo);

            });
            mensaje.setMensaje("Servicio eliminado");
            return mensaje;
        }else {
            mensaje.setMensaje("No se ha podido eliminar pago pendiente");
            return mensaje;
        }
    }
}