package cl.cartors.envio_service.service;

import cl.cartors.envio_service.client.PedidoFeign;
import cl.cartors.envio_service.dto.EnvioDTO;
import cl.cartors.envio_service.dto.PedidoDTO;
import cl.cartors.envio_service.exception.ResourceNotFoundException;
import cl.cartors.envio_service.model.Envio;
import cl.cartors.envio_service.repository.EnvioRepository;
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
@DisplayName("Pruebas unitarias para EnvioService")
class EnvioServiceTest {
    @Mock
    private EnvioRepository envioRepository;
    @Mock
    private PedidoFeign pedidoFeign;
    @InjectMocks
    private EnvioService envioService;

    @Test
    @DisplayName("Debe buscar envio por ID con pedido remoto")
    void findById_cuandoExiste_deberiaRetornarDTO() {
        Envio envio = envio(1L, "Calle 1", LocalDate.of(2026, 6, 19), "EN_RUTA", 10L);
        PedidoDTO pedido = new PedidoDTO();
        pedido.setIdpedido(10L);
        when(envioRepository.findById(1L)).thenReturn(Optional.of(envio));
        when(pedidoFeign.obtenerPedido(10L)).thenReturn(pedido);

        EnvioDTO resultado = envioService.findById(1L);

        assertEquals("EN_RUTA", resultado.getEstado());
        assertEquals(10L, resultado.getPedido().getIdpedido());
    }

    @Test
    @DisplayName("Debe lanzar excepcion cuando envio no existe")
    void findById_cuandoNoExiste_deberiaLanzarExcepcion() {
        when(envioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> envioService.findById(99L));
        verifyNoInteractions(pedidoFeign);
    }

    @Test
    @DisplayName("Debe actualizar envio existente")
    void update_cuandoExiste_deberiaActualizarCampos() {
        Envio actual = envio(1L, "Calle 1", LocalDate.of(2026, 6, 19), "PENDIENTE", 10L);
        Envio cambios = envio(null, "Calle 2", LocalDate.of(2026, 6, 20), "ENTREGADO", 11L);
        when(envioRepository.findById(1L)).thenReturn(Optional.of(actual));
        when(envioRepository.save(actual)).thenReturn(actual);

        Envio resultado = envioService.update(1L, cambios);

        assertEquals("Calle 2", resultado.getDireccionEntrega());
        assertEquals("ENTREGADO", resultado.getEstado());
        assertEquals(11L, resultado.getIdPedido());
    }

    @Test
    @DisplayName("Debe filtrar por estado")
    void findByEstado_deberiaDelegarAlRepositorio() {
        when(envioRepository.findByEstadoIgnoreCase("ENTREGADO")).thenReturn(List.of(envio(1L, "Calle 1", LocalDate.now(), "ENTREGADO", 10L)));

        List<Envio> resultado = envioService.findByEstado("ENTREGADO");

        assertEquals(1, resultado.size());
        verify(envioRepository).findByEstadoIgnoreCase("ENTREGADO");
    }

    private Envio envio(Long id, String direccion, LocalDate fecha, String estado, Long idPedido) {
        return new Envio(id, direccion, fecha, estado, idPedido);
    }
}
