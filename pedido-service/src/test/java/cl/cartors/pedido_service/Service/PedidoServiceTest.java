package cl.cartors.pedido_service.Service;

import cl.cartors.pedido_service.Client.ClienteFeign;
import cl.cartors.pedido_service.Client.EmpleadoFeign;
import cl.cartors.pedido_service.DTO.ClienteDTO;
import cl.cartors.pedido_service.DTO.EmpleadoDTO;
import cl.cartors.pedido_service.DTO.PedidoDTO;
import cl.cartors.pedido_service.Repository.PedidoRepository;
import cl.cartors.pedido_service.mapper.PedidoMapper;
import cl.cartors.pedido_service.model.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteFeign clienteFeign;

    @Mock
    private EmpleadoFeign empleadoFeign;

    @Mock
    private PedidoMapper pedidoMapper;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    void findByIdRetornaPedidoConDatosRemotosDeClienteYEmpleado() {
        Pedido pedido = pedido(1L, LocalDate.of(2026, 6, 19), "PENDIENTE", 7L, 3L);
        ClienteDTO cliente = cliente("Ana", "Perez");
        EmpleadoDTO empleado = empleado("Luis", "Rojas");

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(clienteFeign.obtenerCliente(7L)).thenReturn(cliente);
        when(empleadoFeign.obtenerEmpleado(3L)).thenReturn(empleado);

        PedidoDTO resultado = pedidoService.findById(1L);

        assertEquals(1L, resultado.getIdpedido());
        assertEquals("PENDIENTE", resultado.getEstado());
        assertEquals("Ana", resultado.getCliente().getNombre());
        assertEquals("Luis", resultado.getEmpleado().getNombre());
    }

    @Test
    void findByIdRetornaNullYNoLlamaFeignCuandoPedidoNoExiste() {
        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());

        PedidoDTO resultado = pedidoService.findById(99L);

        assertNull(resultado);
        verifyNoInteractions(clienteFeign, empleadoFeign);
    }

    @Test
    void updateActualizaPedidoExistente() {
        Pedido actual = pedido(5L, LocalDate.of(2026, 6, 1), "PENDIENTE", 1L, 2L);
        Pedido cambios = pedido(null, LocalDate.of(2026, 6, 20), "PAGADO", 10L, 11L);

        when(pedidoRepository.findById(5L)).thenReturn(Optional.of(actual));
        when(pedidoRepository.save(actual)).thenReturn(actual);

        Pedido resultado = pedidoService.update(5L, cambios);

        assertEquals(LocalDate.of(2026, 6, 20), resultado.getFechaPedido());
        assertEquals("PAGADO", resultado.getEstado());
        assertEquals(10L, resultado.getIdCliente());
        assertEquals(11L, resultado.getIdEmpleado());
        verify(pedidoRepository).save(actual);
    }

    @Test
    void findByFechaDelegaFiltroAlRepositorio() {
        LocalDate desde = LocalDate.of(2026, 6, 1);
        LocalDate hasta = LocalDate.of(2026, 6, 30);
        List<Pedido> pedidos = List.of(pedido(1L, desde, "PENDIENTE", 1L, 2L));

        when(pedidoRepository.findByFechaPedidoBetween(desde, hasta)).thenReturn(pedidos);

        List<Pedido> resultado = pedidoService.findByFecha(desde, hasta);

        assertEquals(1, resultado.size());
        verify(pedidoRepository).findByFechaPedidoBetween(desde, hasta);
    }

    private Pedido pedido(Long id, LocalDate fecha, String estado, Long idCliente, Long idEmpleado) {
        return new Pedido(id, fecha, estado, idCliente, idEmpleado);
    }

    private ClienteDTO cliente(String nombre, String apellido) {
        ClienteDTO dto = new ClienteDTO();
        dto.setNombre(nombre);
        dto.setApellido(apellido);
        return dto;
    }

    private EmpleadoDTO empleado(String nombre, String apellido) {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setNombre(nombre);
        dto.setApellido(apellido);
        return dto;
    }
}
