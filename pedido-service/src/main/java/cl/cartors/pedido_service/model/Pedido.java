package cl.cartors.pedido_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @NotNull(message = "la fecha no puede estar vacia")
    private LocalDate fechaPedido;

    @NotNull(message = "el estado no puede estar vacio")
    @Size(max = 20)
    private String estado;

    @NotNull(message = "el cliente no puede estar vacio")
    private Long idCliente;

    @NotNull(message = "el empleado no puede estar vacio")
    private Long idEmpleado;
}
