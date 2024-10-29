package org.example.lavanderia_proyecto.servicios;

import lombok.AllArgsConstructor;
import org.example.lavanderia_proyecto.dto.MensajeDTO;
import org.example.lavanderia_proyecto.dto.PedidoDTO;
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

    }




//    /**
//     * Crea un pedido DTO ejercicio Luis
//     */
//
//    public Pedido crearPedido(PedidoDTO pedidoDTO) {
//        Cliente cliente = clientesRepositorio.findById(pedidoDTO.)
//                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
//
//        // Crear el pedido sin las prendas y servicios directamente asociados
//        Pedido pedido = new Pedido();
//        pedido.setClientes(cliente);
//        pedido.setTotalPrecio(PedidoDTO.getTotalPrecio());
//        pedido.setFechaEntrega(LocalDate.now());
//        Pedido pedidoGuardado = pedidoRepositorio.save(pedido);
//
//        // Crear las relaciones en PedidosPrendasCatalogo
//        for (PedidoPrendaCatalogoDTO detalle : crearPedidoDTO.getDetalles()) {
//            Prenda prenda = prendaRepositorio.findById(detalle.getId_prenda())
//                    .orElseThrow(() -> new RuntimeException("Prenda no encontrada"));
//
//            Catalogo catalogo = catalogoRepositorio.findById(detalle.getId_catalogo())
//                    .orElseThrow(() -> new RuntimeException("Catalogo no encontrado"));
//
//            PedidoPrendaCatalogo pedidoPrendaCatalogo = new PedidoPrendaCatalogo();
//            pedidoPrendaCatalogo.setId_pedidos(pedidoGuardado);
//            pedidoPrendaCatalogo.setId_prendas(prenda);
//            pedidoPrendaCatalogo.setId_catalogo(catalogo);
//            pedidoPrendaCatalogo.setPrecio(detalle.getPrecio());
//            pedidoPrendaCatalogo.setCantidad(detalle.getCantidad());
//
//            pedidoPrendaCatalogoRepositorio.save(pedidoPrendaCatalogo);
//        }
//
//        return pedidoGuardado;
//    }
//
//    /**
//     * Métod0 para procesar pago
//     */
//
//    public MensajeDTO procesarPago(PagosDTO pagosDTO){
//        Pedido pedido = pedidoRepositorio.findById(pagoDTO.getIdPedido()).orElse(null);
//        MensajeDTO mensaje = new MensajeDTO();
//        //hago consulta
//
//        Pagos pago = pagosRepositorio.findByPedidoId(pedido);
//        //tengo que comprobar lo que queda por pagar
//
//        Double total = pago.getCantidadDebida() - pagoDTO.getMontoPagado();
//
//        //consulta primera si pedido esta pagado
//        if (pago.getCantidadDebida()== 0){
//            mensaje.setMensaje("Pedido ya está pagado");
//            return mensaje;
//            //consulta si falta dinero
//        } else if (total > 0) {
//            mensaje.setMensaje("Pedido pagado falta: " + total);
//            pago.setCantidadDebida(total.floatValue());
//            //tengo que guardar variable pago para que se actualice la tabla
//            pagosRepositorio.save(pago);
//            return mensaje;
//        } else {
//            //el -1 es por si el cliente paga demás mostrarle la sobra
//            mensaje.setMensaje("Pedido pagado y sobra: " + total*-1);
//            pago.setCantidadDebida(0.0f);
//            pagosRepositorio.save(pago);
//            return mensaje;
//
//        }
//
//    }
//
//
//    //comprobar si el servicio existe
//    //si servicio esta vinculado a un pedido cuyo pago es el estado no es pagado te devuelve un mensaje
//    //de que no se ha podido eliminar
//
//    //si no cumple lo anterior elimina tod0
//
//
//    /**
//     * Elimina un pedido por Id
//     * @param id
//     */
//
//    public void eliminarPorId(Integer id){
//        pedidoRepositorio.deleteById(id);
//    }
//
//
//    /**
//     * Elimina un pedido
//     * @param pedido
//     */
//
//    public void eliminar(Pedido pedido){
//        pedidoRepositorio.delete(pedido);
//    }
//}
