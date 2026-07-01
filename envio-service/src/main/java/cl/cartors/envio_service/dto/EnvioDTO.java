package cl.cartors.envio_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EnvioDTO {
    private Long idEnvio;
    private String direccionEntrega;
    private LocalDate fechaEnvio;
    private String estado;
    private Long idPedido;
    private PedidoDTO pedido;
}
