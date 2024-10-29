package org.example.lavanderia_proyecto.controladores;

import lombok.AllArgsConstructor;

import org.example.lavanderia_proyecto.dto.PedidoDTO;
import org.example.lavanderia_proyecto.modelos.Pedido;
import org.example.lavanderia_proyecto.servicios.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
@AllArgsConstructor
public class PedidoController {
    @Autowired
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
    public Double getImporteTotal(@PathVariable Integer id){
        return pedidoService.calcularImporte(id);
    }
//
//    @PostMapping("/crear")
//    public PedidoDTO crearPedido(@RequestBody PedidoDTO pedidoDTO){
//        return pedidoService.crearPedido(PedidoDTO);
//    }
//
//    @GetMapping("/pagado")
//    public MensajeDTO pedidoPagado(@RequestBody PagosDTO pagoDTO){
//        return pedidoService.procesarPago(pagosDTO);
//    }


}
