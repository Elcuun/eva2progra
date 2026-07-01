package cl.carstore.empleado_service.mapper;

import cl.carstore.empleado_service.dto.EmpleadoDTO;
import cl.carstore.empleado_service.model.Empleado;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmpleadoMapper {
    public EmpleadoDTO toDTO(Empleado empleado) {
        if (empleado == null) return null;
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setNombre(empleado.getNombre());
        dto.setApellido(empleado.getApellido());
        return dto;

    }

    public List<EmpleadoDTO> toListDTO(List<Empleado> listado){

        return listado.stream()
                .map(this::toDTO)
                .toList();
    }
    
}
