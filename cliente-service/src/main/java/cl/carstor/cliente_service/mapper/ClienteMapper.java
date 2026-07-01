package cl.carstor.cliente_service.mapper;

import cl.carstor.cliente_service.dto.ClienteDTO;
import cl.carstor.cliente_service.model.Cliente;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClienteMapper {
    public ClienteDTO toDTO(Cliente cliente){
        if(cliente == null) return  null;
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setTelefono(cliente.getTelefono());
        dto.setEmail(cliente.getEmail());

        return dto;
    }
    public List<ClienteDTO> toListDTO(List<Cliente> listado){

        return listado.stream()
                .map(this::toDTO)
                .toList();
    }



}
