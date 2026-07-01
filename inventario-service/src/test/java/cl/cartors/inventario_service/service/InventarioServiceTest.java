package cl.cartors.inventario_service.service;

import cl.cartors.inventario_service.Client.ProductoFeign;
import cl.cartors.inventario_service.Client.SucursalFeign;
import cl.cartors.inventario_service.dto.InventarioDTO;
import cl.cartors.inventario_service.dto.ProductoDTO;
import cl.cartors.inventario_service.dto.SucursalDTO;
import cl.cartors.inventario_service.mapper.Inventariomapper;
import cl.cartors.inventario_service.model.Inventario;
import cl.cartors.inventario_service.repository.InventarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para InventarioService")
class InventarioServiceTest {
    @Mock
    private InventarioRepository inventarioRepository;
    @Mock
    private SucursalFeign sucursalFeign;
    @Mock
    private ProductoFeign productoFeign;
    @Mock
    private Inventariomapper inventariomapper;
    @InjectMocks
    private InventarioService inventarioService;

    @Test
    @DisplayName("Debe listar inventarios con producto y sucursal remotos")
    void findAll_deberiaRetornarInventariosDTO() {
        Inventario inventario = inventario(1L, 2L, 3L, 5, LocalDate.of(2026, 6, 19));
        when(inventarioRepository.findAll()).thenReturn(List.of(inventario));
        when(productoFeign.obtenerProducto(2L)).thenReturn(productoDto("Toyota"));
        when(sucursalFeign.obtenerSucursal(3L)).thenReturn(sucursalDto("Centro"));

        List<InventarioDTO> resultado = inventarioService.findAll();

        assertEquals(1, resultado.size());
        assertEquals("Toyota", resultado.getFirst().getProducto().getMarca());
        assertEquals("Centro", resultado.getFirst().getSucursal().getNombre());
    }

    @Test
    @DisplayName("Debe buscar inventario por ID")
    void findById_cuandoExiste_deberiaRetornarDTO() {
        Inventario inventario = inventario(1L, 2L, 3L, 5, LocalDate.of(2026, 6, 19));
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inventario));
        when(productoFeign.obtenerProducto(2L)).thenReturn(productoDto("Toyota"));
        when(sucursalFeign.obtenerSucursal(3L)).thenReturn(sucursalDto("Centro"));

        InventarioDTO resultado = inventarioService.findById(1L);

        assertEquals(5, resultado.getStock());
        assertEquals("Toyota", resultado.getProducto().getMarca());
    }

    @Test
    @DisplayName("Debe actualizar inventario existente")
    void update_cuandoExiste_deberiaActualizarCampos() {
        Inventario actual = inventario(1L, 2L, 3L, 5, LocalDate.of(2026, 6, 19));
        Inventario cambios = inventario(null, 4L, 5L, 9, LocalDate.of(2026, 6, 20));
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(actual));
        when(inventarioRepository.save(actual)).thenReturn(actual);

        Inventario resultado = inventarioService.update(1L, cambios);

        assertEquals(4L, resultado.getIdProducto());
        assertEquals(5L, resultado.getIdSucursal());
        assertEquals(9, resultado.getStock());
    }

    @Test
    @DisplayName("Debe filtrar por stock bajo")
    void findByStockBajo_deberiaDelegarAlRepositorio() {
        when(inventarioRepository.findByStockLessThanEqual(3)).thenReturn(List.of(inventario(1L, 2L, 3L, 2, LocalDate.now())));

        List<Inventario> resultado = inventarioService.findByStockBajo(3);

        assertEquals(1, resultado.size());
        verify(inventarioRepository).findByStockLessThanEqual(3);
    }

    private Inventario inventario(Long id, Long producto, Long sucursal, Integer stock, LocalDate fecha) {
        return new Inventario(id, producto, sucursal, stock, fecha);
    }

    private ProductoDTO productoDto(String marca) {
        ProductoDTO dto = new ProductoDTO();
        dto.setMarca(marca);
        dto.setModelo("Corolla");
        return dto;
    }

    private SucursalDTO sucursalDto(String nombre) {
        SucursalDTO dto = new SucursalDTO();
        dto.setNombre(nombre);
        dto.setDireccion("Calle 1");
        return dto;
    }
}
