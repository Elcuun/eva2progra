package cl.carstore.producto_service.dto;


import lombok.Data;

@Data
public class ProductoDTO {
    private String marca;

    private String modelo;

    private Long anio;

    private String color;

    private int precio;

    private String estado;
}
