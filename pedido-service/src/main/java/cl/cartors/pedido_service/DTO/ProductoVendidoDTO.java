package cl.cartors.pedido_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoVendidoDTO {
    private Long idProducto;
    private Long cantidadVendida;
}
