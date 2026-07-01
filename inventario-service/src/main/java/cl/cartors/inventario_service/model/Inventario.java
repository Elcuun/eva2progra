package cl.cartors.inventario_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInventario;


    @NotNull(message = "el producto no puede estar vacio.")
    private Long idProducto;


    @NotNull(message = "sucursal  no puede estar vacio")
    private Long idSucursal;



    @NotNull(message = "el stock no puede estar vacio")
    private Integer stock;


    @NotNull(message = "la fecha de envio no puede estar vacia")
    private LocalDate fechaIngreso;
}


