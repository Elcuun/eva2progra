package cl.carstore.producto_service.controller;

import cl.carstore.producto_service.dto.ProductoDTO;
import cl.carstore.producto_service.model.Producto;
import cl.carstore.producto_service.service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void listarRetornaProductos() throws Exception {
        ProductoDTO producto = dto("Toyota", "Corolla", 2023L, "Rojo", 14500000, "DISPONIBLE");
        when(productoService.findall()).thenReturn(List.of(producto));

        mockMvc.perform(get("/api/v1/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].marca").value("Toyota"))
                .andExpect(jsonPath("$[0].precio").value(14500000));
    }

    @Test
    void buscarPorIdRetornaNotFoundCuandoNoExiste() throws Exception {
        when(productoService.findByid(99L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/productos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void registrarProductoRetornaCreated() throws Exception {
        Producto producto = new Producto(1L, "Mazda", "CX-5", 2024L, "Azul", 22000000, "DISPONIBLE");
        when(productoService.save(producto)).thenReturn(producto);

        mockMvc.perform(post("/api/v1/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.marca").value("Mazda"));
    }

    @Test
    void modificarProductoRetornaOk() throws Exception {
        Producto producto = new Producto(1L, "Mazda", "CX-5", 2024L, "Azul", 22000000, "DISPONIBLE");
        when(productoService.modificar(1L, producto)).thenReturn(producto);

        mockMvc.perform(put("/api/v1/productos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelo").value("CX-5"));
    }

    @Test
    void eliminarProductoRetornaNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/productos/1"))
                .andExpect(status().isNoContent());

        verify(productoService).delete(1L);
    }

    private ProductoDTO dto(String marca, String modelo, Long anio, String color, int precio, String estado) {
        ProductoDTO dto = new ProductoDTO();
        dto.setMarca(marca);
        dto.setModelo(modelo);
        dto.setAnio(anio);
        dto.setColor(color);
        dto.setPrecio(precio);
        dto.setEstado(estado);
        return dto;
    }
}
