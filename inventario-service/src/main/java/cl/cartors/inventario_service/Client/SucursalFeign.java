package cl.cartors.inventario_service.Client;

import cl.cartors.inventario_service.dto.SucursalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "sucursal-service", path = "/api/v1/sucursales")
public interface SucursalFeign {
    @GetMapping("/{id}")
    SucursalDTO obtenerSucursal(@PathVariable Long id);
}
