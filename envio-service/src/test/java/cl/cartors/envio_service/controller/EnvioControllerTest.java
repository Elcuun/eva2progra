package cl.cartors.envio_service.controller;

import cl.cartors.envio_service.model.Envio;
import cl.cartors.envio_service.service.EnvioService;
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
@DisplayName("Pruebas unitarias para EnvioController")
class EnvioControllerTest {
    @Mock
    private EnvioService envioService;
    @InjectMocks
    private EnvioController envioController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(envioController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Debe retornar envios")
    void listar_deberiaRetornarEnvios() throws Exception {
        when(envioService.findAll()).thenReturn(List.of(envio()));

        mockMvc.perform(get("/api/v1/envios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("EN_RUTA"));
    }

    @Test
    @DisplayName("Debe crear envio")
    void registrar_deberiaRetornarCreated() throws Exception {
        Envio envio = envio();
        when(envioService.save(envio)).thenReturn(envio);

        mockMvc.perform(post("/api/v1/envios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(envio)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estado").value("EN_RUTA"));
    }

    @Test
    @DisplayName("Debe modificar envio")
    void modificar_deberiaRetornarOk() throws Exception {
        Envio envio = envio();
        when(envioService.update(1L, envio)).thenReturn(envio);

        mockMvc.perform(put("/api/v1/envios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(envio)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.direccionEntrega").value("Calle 1"));
    }

    @Test
    @DisplayName("Debe eliminar envio")
    void eliminar_deberiaRetornarNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/envios/1"))
                .andExpect(status().isNoContent());

        verify(envioService).delete(1L);
    }

    private Envio envio() {
        return new Envio(1L, "Calle 1", LocalDate.of(2026, 6, 19), "EN_RUTA", 10L);
    }
}
