package cl.cartors.pedido_service.Service;

import cl.cartors.pedido_service.Client.ClienteFeign;
import cl.cartors.pedido_service.Client.EmpleadoFeign;
import cl.cartors.pedido_service.DTO.ClienteDTO;
import cl.cartors.pedido_service.DTO.EmpleadoDTO;
import cl.cartors.pedido_service.DTO.PedidoDTO;
import cl.cartors.pedido_service.Repository.PedidoRepository;
import cl.cartors.pedido_service.mapper.PedidoMapper;
import cl.cartors.pedido_service.model.Pedido;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Data
@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ClienteFeign clienteFeign;
    @Autowired
    private EmpleadoFeign empleadoFeign;
    @Autowired
    private PedidoMapper pedidoMapper;

    public List<PedidoDTO> findAll() {
        List<Pedido> listado = pedidoRepository.findAll();

        return listado.stream().map(pedido -> {

            PedidoDTO dto = new PedidoDTO();

            dto.setIdpedido(pedido.getIdPedido());
            dto.setFechapedido(pedido.getFechaPedido());
            dto.setEstado(pedido.getEstado());

            ClienteDTO cliente =
                    clienteFeign.obtenerCliente(pedido.getIdCliente());

            EmpleadoDTO empleado =
                    empleadoFeign.obtenerEmpleado(pedido.getIdEmpleado());

            dto.setCliente(cliente);
            dto.setEmpleado(empleado);

            return dto;

        }).toList();
    }

    public PedidoDTO findById(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido == null) return null;

        PedidoDTO dto = new PedidoDTO();

        dto.setIdpedido(pedido.getIdPedido());
        dto.setFechapedido(pedido.getFechaPedido());
        dto.setEstado(pedido.getEstado());

        ClienteDTO cliente =
                clienteFeign.obtenerCliente(pedido.getIdCliente());

        EmpleadoDTO empleado =
                empleadoFeign.obtenerEmpleado(pedido.getIdEmpleado());

        dto.setCliente(cliente);
        dto.setEmpleado(empleado);

        return dto;

    }

    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void delete(Long id) {
        pedidoRepository.deleteById(id);
    }

    public Pedido update(Long id, Pedido pedido) {
        Pedido pedidoActualizado = pedidoRepository.findById(id).orElse(null);
        if (pedidoActualizado == null) return null;

        pedidoActualizado.setFechaPedido(pedido.getFechaPedido());
        pedidoActualizado.setEstado(pedido.getEstado());
        pedidoActualizado.setIdCliente(pedido.getIdCliente());
        pedidoActualizado.setIdEmpleado(pedido.getIdEmpleado());
        return pedidoRepository.save(pedidoActualizado);
    }

    public List<Pedido> findByEstado(String estado) {
        return pedidoRepository.findByEstadoIgnoreCase(estado);
    }

    public List<Pedido> findByCliente(Long idCliente) {
        return pedidoRepository.findByIdCliente(idCliente);
    }

    public List<Pedido> findByEmpleado(Long idEmpleado) {
        return pedidoRepository.findByIdEmpleado(idEmpleado);
    }

    public List<Pedido> findByFecha(LocalDate desde, LocalDate hasta) {
        return pedidoRepository.findByFechaPedidoBetween(desde, hasta);
    }
}
