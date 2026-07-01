package cl.cartors.pedido_service.Controller;

import cl.cartors.pedido_service.Service.DetallePedidoService;
import cl.cartors.pedido_service.model.DetallePedido;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/detallepedidos")
public class DetallePedidoController {
    @Autowired
    private DetallePedidoService detallePedidoService;

    @PostMapping("/{idPedido}/detalles")
    public ResponseEntity<?> registrarDetalle(@PathVariable Long idPedido, @Valid @RequestBody DetallePedido detallePedido) {
        detallePedido.setIdPedido(idPedido);
        return new ResponseEntity<>(detallePedidoService.save(detallePedido), HttpStatus.CREATED);
    }

    @GetMapping("/{idPedido}/detalles")
    public ResponseEntity<?> listarPorPedido(@PathVariable Long idPedido) {
        return ResponseEntity.ok(detallePedidoService.findByPedido(idPedido));
    }

    @GetMapping("/detalles/reportes/producto/{idProducto}")
    public ResponseEntity<?> listarPorProducto(@PathVariable Long idProducto) {
        return ResponseEntity.ok(detallePedidoService.findByProducto(idProducto));
    }

    @GetMapping("/detalles/reportes/total-pedido/{idPedido}")
    public ResponseEntity<?> totalPorPedido(@PathVariable Long idPedido) {
        return ResponseEntity.ok(detallePedidoService.totalPorPedido(idPedido));
    }

    @GetMapping("/detalles/reportes/productos-mas-vendidos")
    public ResponseEntity<?> productosMasVendidos() {
        return ResponseEntity.ok(detallePedidoService.productosMasVendidos());
    }
}
