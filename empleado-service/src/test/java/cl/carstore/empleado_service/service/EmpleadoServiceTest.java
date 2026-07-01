package cl.carstore.empleado_service.service;

import cl.carstore.empleado_service.clients.SucursalFeingn;
import cl.carstore.empleado_service.dto.EmpleadoDTO;
import cl.carstore.empleado_service.dto.SucursalDTO;
import cl.carstore.empleado_service.exception.TelefonoInvalidoException;
import cl.carstore.empleado_service.mapper.EmpleadoMapper;
import cl.carstore.empleado_service.model.Empleado;
import cl.carstore.empleado_service.repository.EmpleadoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para EmpleadoService")
class EmpleadoServiceTest {
    @Mock
    private EmpleadoRepository empleadoRepository;
    @Mock
    private EmpleadoMapper empleadoMapper;
    @Mock
    private SucursalFeingn sucursalFeingn;
    @InjectMocks
    private EmpleadoService empleadoService;

    @Test
    @DisplayName("Debe listar empleados con sucursal remota")
    void findAll_deberiaRetornarEmpleadosConSucursal() {
        Empleado empleado = empleado(1L, "Luis", "Rojas", "Vendedor", "912345678", 2L);
        EmpleadoDTO empleadoDTO = empleadoDto("Luis", "Rojas");
        SucursalDTO sucursalDTO = sucursalDto("Centro");
        when(empleadoRepository.findAll()).thenReturn(List.of(empleado));
        when(empleadoMapper.toDTO(empleado)).thenReturn(empleadoDTO);
        when(sucursalFeingn.obtenerSucursal(2L)).thenReturn(sucursalDTO);

        List<EmpleadoDTO> resultado = empleadoService.findAll();

        assertEquals(1, resultado.size());
        assertEquals("Centro", resultado.getFirst().getSucursal().getNombre());
    }

    @Test
    @DisplayName("Debe guardar empleado con telefono valido")
    void save_conTelefonoValido_deberiaGuardar() {
        Empleado empleado = empleado(null, "Luis", "Rojas", "Vendedor", "912345678", 2L);
        when(empleadoRepository.save(empleado)).thenReturn(empleado);

        Empleado resultado = empleadoService.save(empleado);

        assertEquals("912345678", resultado.getTelefono());
        verify(empleadoRepository).save(empleado);
    }

    @Test
    @DisplayName("Debe lanzar excepcion con telefono invalido")
    void save_conTelefonoInvalido_deberiaLanzarExcepcion() {
        Empleado empleado = empleado(null, "Luis", "Rojas", "Vendedor", "123", 2L);

        assertThrows(TelefonoInvalidoException.class, () -> empleadoService.save(empleado));
        verifyNoInteractions(empleadoRepository);
    }

    @Test
    @DisplayName("Debe actualizar empleado existente")
    void update_cuandoExiste_deberiaActualizarCampos() {
        Empleado actual = empleado(1L, "Luis", "Rojas", "Vendedor", "912345678", 2L);
        Empleado cambios = empleado(null, "Ana", "Perez", "Jefa", "987654321", 3L);
        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(actual));
        when(empleadoRepository.save(actual)).thenReturn(actual);

        Empleado resultado = empleadoService.update(1L, cambios);

        assertEquals("Ana", resultado.getNombre());
        assertEquals("Jefa", resultado.getCargo());
        assertEquals(3L, resultado.getSucursal());
    }

    private Empleado empleado(Long id, String nombre, String apellido, String cargo, String telefono, Long sucursal) {
        return new Empleado(id, nombre, apellido, cargo, telefono, nombre + "@mail.com", "1234", sucursal);
    }

    private EmpleadoDTO empleadoDto(String nombre, String apellido) {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setNombre(nombre);
        dto.setApellido(apellido);
        return dto;
    }

    private SucursalDTO sucursalDto(String nombre) {
        SucursalDTO dto = new SucursalDTO();
        dto.setNombre(nombre);
        dto.setDireccion("Calle 1");
        return dto;
    }
}
