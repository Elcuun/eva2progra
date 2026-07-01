package cl.carstor.sucursal_service.service;

import cl.carstor.sucursal_service.exception.TelefonoInvalidoException;
import cl.carstor.sucursal_service.model.Sucursal;
import cl.carstor.sucursal_service.repository.SucursalRepository;
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
@DisplayName("Pruebas unitarias para SucursalService")
class SucursalServiceTest {
    @Mock
    private SucursalRepository sucursalRepository;
    @InjectMocks
    private SucursalService sucursalService;

    @Test
    @DisplayName("Debe listar sucursales")
    void findAll_deberiaRetornarSucursales() {
        when(sucursalRepository.findAll()).thenReturn(List.of(sucursal(1L, "Centro", "Calle 1", "912345678")));

        List<Sucursal> resultado = sucursalService.findAll();

        assertEquals(1, resultado.size());
        assertEquals("Centro", resultado.getFirst().getNombre());
    }

    @Test
    @DisplayName("Debe guardar sucursal con telefono valido")
    void save_conTelefonoValido_deberiaGuardar() {
        Sucursal sucursal = sucursal(null, "Centro", "Calle 1", "912345678");
        when(sucursalRepository.save(sucursal)).thenReturn(sucursal);

        Sucursal resultado = sucursalService.save(sucursal);

        assertEquals("912345678", resultado.getTelefono());
        verify(sucursalRepository).save(sucursal);
    }

    @Test
    @DisplayName("Debe lanzar excepcion con telefono invalido")
    void save_conTelefonoInvalido_deberiaLanzarExcepcion() {
        Sucursal sucursal = sucursal(null, "Centro", "Calle 1", "123");

        assertThrows(TelefonoInvalidoException.class, () -> sucursalService.save(sucursal));
        verifyNoInteractions(sucursalRepository);
    }

    @Test
    @DisplayName("Debe actualizar sucursal existente")
    void update_cuandoExiste_deberiaActualizarCampos() {
        Sucursal actual = sucursal(1L, "Centro", "Calle 1", "912345678");
        Sucursal cambios = sucursal(null, "Norte", "Calle 2", "987654321");
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(actual));
        when(sucursalRepository.save(actual)).thenReturn(actual);

        Sucursal resultado = sucursalService.update(1L, cambios);

        assertEquals("Norte", resultado.getNombre());
        assertEquals("Calle 2", resultado.getDireccion());
        assertEquals("987654321", resultado.getTelefono());
    }

    private Sucursal sucursal(Long id, String nombre, String direccion, String telefono) {
        return new Sucursal(id, nombre, direccion, telefono);
    }
}
