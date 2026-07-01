package cl.carstor.sucursal_service.repository;

import cl.carstor.sucursal_service.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
}
