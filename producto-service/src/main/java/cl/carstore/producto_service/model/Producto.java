package cl.carstore.producto_service.model;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "La marca no puede estar vacia.")
    private String marca;
    @NotBlank(message = "El Modelo no puede estar vacio.")
    private String modelo;
    @NotNull(message = "El año no puede estar vacio.")
    @Positive(message = "El precio debe ser positivo")
    private Long anio;
    @NotBlank(message = "El color no puede estar vacio.")
    private String color;
    @NotNull(message = "El precio no puede estar vacio.")
    @Positive(message = "El precio debe ser positivo")
    private int precio;
    @NotBlank(message = "El estado no puede estar vacio.")
    private String estado;
}
