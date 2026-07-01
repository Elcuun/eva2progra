package cl.cartors.inventario_service.service;

import cl.cartors.inventario_service.Client.ProductoFeign;
import cl.cartors.inventario_service.Client.SucursalFeign;
import cl.cartors.inventario_service.dto.InventarioDTO;
import cl.cartors.inventario_service.dto.ProductoDTO;
import cl.cartors.inventario_service.dto.SucursalDTO;
import cl.cartors.inventario_service.mapper.Inventariomapper;
import cl.cartors.inventario_service.model.Inventario;
import cl.cartors.inventario_service.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioService {
    @Autowired
    InventarioRepository inventarioRepository;
    @Autowired
    SucursalFeign sucursalFeign;
    @Autowired
    ProductoFeign productoFeign;
    @Autowired
    Inventariomapper inventariomapper;

    public List<InventarioDTO> findAll(){
        List<Inventario> listado = inventarioRepository.findAll();

        return listado.stream().map(inventario -> {

            InventarioDTO dto = new InventarioDTO();

            dto.setIdInventario(inventario.getIdInventario());
            dto.setFechaIngreso(inventario.getFechaIngreso());
            dto.setStock(inventario.getStock());

            ProductoDTO producto =
                    productoFeign.obtenerProducto(inventario.getIdProducto());

            SucursalDTO sucursal =
                    sucursalFeign.obtenerSucursal(inventario.getIdSucursal());

            dto.setProducto(producto);
            dto.setSucursal(sucursal);

            return dto;

        }).toList();

    }
    public InventarioDTO findById(Long id){
        Inventario inventario = inventarioRepository.findById(id).orElse(null);
        InventarioDTO dto = new InventarioDTO();
        dto.setStock(
                inventario.getStock());

        dto.setFechaIngreso(
                inventario.getFechaIngreso());

        ProductoDTO producto = productoFeign.obtenerProducto(inventario.getIdProducto());
        SucursalDTO sucursal = sucursalFeign.obtenerSucursal(inventario.getIdSucursal());
        dto.setProducto(producto);
        dto.setSucursal(sucursal);
        return dto;

    }
    public Inventario save(Inventario inventario){
        return inventarioRepository.save(inventario);

    }
    public void  delete(Long id){
        inventarioRepository.deleteById(id);

    }
    public Inventario update(Long id, Inventario inventario){
        Inventario inventarioActualizado = inventarioRepository.findById(id).orElse(null);
        if (inventarioActualizado == null ) return null;

        inventarioActualizado.setIdInventario(inventario.getIdInventario());
        inventarioActualizado.setIdSucursal(inventario.getIdSucursal());
        inventarioActualizado.setIdProducto(inventario.getIdProducto());
        inventarioActualizado.setStock(inventario.getStock());
        inventarioActualizado.setFechaIngreso(inventario.getFechaIngreso());
        return inventarioRepository.save(inventarioActualizado);

    }

    public List<Inventario> findByProducto(Integer idProducto) {
        return inventarioRepository.findByIdProducto(idProducto);
    }

    public List<Inventario> findBySucursal(Integer idSucursal) {
        return inventarioRepository.findByIdSucursal(idSucursal);
    }

    public List<Inventario> findByStockBajo(Integer stock) {
        return inventarioRepository.findByStockLessThanEqual(stock);
    }

    public List<Inventario> findByFechaIngreso(java.time.LocalDate desde, java.time.LocalDate hasta) {
        return inventarioRepository.findByFechaIngresoBetween(desde, hasta);
    }

}

