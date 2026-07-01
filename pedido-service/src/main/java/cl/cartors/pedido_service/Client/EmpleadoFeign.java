package cl.cartors.pedido_service.Client;

import cl.cartors.pedido_service.DTO.EmpleadoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "empleado-service", path = "/api/v1/empleados")
public interface EmpleadoFeign {
    @GetMapping("/{id}")
    EmpleadoDTO obtenerEmpleado(@PathVariable Long id);
}
