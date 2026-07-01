package cl.cartors.pedido_service.Repository;

import cl.cartors.pedido_service.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByEstadoIgnoreCase(String estado);
    List<Pedido> findByIdCliente(Long idCliente);
    List<Pedido> findByIdEmpleado(Long idEmpleado);
    List<Pedido> findByFechaPedidoBetween(LocalDate desde, LocalDate hasta);
}
