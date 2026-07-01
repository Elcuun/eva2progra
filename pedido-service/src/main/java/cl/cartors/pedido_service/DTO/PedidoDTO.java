package cl.cartors.pedido_service.DTO;

import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
public class PedidoDTO {
    private Long idpedido;
    private String estado;
    private LocalDate fechapedido;
    private ClienteDTO cliente;
    private EmpleadoDTO empleado;
}
