package org.example.lavanderia_proyecto.servicios;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.lavanderia_proyecto.dto.*;
import org.example.lavanderia_proyecto.modelos.*;
import org.example.lavanderia_proyecto.repositorios.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class PedidoService {
    private CatalogoRepositorio catalogoRepositorio;
    private PedidoPrendaCatalogoRepositorio pedidoPrendaCatalogoRepositorio;
    private PedidoRepositorio pedidoRepositorio;
    private ClientesRepositorio clientesRepositorio;
    private PrendaRepositorio prendaRepositorio;
    private PagosRepositorio pagosRepositorio;


    /**
     * Este extrae todos los pedidos la de base de datos
     */

    public List<Pedido> getAll() {
        return pedidoRepositorio.findAll();
    }

    /**
     * busca un pedido a partir de su ID
     *
     * @param id
     * @return
     */

    public PedidoDTO getById(Integer id) {
        return PedidoDTO.convertirPedidoAPedidoDTO(pedidoRepositorio.findById(id).orElse(null));
    }

    /**
     * método 3 totalGastado
     */

    public Double calcularImporte(Integer pedidoId) throws Exception {
        Pedido pedido = pedidoRepositorio.findById(pedidoId).orElse(null);

        /**
         * TotalGastadoNegativo
         */
        if (pedido == null) {
            throw new Exception("El pedido con el id indicado no existe.");
        } else {

            /**
             * TotalGastadoPositivo
             */
            Double total = 0.0;
            for (PedidoPrendaCatalogo ppc : pedido.getPedidoPrendaCatalogo()) {
                total += ppc.getPrecio() * ppc.getCantidad();
            }
            pedido.setTotal(total);
            pedidoRepositorio.save(pedido);
            return total;
        }
    }


    /**
     * Crea un pedido DTO ejercicio Luis
     */
    public PedidoDTO crearNuevo(@Valid CrearPedidoDTO pedidoCrearDTO) {
        Pedido entity = new Pedido();
        entity.setFecha(pedidoCrearDTO.getFechaEntrega());
        entity.setTotal(pedidoCrearDTO.getTotal());
        entity.setCliente(clientesRepositorio.findById(pedidoCrearDTO.getIdCliente()).orElse(null));
        entity.getPedidoPrendaCatalogo().add(pedidoPrendaCatalogoRepositorio.findById(pedidoCrearDTO.getIdPrenda()).orElse(null));

        pedidoRepositorio.save(entity);

        PedidoPrendaCatalogo p = pedidoPrendaCatalogoRepositorio.findById(pedidoCrearDTO.getIdPrenda()).orElse(null);
        p.setPedido(entity);

        pedidoPrendaCatalogoRepositorio.save(p);
        pedidoRepositorio.save(entity);


        return PedidoDTO.convertirPedidoAPedidoDTO(entity);

    }

    /**
     * Edita un pedido
     *
     * @param dto
     * @param id
     * @return
     */
    public PedidoDTO editar(CrearPedidoDTO dto, Integer id) {
        Pedido entity = pedidoRepositorio.getReferenceById(id);
        entity.setFecha(dto.getFechaEntrega());
        entity.setTotal(dto.getTotal());
        entity.setCliente(clientesRepositorio.findById(dto.getIdCliente()).orElse(null));

        Pedido pedidoGuardado = pedidoRepositorio.save(entity);
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setTotal(pedidoGuardado.getTotal());
        pedidoDTO.setFechaEntrega(pedidoGuardado.getFecha());


        return pedidoDTO;
    }

    /**
     * Crea un pedido nuevo o sale una excepcion
     *
     * @param dto
     * @return
     */
    public Pedido guardar(CrearPedidoDTO dto) throws Exception {
        Pedido pedidoGuardado = new Pedido();
        pedidoGuardado.setFecha(dto.getFechaEntrega());
        pedidoGuardado.setTotal(dto.getTotal());
        pedidoGuardado.setCliente(clientesRepositorio.findById(dto.getIdCliente()).orElse(null));

        /**
         * PedidoNegativo
         */
        if (dto.getTotal() <= 0) {
            throw new Exception("El total no puede estar vacío.");
        }

        /**
         * PedidoPositivo
         */
        return pedidoRepositorio.save(pedidoGuardado);
    }

    /**
     * Eliminar pedido hecho correctamente
     *
     * @param id
     * @return
     */
    public Boolean eliminar(Integer id) throws Exception {
        PedidoDTO pedido = getById(id);
        /**
         * PedidoNegativo
         */
        if (pedido == null) {
            throw new Exception("El pedido con el id indicado no existe.");
        }

        pedidoRepositorio.deleteById(id);

        pedido = getById(id);

        /**
         * PedidoPositivo
         */
        if (pedido != null) {
            throw new Exception("No se ha podido eliminar el pedido.");
        }

        return true;
    }





    /**
     * Métod0 para total pagado
     */

    public MensajeDTO procesarPago(PagosDTO pagosDTO) throws Exception {
        Pedido pedido = pedidoRepositorio.findById(pagosDTO.getIdPedido()).orElse(null);

        Pagos pagos = pagosRepositorio.findByPedidoId(pedido.getId());
        if (pagos == null) {
            throw new Exception("¡Ups! El pago no existe.");
        }

        Double total = pagos.getSaldoPendiente() - pagosDTO.getTotalPagado();

        /**
         * PagarPedidoNegativo
         */
        if (pagos.getSaldoPendiente() == 0) {
            throw new Exception( "El pedido ya está pagado.");




            /**
             * PagarPedidoPositivo
             */
            //consulta si falta dinero
        } else if (total > 0) {
            pagos.setSaldoPendiente(total.doubleValue());

            pagosRepositorio.save(pagos);
            return new MensajeDTO("Pedido pagado falta: " + total);

            /**
             * PagarPedidoPositivo
             */
        } else {
            //el -1 es por si el cliente paga demás mostrarle la sobra
            pagos.setSaldoPendiente(0.0);
            pagosRepositorio.save(pagos);
            return new MensajeDTO("Pedido pagado y sobra: " + total * -1);
        }
    }

}








