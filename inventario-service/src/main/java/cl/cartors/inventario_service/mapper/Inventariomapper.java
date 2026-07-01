package cl.cartors.inventario_service.mapper;

import cl.cartors.inventario_service.dto.InventarioDTO;
import cl.cartors.inventario_service.model.Inventario;
import org.springframework.stereotype.Component;

@Component
public class Inventariomapper {
    public InventarioDTO toDTO(Inventario inventario) {
        InventarioDTO dto = new InventarioDTO();
        dto.setIdInventario(inventario.getIdInventario());
        dto.setStock(inventario.getStock());
        dto.setFechaIngreso(inventario.getFechaIngreso());
        return dto;
    }


}
