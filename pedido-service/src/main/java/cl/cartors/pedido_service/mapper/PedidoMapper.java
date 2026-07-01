package cl.cartors.pedido_service.mapper;

import cl.cartors.pedido_service.DTO.PedidoDTO;
import cl.cartors.pedido_service.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {
    public PedidoDTO toDTO(Pedido pedido) {
        if (pedido == null) return null;
        PedidoDTO dto = new PedidoDTO();
        dto.setIdpedido(pedido.getIdPedido());
        dto.setFechapedido(pedido.getFechaPedido());
        dto.setEstado(pedido.getEstado());


        return dto;

    }
}
