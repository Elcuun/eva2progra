package cl.cartors.inventario_service.Controller;

import cl.cartors.inventario_service.dto.InventarioDTO;
import cl.cartors.inventario_service.model.Inventario;
import cl.cartors.inventario_service.service.InventarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
@DisplayName("Pruebas unitarias para InventarioController")
class InventarioControllerTest {
    @Mock
    private InventarioService inventarioService;
    @InjectMocks
    private InventarioController inventarioController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(inventarioController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Debe retornar inventarios")
    void listar_deberiaRetornarInventarios() throws Exception {
        InventarioDTO dto = new InventarioDTO();
        dto.setIdInventario(1L);
        dto.setStock(5);
        when(inventarioService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/inventarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].stock").value(5));
    }

    @Test
    @DisplayName("Debe retornar 404 cuando inventario no existe")
    void buscar_cuandoNoExiste_deberiaRetornarNotFound() throws Exception {
        when(inventarioService.findById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/inventarios/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debe crear inventario")
    void registrarInventario_deberiaRetornarCreated() throws Exception {
        Inventario inventario = new Inventario(1L, 2L, 3L, 5, LocalDate.of(2026, 6, 19));
        when(inventarioService.save(inventario)).thenReturn(inventario);

        mockMvc.perform(post("/api/v1/inventarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.stock").value(5));
    }

    @Test
    @DisplayName("Debe modificar inventario")
    void modificar_deberiaRetornarOk() throws Exception {
        Inventario inventario = new Inventario(1L, 2L, 3L, 5, LocalDate.of(2026, 6, 19));
        when(inventarioService.update(1L, inventario)).thenReturn(inventario);

        mockMvc.perform(put("/api/v1/inventarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stock").value(5));
    }

    @Test
    @DisplayName("Debe eliminar inventario")
    void eliminar_deberiaRetornarNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/inventarios/1"))
                .andExpect(status().isNoContent());

        verify(inventarioService).delete(1L);
    }
}
