package cl.cartors.envio_service.service;

import cl.cartors.envio_service.client.PedidoFeign;
import cl.cartors.envio_service.dto.EnvioDTO;
import cl.cartors.envio_service.dto.PedidoDTO;
import cl.cartors.envio_service.dto.ResumenEstadoDTO;
import cl.cartors.envio_service.exception.ResourceNotFoundException;
import cl.cartors.envio_service.model.Envio;
import cl.cartors.envio_service.repository.EnvioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EnvioService {
    @Autowired
    private EnvioRepository envioRepository;
    @Autowired
    private PedidoFeign pedidoFeign;

    public List<Envio> findAll() {
        return envioRepository.findAll();
    }

    public EnvioDTO findById(Long id) {
        Envio envio = envioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el envio " + id));
        return toDTO(envio);
    }

    public Envio save(Envio envio) {
        return envioRepository.save(envio);
    }

    public Envio update(Long id, Envio envio) {
        Envio actual = envioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el envio " + id));
        actual.setDireccionEntrega(envio.getDireccionEntrega());
        actual.setFechaEnvio(envio.getFechaEnvio());
        actual.setEstado(envio.getEstado());
        actual.setIdPedido(envio.getIdPedido());
        return envioRepository.save(actual);
    }

    public void delete(Long id) {
        if (!envioRepository.existsById(id)) {
            throw new ResourceNotFoundException("No existe el envio " + id);
        }
        envioRepository.deleteById(id);
    }

    public List<Envio> findByEstado(String estado) {
        return envioRepository.findByEstadoIgnoreCase(estado);
    }

    public List<Envio> findByFechaEnvioBetween(LocalDate desde, LocalDate hasta) {
        return envioRepository.findByFechaEnvioBetween(desde, hasta);
    }

    public List<Envio> findByDireccion(String texto) {
        return envioRepository.findByDireccionEntregaContainingIgnoreCase(texto);
    }

    public List<Envio> findByPedido(Long idPedido) {
        return envioRepository.findByIdPedido(idPedido);
    }

    public List<ResumenEstadoDTO> resumenPorEstado() {
        return envioRepository.resumenPorEstado().stream()
                .map(fila -> new ResumenEstadoDTO((String) fila[0], (Long) fila[1]))
                .toList();
    }

    private EnvioDTO toDTO(Envio envio) {
        EnvioDTO dto = new EnvioDTO();
        dto.setIdEnvio(envio.getIdEnvio());
        dto.setDireccionEntrega(envio.getDireccionEntrega());
        dto.setFechaEnvio(envio.getFechaEnvio());
        dto.setEstado(envio.getEstado());
        dto.setIdPedido(envio.getIdPedido());
        PedidoDTO pedido = pedidoFeign.obtenerPedido(envio.getIdPedido());
        dto.setPedido(pedido);
        return dto;
    }
}
