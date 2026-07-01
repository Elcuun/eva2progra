package cl.cartors.pedido_service.Service;

import cl.cartors.pedido_service.DTO.ProductoVendidoDTO;
import cl.cartors.pedido_service.Repository.DetallePedidoRepository;
import cl.cartors.pedido_service.model.DetallePedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DetallePedidoService {
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    public DetallePedido save(DetallePedido detallePedido) {
        return detallePedidoRepository.save(detallePedido);
    }

    public List<DetallePedido> findByPedido(Long idPedido) {
        return detallePedidoRepository.findByIdPedido(idPedido);
    }

    public List<DetallePedido> findByProducto(Long idProducto) {
        return detallePedidoRepository.findByIdProducto(idProducto);
    }

    public BigDecimal totalPorPedido(Long idPedido) {
        return detallePedidoRepository.totalPorPedido(idPedido);
    }

    public List<ProductoVendidoDTO> productosMasVendidos() {
        return detallePedidoRepository.productosMasVendidos().stream()
                .map(fila -> new ProductoVendidoDTO((Long) fila[0], (Long) fila[1]))
                .toList();
    }
}
