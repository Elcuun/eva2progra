package cl.carstore.empleado_service.clients;

import cl.carstore.empleado_service.dto.SucursalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient( name = "sucursal-service", path = "/api/v1/sucursales")
public interface SucursalFeingn {
    @GetMapping("/{id}")
    SucursalDTO obtenerSucursal(@PathVariable Long id);
}
