package org.example.lavanderia_proyecto.controladores;

import lombok.AllArgsConstructor;

import org.example.lavanderia_proyecto.dto.CrearPedidoDTO;
import org.example.lavanderia_proyecto.dto.MensajeDTO;
import org.example.lavanderia_proyecto.dto.PagosDTO;
import org.example.lavanderia_proyecto.dto.PedidoDTO;
import org.example.lavanderia_proyecto.modelos.Pedido;
import org.example.lavanderia_proyecto.servicios.PedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
@AllArgsConstructor
public class PedidoController {

    private PedidoService pedidoService;

    @GetMapping("/total")
    public List<Pedido> getAllPedidos(){
        List<Pedido> pedidos = pedidoService.getAll();
        return pedidos;
    }

    @GetMapping("/get")
    public PedidoDTO getPedidoById(@RequestParam("id") Integer id){
        return pedidoService.getById(id);
    }

    @GetMapping("/get/{id}")
    public PedidoDTO getPedidoByIdPath(@PathVariable Integer id){
        return pedidoService.getById(id);
    }

    @GetMapping("/importe/{id}")
    public Double getImporteTotal(@PathVariable Integer id) throws Exception {
        return pedidoService.calcularImporte(id);
    }

    @PostMapping("/crear")
    public PedidoDTO crearPedido(@RequestBody CrearPedidoDTO pedido){
        return pedidoService.crearNuevo(pedido);
    }

    @PutMapping("/editarpedido/{id}")
    public PedidoDTO crear(@RequestBody CrearPedidoDTO crearPedidoDTO,
                           @PathVariable Integer id){
        return pedidoService.editar(crearPedidoDTO, id);
    }

    @PostMapping()
    public Pedido guardar(@RequestBody CrearPedidoDTO pedido) throws Exception {
        Pedido pedidoGuardado = pedidoService.guardar(pedido);
        return pedidoGuardado;
    }

    @DeleteMapping
    public Boolean eliminar(@RequestParam Integer id) throws Exception {
        return pedidoService.eliminar(id);
    }


    @GetMapping("/pagado")
    public MensajeDTO pedidoPagado(@RequestBody PagosDTO pagosDTO) throws Exception {
        return pedidoService.procesarPago(pagosDTO);
    }


}
