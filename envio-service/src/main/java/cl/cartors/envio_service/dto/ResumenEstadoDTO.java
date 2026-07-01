package cl.cartors.envio_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResumenEstadoDTO {
    private String estado;
    private Long cantidad;
}
