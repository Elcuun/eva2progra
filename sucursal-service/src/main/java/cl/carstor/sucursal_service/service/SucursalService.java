package cl.carstor.sucursal_service.service;

import cl.carstor.sucursal_service.exception.TelefonoInvalidoException;
import cl.carstor.sucursal_service.model.Sucursal;
import cl.carstor.sucursal_service.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalService {
    @Autowired
    private SucursalRepository sucursalRepository;

    public List<Sucursal> findAll() {
        return sucursalRepository.findAll();

    }

    public Sucursal findById(Long id){
        return sucursalRepository.findById(id).orElse(null);
    }
    public  Sucursal save(Sucursal sucursal){
        if(sucursal.getTelefono().length() != 9){
            throw new TelefonoInvalidoException(
                    "El telfono debe tener 9 caracteres"

            );
        }
        return sucursalRepository.save(sucursal);

    }

    public void delete(Long id){
        sucursalRepository.deleteById(id);
    }

    public Sucursal update(Long id, Sucursal sucursal){
        Sucursal sucursalActualizado = sucursalRepository.findById(id).orElse(null);
        if(sucursalActualizado == null) return null;
        sucursalActualizado.setNombre(sucursal.getNombre());
        sucursalActualizado.setDireccion(sucursal.getDireccion());
        sucursalActualizado.setTelefono(sucursal.getTelefono());

        return sucursalRepository.save(sucursalActualizado);

    }



}
