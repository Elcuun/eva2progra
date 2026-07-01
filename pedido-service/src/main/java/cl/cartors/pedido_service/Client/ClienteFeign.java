package cl.cartors.pedido_service.Client;

import cl.cartors.pedido_service.DTO.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cliente-service", path = "/api/v1/clientes")
public interface ClienteFeign {
    @GetMapping("/{id}")
    ClienteDTO obtenerCliente(@PathVariable Long id);

}
