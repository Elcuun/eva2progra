package cl.cartors.envio_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "envios")
public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEnvio;

    @NotNull(message = "la direccion de entrega no puede estar vacia")
    @Size(max = 150)
    private String direccionEntrega;

    @NotNull(message = "la fecha de envio no puede estar vacia")
    private LocalDate fechaEnvio;

    @NotNull(message = "el estado no puede estar vacio")
    @Size(max = 40)
    private String estado;

    @NotNull(message = "el pedido no puede estar vacio")
    private Long idPedido;
}
