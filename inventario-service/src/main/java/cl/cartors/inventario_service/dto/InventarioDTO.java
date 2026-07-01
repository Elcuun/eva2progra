package cl.cartors.inventario_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InventarioDTO {
    private Long idInventario;

    private Integer stock;

    private LocalDate fechaIngreso;

    private Long idProducto;

    private Long idSucursal;

    private ProductoDTO producto;

    private SucursalDTO sucursal;
}
