package cl.carstore.empleado_service.repository;

import cl.carstore.empleado_service.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository  extends JpaRepository<Empleado, Long> {
}
