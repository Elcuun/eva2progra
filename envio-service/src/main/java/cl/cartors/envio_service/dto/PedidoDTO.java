package cl.cartors.envio_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PedidoDTO {
    private Long idpedido;
    private String estado;
    private LocalDate fechapedido;
    private Long idCliente;
    private Long idEmpleado;
}
