package cl.carstore.empleado_service.dto;

import lombok.Data;

@Data
public class EmpleadoDTO {
    private String nombre;
    private String apellido;
    private SucursalDTO sucursal;


}
