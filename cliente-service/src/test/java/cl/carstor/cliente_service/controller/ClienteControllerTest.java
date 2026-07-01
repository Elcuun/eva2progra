package cl.carstor.cliente_service.controller;

import cl.carstor.cliente_service.dto.ClienteDTO;
import cl.carstor.cliente_service.model.Cliente;
import cl.carstor.cliente_service.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
@DisplayName("Pruebas unitarias para ClienteController")
class ClienteControllerTest {
    @Mock
    private ClienteService clienteService;
    @InjectMocks
    private ClienteController clienteController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Debe retornar clientes")
    void listar_deberiaRetornarClientes() throws Exception {
        ClienteDTO dto = new ClienteDTO();
        dto.setNombre("Ana");
        dto.setEmail("ana@mail.com");
        when(clienteService.findall()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Ana"));
    }

    @Test
    @DisplayName("Debe retornar 404 cuando cliente no existe")
    void buscarPorId_cuandoNoExiste_deberiaRetornarNotFound() throws Exception {
        when(clienteService.findByid(99L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/clientes/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debe crear cliente")
    void registrarCliente_deberiaRetornarCreated() throws Exception {
        Cliente cliente = new Cliente(1L, "Ana", "Perez", "912345678", "ana@mail.com", "1234", "Calle 1");
        when(clienteService.save(cliente)).thenReturn(cliente);

        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("ana@mail.com"));
    }

    @Test
    @DisplayName("Debe modificar cliente")
    void modificar_deberiaRetornarOk() throws Exception {
        Cliente cliente = new Cliente(1L, "Ana", "Perez", "912345678", "ana@mail.com", "1234", "Calle 1");
        when(clienteService.modificar(1L, cliente)).thenReturn(cliente);

        mockMvc.perform(put("/api/v1/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Ana"));
    }

    @Test
    @DisplayName("Debe eliminar cliente")
    void eliminar_deberiaRetornarNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/clientes/1"))
                .andExpect(status().isNoContent());

        verify(clienteService).delete(1L);
    }
}
