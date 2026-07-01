package cl.carstore.producto_service.service;

import cl.carstore.producto_service.dto.ProductoDTO;
import cl.carstore.producto_service.mapper.ProductoMapper;
import cl.carstore.producto_service.model.Producto;
import cl.carstore.producto_service.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private ProductoMapper productoMapper;

    public List<ProductoDTO> findall(){
        List<Producto> listado = productoRepository.findAll(); return listado.stream().map(productoMapper::toDTO).toList();
    }

    public ProductoDTO findByid(Long id) {
        Producto producto = productoRepository.findById(id).orElse(null);
        return productoMapper.toDTO(producto);

    }
    public Producto save(Producto producto){
        return  productoRepository.save(producto);

    }
    public void delete (Long id){
        productoRepository.deleteById(id);

    }

    public Producto modificar(Long id, Producto producto){
        Producto productoActualizado = productoRepository.findById(id).orElse(null);
        if(productoActualizado == null) return null;
        productoActualizado.setMarca(producto.getMarca());
        productoActualizado.setModelo(producto.getModelo());
        productoActualizado.setAnio(producto.getAnio());
        productoActualizado.setColor(producto.getColor());
        productoActualizado.setPrecio(producto.getPrecio());
        productoActualizado.setEstado(producto.getEstado());

        return productoRepository.save(productoActualizado);


    }

    public Producto productoMasCaro(){
        return productoRepository
                .findTopByOrderByPrecioDesc();
    }

    public List<ProductoDTO> findAllByColor(String color) {

        List<Producto> productos = productoRepository.findAllByColor(color);

        return productos.stream()
                .map(productoMapper::toDTO)
                .toList();
    }

    public List<ProductoDTO> findAllByMarca(String marca) {

        List<Producto> productos = productoRepository.findByMarca(marca);

        return productos.stream()
                .map(productoMapper::toDTO)
                .toList();
    }


}
