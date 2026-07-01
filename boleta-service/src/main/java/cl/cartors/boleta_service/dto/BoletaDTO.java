package cl.cartors.boleta_service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BoletaDTO {
    private Long idBoleta;
    private LocalDate fecha;
    private BigDecimal total;
    private String metodoPago;
    private Long idPedido;
    private PedidoDTO pedido;
}
