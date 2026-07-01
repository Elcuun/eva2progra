package cl.cartors.inventario_service.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private Long idProducto;
    private String marca;
    private String modelo;
    private String estado;


}
