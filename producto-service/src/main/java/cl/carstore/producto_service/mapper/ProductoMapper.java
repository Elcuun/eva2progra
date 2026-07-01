package cl.carstore.producto_service.mapper;

import cl.carstore.producto_service.dto.ProductoDTO;
import cl.carstore.producto_service.model.Producto;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ProductoMapper {
    public ProductoDTO toDTO(Producto producto){
        if(producto == null) return  null;
        ProductoDTO dto = new ProductoDTO();
        dto.setMarca(producto.getMarca());
        dto.setModelo(producto.getModelo());
        dto.setAnio(producto.getAnio());
        dto.setColor(producto.getColor());
        dto.setPrecio(producto.getPrecio());
        dto.setEstado(producto.getEstado());

        return dto;
    }

    public List<ProductoDTO> toListDTO(List<Producto> listado){

        return listado.stream()
                .map(this::toDTO)
                .toList();
    }
}
