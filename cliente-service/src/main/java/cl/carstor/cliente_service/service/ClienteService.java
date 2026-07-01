package cl.carstor.cliente_service.service;

import cl.carstor.cliente_service.dto.ClienteDTO;
import cl.carstor.cliente_service.exception.TelefonoInvalidoException;
import cl.carstor.cliente_service.mapper.ClienteMapper;
import cl.carstor.cliente_service.model.Cliente;
import cl.carstor.cliente_service.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    public List<ClienteDTO> findall(){
        List<Cliente> listado = clienteRepository.findAll(); return listado.stream().map(clienteMapper::toDTO).toList();

    }

    public ClienteDTO findByid(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        return clienteMapper.toDTO(cliente);

    }
    public Cliente save(Cliente cliente){
        if(cliente.getTelefono().length() != 9){
            throw new TelefonoInvalidoException(
                    "El telfono debe tener 9 caracteres"

            );
        }
        return  clienteRepository.save(cliente);

    }

    public void delete (Long id){
        clienteRepository.deleteById(id);

    }
    public Cliente modificar(Long id, Cliente cliente){
        Cliente clienteActualizado = clienteRepository.findById(id).orElse(null);
        if(clienteActualizado == null) return null;
        clienteActualizado.setNombre(cliente.getNombre());
        clienteActualizado.setApellido(cliente.getApellido());
        clienteActualizado.setTelefono(cliente.getTelefono());
        clienteActualizado.setEmail(cliente.getEmail());
        clienteActualizado.setPassword(cliente.getPassword());
        clienteActualizado.setDireccion(cliente.getDireccion());

        return clienteRepository.save(clienteActualizado);


    }
    public ClienteDTO findByEmail(String email){
        Cliente cliente = clienteRepository.findByEmail(email);
        return clienteMapper.toDTO( cliente);
    }




}
