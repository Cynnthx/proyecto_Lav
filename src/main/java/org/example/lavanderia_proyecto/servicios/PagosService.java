package org.example.lavanderia_proyecto.servicios;


import lombok.AllArgsConstructor;
import org.example.lavanderia_proyecto.dto.PagosCrearDTO;
import org.example.lavanderia_proyecto.dto.PagosDTO;
import org.example.lavanderia_proyecto.modelos.Pagos;
import org.example.lavanderia_proyecto.repositorios.PagosRepositorio;
import org.example.lavanderia_proyecto.repositorios.PedidoRepositorio;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PagosService {
    private final PagosRepositorio pagosRepository;
    private final PedidoRepositorio pedidoRepository;

    /**
     * Obtener todos los pagos
     *
     * @return
     */
    public List<PagosDTO> getAll() {
        List<Pagos> pagos = pagosRepository.findAll();
        List<PagosDTO> pagosDTO = new ArrayList<>();
        for (Pagos p : pagos) {
            PagosDTO dto = new PagosDTO();
            dto.setPagado(p.getPagado());
            dto.setSaldoPendiente(p.getSaldoPendiente());
            pagosDTO.add(dto);
        }
        return pagosDTO;
    }

    /**
     * Crea un nuevo pago
     *
     * @param pagosCrearDTO
     * @return
     */
    public Pagos crearNuevo(PagosCrearDTO pagosCrearDTO) {
        Pagos entity = new Pagos();
        entity.setPagado(pagosCrearDTO.getPagado());
        entity.setSaldoPendiente(pagosCrearDTO.getSaldoPendiente());

        entity.setPedido(pedidoRepository.findById(pagosCrearDTO.getIdPedido()).orElse(null));

        return pagosRepository.save(entity);
    }

    /**
     * Edita un pago
     *
     * @param dto
     * @param id
     * @return
     */
    public Pagos editar(PagosCrearDTO dto, Integer id) {
        Pagos entity = pagosRepository.getReferenceById(id);
        entity.setPagado(dto.getPagado());
        entity.setSaldoPendiente(dto.getSaldoPendiente());
        entity.setPedido(pedidoRepository.findById(dto.getIdPedido()).orElse(null));
        return pagosRepository.save(entity);
    }

    /**
     * Busca un pago por id
     *
     * @param id
     * @return
     */
    public PagosDTO getById(Integer id) {
        Pagos pagos = pagosRepository.findById(id).orElse(null);
        if (pagos == null) {
            return null;
        }
        PagosDTO pagosDTO = new PagosDTO();
        pagosDTO.setPagado(pagos.getPagado());
        pagosDTO.setSaldoPendiente(pagos.getSaldoPendiente());
        return pagosDTO;
    }

    /**
     * Guarda un pago
     *
     * @param pagosCrearDTO
     * @return
     */
    public Pagos guardar(PagosCrearDTO pagosCrearDTO) {
        Pagos pagosGuardado = new Pagos();
        pagosGuardado.setPagado(pagosCrearDTO.getPagado());
        pagosGuardado.setSaldoPendiente(pagosCrearDTO.getSaldoPendiente());
        pagosGuardado.setPedido(pedidoRepository.findById(pagosCrearDTO.getIdPedido()).orElse(null));
        return pagosRepository.save(pagosGuardado);
    }

    /**
     * Elimina un pago por id
     *
     * @param id
     * @return
     */
    public String eliminar(Integer id) {
        Pagos pagos = pagosRepository.findById(id).orElse(null);
        if (pagos == null) {
            return "El pago con el id indicado no existe";
        }
        try {
            pagosRepository.deleteById(id);
            pagos = pagosRepository.findById(id).orElse(null);
            if (pagos != null) {
                return "No se ha podido eliminar el pago";
            } else {
                return "Pago eliminado correctamente";
            }
        } catch (Exception e) {
            return "No se ha podido eliminar el pago";
        }
    }
}



