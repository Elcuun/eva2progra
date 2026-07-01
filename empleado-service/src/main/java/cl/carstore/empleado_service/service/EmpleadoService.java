package cl.carstore.empleado_service.service;

import cl.carstore.empleado_service.clients.SucursalFeingn;
import cl.carstore.empleado_service.dto.EmpleadoDTO;
import cl.carstore.empleado_service.dto.SucursalDTO;
import cl.carstore.empleado_service.exception.TelefonoInvalidoException;
import cl.carstore.empleado_service.mapper.EmpleadoMapper;
import cl.carstore.empleado_service.model.Empleado;
import cl.carstore.empleado_service.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private EmpleadoMapper empleadoMapper;

    @Autowired
    private SucursalFeingn sucursalFeingn;

    public List<EmpleadoDTO> findAll(){
        List<Empleado> listado = empleadoRepository.findAll();

        return listado.stream().map(empleado -> {

            EmpleadoDTO dto = empleadoMapper.toDTO(empleado);

            dto.setSucursal(
                    sucursalFeingn.obtenerSucursal(empleado.getSucursal())
            );

            return dto;

        }).toList();

    }

    public EmpleadoDTO findById(Long id){
        Empleado empleado = empleadoRepository.findById(id).orElse(null);
        EmpleadoDTO empleadoDTO = empleadoMapper.toDTO(empleado);
        SucursalDTO sucursalDTO = sucursalFeingn.obtenerSucursal(empleado.getSucursal());
        empleadoDTO.setSucursal(sucursalDTO);
        return empleadoDTO;

    }

    public Empleado save(Empleado empleado){
        if(empleado.getTelefono().length() != 9){
            throw new TelefonoInvalidoException(
                    "El telfono debe tener 9 caracteres"

            );
        }
        return empleadoRepository.save(empleado);

    }

    public void  delete(Long id){
        empleadoRepository.deleteById(id);

    }
    public Empleado update(Long id, Empleado empleado){
        Empleado empleadoActualizado = empleadoRepository.findById(id).orElse(null);
        if (empleadoActualizado == null ) return null;

        empleadoActualizado.setNombre(empleado.getNombre());
        empleadoActualizado.setApellido(empleado.getApellido());
        empleadoActualizado.setNombre(empleado.getNombre());
        empleadoActualizado.setCargo(empleado.getCargo());
        empleadoActualizado.setTelefono(empleado.getTelefono());
        empleadoActualizado.setEmail(empleado.getEmail());
        empleadoActualizado.setPassword(empleado.getPassword());
        empleadoActualizado.setSucursal(empleado.getSucursal());
        return empleadoRepository.save(empleadoActualizado);

    }

}
