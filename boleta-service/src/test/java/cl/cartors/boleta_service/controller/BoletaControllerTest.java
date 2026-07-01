package cl.cartors.boleta_service.controller;

import cl.cartors.boleta_service.model.Boleta;
import cl.cartors.boleta_service.service.BoletaService;
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

import java.math.BigDecimal;
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
@DisplayName("Pruebas unitarias para BoletaController")
class BoletaControllerTest {
    @Mock
    private BoletaService boletaService;
    @InjectMocks
    private BoletaController boletaController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(boletaController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Debe retornar boletas")
    void listar_deberiaRetornarBoletas() throws Exception {
        when(boletaService.findAll()).thenReturn(List.of(boleta()));

        mockMvc.perform(get("/api/v1/boletas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].metodoPago").value("DEBITO"));
    }

    @Test
    @DisplayName("Debe crear boleta")
    void registrar_deberiaRetornarCreated() throws Exception {
        Boleta boleta = boleta();
        when(boletaService.save(boleta)).thenReturn(boleta);

        mockMvc.perform(post("/api/v1/boletas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boleta)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.metodoPago").value("DEBITO"));
    }

    @Test
    @DisplayName("Debe modificar boleta")
    void modificar_deberiaRetornarOk() throws Exception {
        Boleta boleta = boleta();
        when(boletaService.update(1L, boleta)).thenReturn(boleta);

        mockMvc.perform(put("/api/v1/boletas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boleta)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(150000));
    }

    @Test
    @DisplayName("Debe eliminar boleta")
    void eliminar_deberiaRetornarNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/boletas/1"))
                .andExpect(status().isNoContent());

        verify(boletaService).delete(1L);
    }

    private Boleta boleta() {
        return new Boleta(1L, LocalDate.of(2026, 6, 19), BigDecimal.valueOf(150000), "DEBITO", 10L);
    }
}
