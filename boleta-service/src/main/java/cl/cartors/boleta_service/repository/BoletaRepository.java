package cl.cartors.boleta_service.repository;

import cl.cartors.boleta_service.model.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BoletaRepository extends JpaRepository<Boleta, Long> {
    List<Boleta> findByMetodoPagoIgnoreCase(String metodoPago);

    List<Boleta> findByFechaBetween(LocalDate desde, LocalDate hasta);

    List<Boleta> findByTotalBetween(BigDecimal minimo, BigDecimal maximo);

    List<Boleta> findByIdPedido(Long idPedido);

    @Query("select b.metodoPago, count(b), coalesce(sum(b.total), 0) from Boleta b group by b.metodoPago")
    List<Object[]> resumenPorMetodoPago();

    @Query("select coalesce(sum(b.total), 0) from Boleta b where b.fecha between :desde and :hasta")
    BigDecimal totalVendidoEntreFechas(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);
}
