package cl.cartors.boleta_service.service;

import cl.cartors.boleta_service.client.PedidoFeign;
import cl.cartors.boleta_service.dto.BoletaDTO;
import cl.cartors.boleta_service.dto.PedidoDTO;
import cl.cartors.boleta_service.exception.ResourceNotFoundException;
import cl.cartors.boleta_service.model.Boleta;
import cl.cartors.boleta_service.repository.BoletaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para BoletaService")
class BoletaServiceTest {
    @Mock
    private BoletaRepository boletaRepository;
    @Mock
    private PedidoFeign pedidoFeign;
    @InjectMocks
    private BoletaService boletaService;

    @Test
    @DisplayName("Debe buscar boleta por ID con pedido remoto")
    void findById_cuandoExiste_deberiaRetornarDTO() {
        Boleta boleta = boleta(1L, LocalDate.of(2026, 6, 19), BigDecimal.valueOf(150000), "DEBITO", 10L);
        PedidoDTO pedido = new PedidoDTO();
        pedido.setIdpedido(10L);
        when(boletaRepository.findById(1L)).thenReturn(Optional.of(boleta));
        when(pedidoFeign.obtenerPedido(10L)).thenReturn(pedido);

        BoletaDTO resultado = boletaService.findById(1L);

        assertEquals("DEBITO", resultado.getMetodoPago());
        assertEquals(10L, resultado.getPedido().getIdpedido());
    }

    @Test
    @DisplayName("Debe lanzar excepcion cuando boleta no existe")
    void findById_cuandoNoExiste_deberiaLanzarExcepcion() {
        when(boletaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> boletaService.findById(99L));
        verifyNoInteractions(pedidoFeign);
    }

    @Test
    @DisplayName("Debe actualizar boleta existente")
    void update_cuandoExiste_deberiaActualizarCampos() {
        Boleta actual = boleta(1L, LocalDate.of(2026, 6, 19), BigDecimal.valueOf(150000), "DEBITO", 10L);
        Boleta cambios = boleta(null, LocalDate.of(2026, 6, 20), BigDecimal.valueOf(200000), "CREDITO", 11L);
        when(boletaRepository.findById(1L)).thenReturn(Optional.of(actual));
        when(boletaRepository.save(actual)).thenReturn(actual);

        Boleta resultado = boletaService.update(1L, cambios);

        assertEquals(BigDecimal.valueOf(200000), resultado.getTotal());
        assertEquals("CREDITO", resultado.getMetodoPago());
        assertEquals(11L, resultado.getIdPedido());
    }

    @Test
    @DisplayName("Debe calcular total vendido entre fechas")
    void totalVendidoEntreFechas_deberiaDelegarAlRepositorio() {
        LocalDate desde = LocalDate.of(2026, 6, 1);
        LocalDate hasta = LocalDate.of(2026, 6, 30);
        when(boletaRepository.totalVendidoEntreFechas(desde, hasta)).thenReturn(BigDecimal.valueOf(300000));

        BigDecimal resultado = boletaService.totalVendidoEntreFechas(desde, hasta);

        assertEquals(BigDecimal.valueOf(300000), resultado);
        verify(boletaRepository).totalVendidoEntreFechas(desde, hasta);
    }

    private Boleta boleta(Long id, LocalDate fecha, BigDecimal total, String metodoPago, Long idPedido) {
        return new Boleta(id, fecha, total, metodoPago, idPedido);
    }
}
