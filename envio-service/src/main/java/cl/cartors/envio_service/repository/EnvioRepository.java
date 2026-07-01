package cl.cartors.envio_service.repository;

import cl.cartors.envio_service.model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface EnvioRepository extends JpaRepository<Envio, Long> {
    List<Envio> findByEstadoIgnoreCase(String estado);

    List<Envio> findByFechaEnvioBetween(LocalDate desde, LocalDate hasta);

    List<Envio> findByDireccionEntregaContainingIgnoreCase(String texto);

    List<Envio> findByIdPedido(Long idPedido);

    @Query("select e.estado, count(e) from Envio e group by e.estado")
    List<Object[]> resumenPorEstado();
}
