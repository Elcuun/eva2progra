package cl.cartors.inventario_service.repository;

import cl.cartors.inventario_service.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    List<Inventario> findByIdProducto(Integer idProducto);
    List<Inventario> findByIdSucursal(Integer idSucursal);
    List<Inventario> findByStockLessThanEqual(Integer stock);
    List<Inventario> findByFechaIngresoBetween(LocalDate desde, LocalDate hasta);
}
