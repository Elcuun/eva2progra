package cl.cartors.boleta_service.client;

import cl.cartors.boleta_service.dto.PedidoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "pedido-service", path = "/api/v1/pedidos")
public interface PedidoFeign {
    @GetMapping("/{id}")
    PedidoDTO obtenerPedido(@PathVariable("id") Long id);
}
