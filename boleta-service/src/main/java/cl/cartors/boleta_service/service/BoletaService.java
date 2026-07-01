package cl.cartors.boleta_service.service;

import cl.cartors.boleta_service.client.PedidoFeign;
import cl.cartors.boleta_service.dto.BoletaDTO;
import cl.cartors.boleta_service.dto.PedidoDTO;
import cl.cartors.boleta_service.dto.ResumenMetodoPagoDTO;
import cl.cartors.boleta_service.exception.ResourceNotFoundException;
import cl.cartors.boleta_service.model.Boleta;
import cl.cartors.boleta_service.repository.BoletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BoletaService {
    @Autowired
    private BoletaRepository boletaRepository;
    @Autowired
    private PedidoFeign pedidoFeign;

    public List<Boleta> findAll() {
        return boletaRepository.findAll();
    }

    public BoletaDTO findById(Long id) {
        Boleta boleta = boletaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la boleta " + id));
        return toDTO(boleta);
    }

    public Boleta save(Boleta boleta) {
        return boletaRepository.save(boleta);
    }

    public Boleta update(Long id, Boleta boleta) {
        Boleta actual = boletaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la boleta " + id));
        actual.setFecha(boleta.getFecha());
        actual.setTotal(boleta.getTotal());
        actual.setMetodoPago(boleta.getMetodoPago());
        actual.setIdPedido(boleta.getIdPedido());
        return boletaRepository.save(actual);
    }

    public void delete(Long id) {
        if (!boletaRepository.existsById(id)) {
            throw new ResourceNotFoundException("No existe la boleta " + id);
        }
        boletaRepository.deleteById(id);
    }

    public List<Boleta> findByMetodoPago(String metodoPago) {
        return boletaRepository.findByMetodoPagoIgnoreCase(metodoPago);
    }

    public List<Boleta> findByFechaBetween(LocalDate desde, LocalDate hasta) {
        return boletaRepository.findByFechaBetween(desde, hasta);
    }

    public List<Boleta> findByTotalBetween(BigDecimal minimo, BigDecimal maximo) {
        return boletaRepository.findByTotalBetween(minimo, maximo);
    }

    public List<Boleta> findByPedido(Long idPedido) {
        return boletaRepository.findByIdPedido(idPedido);
    }

    public List<ResumenMetodoPagoDTO> resumenPorMetodoPago() {
        return boletaRepository.resumenPorMetodoPago().stream()
                .map(fila -> new ResumenMetodoPagoDTO((String) fila[0], (Long) fila[1], (BigDecimal) fila[2]))
                .toList();
    }

    public BigDecimal totalVendidoEntreFechas(LocalDate desde, LocalDate hasta) {
        return boletaRepository.totalVendidoEntreFechas(desde, hasta);
    }

    private BoletaDTO toDTO(Boleta boleta) {
        BoletaDTO dto = new BoletaDTO();
        dto.setIdBoleta(boleta.getIdBoleta());
        dto.setFecha(boleta.getFecha());
        dto.setTotal(boleta.getTotal());
        dto.setMetodoPago(boleta.getMetodoPago());
        dto.setIdPedido(boleta.getIdPedido());
        PedidoDTO pedido = pedidoFeign.obtenerPedido(boleta.getIdPedido());
        dto.setPedido(pedido);
        return dto;
    }
}
