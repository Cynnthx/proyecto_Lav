package org.example.lavanderia_proyecto.servicios;

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

    public List<Pedido> getAll(){
        return pedidoRepositorio.findAll();
    }

    /**
     * busca un pedido a partir de su ID
     * @param id
     * @return
     */

    public PedidoDTO getById(Integer id){
        return PedidoDTO.convertirPedidoAPedidoDTO(pedidoRepositorio.findById(id).orElse(null));
    }

    /**
     * calcular importe
     *
     */

    public Double calcularImporte (Integer pedidoId){
        Pedido pedido = pedidoRepositorio.findById(pedidoId).orElse(null);
        if (pedido == null){
            return null;
        }else {
            Double total = 0.0;
            for (PedidoPrendaCatalogo ppc: pedido.getPedidoPrendaCatalogo()){
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
    public PedidoDTO crearNuevo(CrearPedidoDTO pedidoCrearDTO){
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
    public PedidoDTO editar (CrearPedidoDTO dto, Integer id){
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
     * Crea un pedido nuevo o modifica uno existente
     *
     * @param dto
     * @return
     */
    public Pedido guardar(CrearPedidoDTO dto) {
        Pedido pedidoGuardado = new Pedido();
        pedidoGuardado.setFecha(dto.getFechaEntrega());
        pedidoGuardado.setTotal(dto.getTotal());
        pedidoGuardado.setCliente(clientesRepositorio.findById(dto.getIdCliente()).orElse(null));


        return pedidoRepositorio.save(pedidoGuardado);
    }

    /**
     * Elimina un pedido
     *
     * @param id
     * @return
     */
    public String eliminar(Integer id) {
        String mensaje;
        PedidoDTO pedido = getById(id);
        if (pedido == null) {
            return "El pedido con el id indicado no existe.";
        }
        try {
            pedidoRepositorio.deleteById(id);
            pedido = getById(id);
            if (pedido != null) {
                mensaje = "No se ha podido eliminar el pedido.";
            } else {
                mensaje = "Pedido eliminado correctamente";
            }
        } catch (Exception e) {
            mensaje = "No se ha podido eliminar el pedido.";
        }
        return mensaje;


    }





    /**
     * Métod0 para procesar pago
     */

    public MensajeDTO procesarPago(PagosDTO pagosDTO){
        Pedido pedido = pedidoRepositorio.findById(pagosDTO.getIdPedido()).orElse(null);
        MensajeDTO mensaje = new MensajeDTO();

        Pagos pagos = pagosRepositorio.findByPedidoId(pedido.getId());
        if (pagos == null) {
            return new MensajeDTO("¡Ups! El pago no existe.");
        }

        //tengo que comprobar lo que queda por pagar
//
        Double total = pagos.getSaldoPendiente() - pagosDTO.getTotalPagado();

        // consulta primera si pedido esta pagado
       if (pagos.getSaldoPendiente()== 0){
            mensaje.setMensaje(
                    "El pedido ya está pagado.");
           return mensaje;


         //consulta si falta dinero
        } else if (total > 0) {
            mensaje.setMensaje("Pedido pagado falta: " + total);
            pagos.setSaldoPendiente(total.doubleValue());

            pagosRepositorio.save(pagos);
            return mensaje;

       }else {
            //el -1 es por si el cliente paga demás mostrarle la sobra
            mensaje.setMensaje("Pedido pagado y sobra: " + total*-1);
            pagos.setSaldoPendiente(0.0);
            pagosRepositorio.save(pagos);
            return mensaje;
       }
    }

    //comprobar si el servicio existe
    //si servicio esta vinculado a un pedido cuyo pago es el estado no es pagado te devuelve un mensaje
    //de que no se ha podido eliminar

    //si no cumple lo anterior elimina tod0

    /**
     * Elimina un pedido por Id
     * @param id
     */

    public void eliminarPorId(Integer id) {
        pedidoRepositorio.deleteById(id);
    }

    /**
     * Elimina un pedido
     * @param pedido
     */
    public void eliminar(Pedido pedido) {
        pedidoRepositorio.delete(pedido);

    }
}







