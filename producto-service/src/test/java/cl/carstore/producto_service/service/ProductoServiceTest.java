package cl.carstore.producto_service.service;

import cl.carstore.producto_service.dto.ProductoDTO;
import cl.carstore.producto_service.mapper.ProductoMapper;
import cl.carstore.producto_service.model.Producto;
import cl.carstore.producto_service.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private ProductoMapper productoMapper;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void findallRetornaProductosMapeadosADto() {
        Producto producto = producto(1L, "Toyota", "Corolla", 2023L, "Rojo", 14500000, "DISPONIBLE");
        ProductoDTO dto = dto("Toyota", "Corolla", 2023L, "Rojo", 14500000, "DISPONIBLE");

        when(productoRepository.findAll()).thenReturn(List.of(producto));
        when(productoMapper.toDTO(producto)).thenReturn(dto);

        List<ProductoDTO> resultado = productoService.findall();

        assertEquals(1, resultado.size());
        assertEquals("Toyota", resultado.getFirst().getMarca());
        assertEquals(14500000, resultado.getFirst().getPrecio());
    }

    @Test
    void modificarActualizaCamposCuandoProductoExiste() {
        Producto actual = producto(10L, "Toyota", "Corolla", 2020L, "Blanco", 10000000, "DISPONIBLE");
        Producto cambios = producto(null, "Mazda", "CX-5", 2024L, "Azul", 22000000, "RESERVADO");

        when(productoRepository.findById(10L)).thenReturn(Optional.of(actual));
        when(productoRepository.save(actual)).thenReturn(actual);

        Producto resultado = productoService.modificar(10L, cambios);

        assertEquals("Mazda", resultado.getMarca());
        assertEquals("CX-5", resultado.getModelo());
        assertEquals(2024L, resultado.getAnio());
        assertEquals("Azul", resultado.getColor());
        assertEquals(22000000, resultado.getPrecio());
        assertEquals("RESERVADO", resultado.getEstado());
        verify(productoRepository).save(actual);
    }

    @Test
    void modificarRetornaNullCuandoProductoNoExiste() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        Producto resultado = productoService.modificar(99L, producto(null, "Kia", "Rio", 2021L, "Gris", 9000000, "DISPONIBLE"));

        assertNull(resultado);
    }

    @Test
    void productoMasCaroConsultaRepositorioOrdenadoPorPrecioDescendente() {
        Producto esperado = producto(3L, "BMW", "X5", 2024L, "Negro", 60000000, "DISPONIBLE");
        when(productoRepository.findTopByOrderByPrecioDesc()).thenReturn(esperado);

        Producto resultado = productoService.productoMasCaro();

        assertEquals(60000000, resultado.getPrecio());
        verify(productoRepository).findTopByOrderByPrecioDesc();
    }

    private Producto producto(Long id, String marca, String modelo, Long anio, String color, int precio, String estado) {
        return new Producto(id, marca, modelo, anio, color, precio, estado);
    }

    private ProductoDTO dto(String marca, String modelo, Long anio, String color, int precio, String estado) {
        ProductoDTO dto = new ProductoDTO();
        dto.setMarca(marca);
        dto.setModelo(modelo);
        dto.setAnio(anio);
        dto.setColor(color);
        dto.setPrecio(precio);
        dto.setEstado(estado);
        return dto;
    }
}
