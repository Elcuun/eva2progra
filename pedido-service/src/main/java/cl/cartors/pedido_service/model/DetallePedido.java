package cl.cartors.pedido_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;

    @NotNull(message = "el pedido no puede estar vacio")
    private Long idPedido;

    @NotNull(message = "el producto no puede estar vacio")
    private Long idProducto;

    @NotNull(message = "la cantidad no puede estar vacia")
    private Integer cantidad;

    @NotNull(message = "el subtotal no puede estar vacio")
    private BigDecimal subtotal;
}
