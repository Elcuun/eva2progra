package cl.cartors.boleta_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "boletas")
public class Boleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBoleta;

    @NotNull(message = "la fecha no puede estar vacia")
    private LocalDate fecha;

    @NotNull(message = "el total no puede estar vacio")
    private BigDecimal total;

    @NotNull(message = "el metodo de pago no puede estar vacio")
    @Size(max = 40)
    private String metodoPago;

    @NotNull(message = "el pedido no puede estar vacio")
    private Long idPedido;
}
