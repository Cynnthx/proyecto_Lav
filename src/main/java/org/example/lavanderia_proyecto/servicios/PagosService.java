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

    public Pagos crearNuevo(PagosCrearDTO pagosCrearDTO) {
        Pagos entity = new Pagos();
        entity.setPagado(pagosCrearDTO.getPagado());
        entity.setSaldoPendiente(pagosCrearDTO.getSaldoPendiente());

        entity.setPedido(pedidoRepository.findById(pagosCrearDTO.getPedidoId()).orElse(null));

        return pagosRepository.save(entity);
    }

    public Pagos editar(PagosCrearDTO dto, Integer id) {
        Pagos entity = pagosRepository.getReferenceById(id);
        entity.setPagado(dto.getPagado());
        entity.setSaldoPendiente(dto.getSaldoPendiente());
        entity.setPedido(pedidoRepository.findById(dto.getPedidoId()).orElse(null));
        return pagosRepository.save(entity);
    }

    public PagosDTO getById(Integer id) throws Exception {
        Pagos pagos = pagosRepository.findById(id).orElse(null);
        if (pagos == null) {
            throw new Exception("No existe ningún pago con el id indicado");
        }
        PagosDTO pagosDTO = new PagosDTO();
        pagosDTO.setPagado(pagos.getPagado());
        pagosDTO.setSaldoPendiente(pagos.getSaldoPendiente());
        return pagosDTO;
    }

    public Pagos guardar(PagosCrearDTO pagosCrearDTO) throws Exception {
        if (pagosCrearDTO.getPedidoId() == null) {
            throw new IllegalArgumentException("El id del pedido no debe ser nulo");
        }
        Pagos pagosGuardado = new Pagos();
        pagosGuardado.setPagado(pagosCrearDTO.getPagado());
        pagosGuardado.setSaldoPendiente(pagosCrearDTO.getSaldoPendiente());
        pagosGuardado.setPedido(pedidoRepository.findById(pagosCrearDTO.getPedidoId()).orElse(null));
        return pagosRepository.save(pagosGuardado);
    }

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