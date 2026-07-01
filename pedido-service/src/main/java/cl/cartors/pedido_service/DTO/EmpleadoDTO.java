package cl.cartors.pedido_service.DTO;

import lombok.Data;

@Data
public class EmpleadoDTO {
    private String nombre;
    private String apellido;
    private SucursalDTO sucursal;
}
