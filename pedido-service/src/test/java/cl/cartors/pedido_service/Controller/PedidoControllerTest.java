package cl.cartors.pedido_service.Controller;

import cl.cartors.pedido_service.DTO.ClienteDTO;
import cl.cartors.pedido_service.DTO.EmpleadoDTO;
import cl.cartors.pedido_service.DTO.PedidoDTO;
import cl.cartors.pedido_service.Service.PedidoService;
import cl.cartors.pedido_service.model.Pedido;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PedidoControllerTest {

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoController pedidoController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(pedidoController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void listarRetornaPedidos() throws Exception {
        when(pedidoService.findAll()).thenReturn(List.of(pedidoDto()));

        mockMvc.perform(get("/api/v1/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idpedido").value(1))
                .andExpect(jsonPath("$[0].cliente.nombre").value("Ana"));
    }

    @Test
    void buscarPorIdRetornaNotFoundCuandoNoExiste() throws Exception {
        when(pedidoService.findById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/pedidos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void registrarPedidoRetornaCreated() throws Exception {
        Pedido pedido = new Pedido(1L, LocalDate.of(2026, 6, 19), "PENDIENTE", 7L, 3L);
        when(pedidoService.save(pedido)).thenReturn(pedido);

        mockMvc.perform(post("/api/v1/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));
    }

    @Test
    void modificarPedidoRetornaOk() throws Exception {
        Pedido pedido = new Pedido(1L, LocalDate.of(2026, 6, 19), "PENDIENTE", 7L, 3L);
        when(pedidoService.update(1L, pedido)).thenReturn(pedido);

        mockMvc.perform(put("/api/v1/pedidos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));
    }

    @Test
    void eliminarPedidoRetornaNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/pedidos/1"))
                .andExpect(status().isNoContent());

        verify(pedidoService).delete(1L);
    }

    private PedidoDTO pedidoDto() {
        ClienteDTO cliente = new ClienteDTO();
        cliente.setNombre("Ana");
        cliente.setApellido("Perez");

        EmpleadoDTO empleado = new EmpleadoDTO();
        empleado.setNombre("Luis");
        empleado.setApellido("Rojas");

        PedidoDTO dto = new PedidoDTO();
        dto.setIdpedido(1L);
        dto.setEstado("PENDIENTE");
        dto.setFechapedido(LocalDate.of(2026, 6, 19));
        dto.setCliente(cliente);
        dto.setEmpleado(empleado);
        return dto;
    }
}
