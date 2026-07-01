package cl.carstore.empleado_service.controller;

import cl.carstore.empleado_service.dto.EmpleadoDTO;
import cl.carstore.empleado_service.model.Empleado;
import cl.carstore.empleado_service.service.EmpleadoService;
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
@DisplayName("Pruebas unitarias para EmpleadoController")
class EmpleadoControllerTest {
    @Mock
    private EmpleadoService empleadoService;
    @InjectMocks
    private EmpleadoController empleadoController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(empleadoController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Debe retornar empleados")
    void listar_deberiaRetornarEmpleados() throws Exception {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setNombre("Luis");
        when(empleadoService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Luis"));
    }

    @Test
    @DisplayName("Debe retornar 404 cuando empleado no existe")
    void buscarPorId_cuandoNoExiste_deberiaRetornarNotFound() throws Exception {
        when(empleadoService.findById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/empleados/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debe crear empleado")
    void registrarEmpleado_deberiaRetornarCreated() throws Exception {
        Empleado empleado = new Empleado(1L, "Luis", "Rojas", "Vendedor", "912345678", "luis@mail.com", "1234", 2L);
        when(empleadoService.save(empleado)).thenReturn(empleado);

        mockMvc.perform(post("/api/v1/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(empleado)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Luis"));
    }

    @Test
    @DisplayName("Debe modificar empleado")
    void modificar_deberiaRetornarOk() throws Exception {
        Empleado empleado = new Empleado(1L, "Luis", "Rojas", "Vendedor", "912345678", "luis@mail.com", "1234", 2L);
        when(empleadoService.update(1L, empleado)).thenReturn(empleado);

        mockMvc.perform(put("/api/v1/empleados/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(empleado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cargo").value("Vendedor"));
    }

    @Test
    @DisplayName("Debe eliminar empleado")
    void eliminar_deberiaRetornarNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/empleados/1"))
                .andExpect(status().isNoContent());

        verify(empleadoService).delete(1L);
    }
}
