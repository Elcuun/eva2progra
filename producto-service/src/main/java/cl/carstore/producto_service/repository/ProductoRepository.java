package cl.carstore.producto_service.repository;

import cl.carstore.producto_service.dto.ProductoDTO;
import cl.carstore.producto_service.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Producto findTopByOrderByPrecioDesc();
    List<Producto> findAllByColor(String color);

  List<Producto> findByMarca(String marca);
}
