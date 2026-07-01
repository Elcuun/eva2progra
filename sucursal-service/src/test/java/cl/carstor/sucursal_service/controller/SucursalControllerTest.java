package cl.carstor.sucursal_service.controller;

import cl.carstor.sucursal_service.model.Sucursal;
import cl.carstor.sucursal_service.service.SucursalService;
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
@DisplayName("Pruebas unitarias para SucursalController")
class SucursalControllerTest {
    @Mock
    private SucursalService sucursalService;
    @InjectMocks
    private SucursalController sucursalController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(sucursalController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Debe retornar sucursales")
    void listar_deberiaRetornarSucursales() throws Exception {
        when(sucursalService.findAll()).thenReturn(List.of(new Sucursal(1L, "Centro", "Calle 1", "912345678")));

        mockMvc.perform(get("/api/v1/sucursales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Centro"));
    }

    @Test
    @DisplayName("Debe retornar 404 cuando sucursal no existe")
    void buscarPorId_cuandoNoExiste_deberiaRetornarNotFound() throws Exception {
        when(sucursalService.findById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/sucursales/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debe crear sucursal")
    void registrarSucursal_deberiaRetornarCreated() throws Exception {
        Sucursal sucursal = new Sucursal(1L, "Centro", "Calle 1", "912345678");
        when(sucursalService.save(sucursal)).thenReturn(sucursal);

        mockMvc.perform(post("/api/v1/sucursales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sucursal)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Centro"));
    }

    @Test
    @DisplayName("Debe modificar sucursal")
    void modificar_deberiaRetornarOk() throws Exception {
        Sucursal sucursal = new Sucursal(1L, "Centro", "Calle 1", "912345678");
        when(sucursalService.update(1L, sucursal)).thenReturn(sucursal);

        mockMvc.perform(put("/api/v1/sucursales/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sucursal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.direccion").value("Calle 1"));
    }

    @Test
    @DisplayName("Debe eliminar sucursal")
    void eliminar_deberiaRetornarNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/sucursales/1"))
                .andExpect(status().isNoContent());

        verify(sucursalService).delete(1L);
    }
}
