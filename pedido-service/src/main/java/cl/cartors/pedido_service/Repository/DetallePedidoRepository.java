package cl.cartors.pedido_service.Repository;

import cl.cartors.pedido_service.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    List<DetallePedido> findByIdPedido(Long idPedido);

    List<DetallePedido> findByIdProducto(Long idProducto);

    @Query("select coalesce(sum(d.subtotal), 0) from DetallePedido d where d.idPedido = :idPedido")
    BigDecimal totalPorPedido(@Param("idPedido") Long idPedido);

    @Query("select d.idProducto, sum(d.cantidad) from DetallePedido d group by d.idProducto")
    List<Object[]> productosMasVendidos();
}
