package cl.cartors.boleta_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ResumenMetodoPagoDTO {
    private String metodoPago;
    private Long cantidad;
    private BigDecimal totalVendido;
}
