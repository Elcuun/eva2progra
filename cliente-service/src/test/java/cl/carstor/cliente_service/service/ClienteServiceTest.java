package cl.carstor.cliente_service.service;

import cl.carstor.cliente_service.dto.ClienteDTO;
import cl.carstor.cliente_service.exception.TelefonoInvalidoException;
import cl.carstor.cliente_service.mapper.ClienteMapper;
import cl.carstor.cliente_service.model.Cliente;
import cl.carstor.cliente_service.repository.ClienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para ClienteService")
class ClienteServiceTest {
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ClienteMapper clienteMapper;
    @InjectMocks
    private ClienteService clienteService;

    @Test
    @DisplayName("Debe listar clientes mapeados a DTO")
    void findall_deberiaRetornarClientesDTO() {
        Cliente cliente = cliente(1L, "Ana", "Perez", "912345678", "ana@mail.com");
        ClienteDTO dto = dto(1L, "Ana", "Perez", "912345678", "ana@mail.com");
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));
        when(clienteMapper.toDTO(cliente)).thenReturn(dto);

        List<ClienteDTO> resultado = clienteService.findall();

        assertEquals(1, resultado.size());
        assertEquals("Ana", resultado.getFirst().getNombre());
        verify(clienteRepository).findAll();
    }

    @Test
    @DisplayName("Debe guardar cliente con telefono valido")
    void save_conTelefonoValido_deberiaGuardar() {
        Cliente cliente = cliente(null, "Ana", "Perez", "912345678", "ana@mail.com");
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente resultado = clienteService.save(cliente);

        assertEquals("912345678", resultado.getTelefono());
        verify(clienteRepository).save(cliente);
    }

    @Test
    @DisplayName("Debe lanzar excepcion con telefono invalido")
    void save_conTelefonoInvalido_deberiaLanzarExcepcion() {
        Cliente cliente = cliente(null, "Ana", "Perez", "123", "ana@mail.com");

        assertThrows(TelefonoInvalidoException.class, () -> clienteService.save(cliente));
        verifyNoInteractions(clienteRepository);
    }

    @Test
    @DisplayName("Debe modificar cliente existente")
    void modificar_cuandoExiste_deberiaActualizarCampos() {
        Cliente actual = cliente(1L, "Ana", "Perez", "912345678", "ana@mail.com");
        Cliente cambios = cliente(null, "Luis", "Rojas", "987654321", "luis@mail.com");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(actual));
        when(clienteRepository.save(actual)).thenReturn(actual);

        Cliente resultado = clienteService.modificar(1L, cambios);

        assertEquals("Luis", resultado.getNombre());
        assertEquals("987654321", resultado.getTelefono());
        verify(clienteRepository).save(actual);
    }

    private Cliente cliente(Long id, String nombre, String apellido, String telefono, String email) {
        return new Cliente(id, nombre, apellido, telefono, email, "1234", "Calle 1");
    }

    private ClienteDTO dto(Long id, String nombre, String apellido, String telefono, String email) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(id);
        dto.setNombre(nombre);
        dto.setApellido(apellido);
        dto.setTelefono(telefono);
        dto.setEmail(email);
        return dto;
    }
}
