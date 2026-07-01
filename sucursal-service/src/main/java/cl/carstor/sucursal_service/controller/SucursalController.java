package cl.carstor.sucursal_service.controller;

import cl.carstor.sucursal_service.model.Sucursal;
import cl.carstor.sucursal_service.service.SucursalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/sucursales")
@Tag(name = "Sucursales", description = "Gestion de sucursales de Carstor")
public class SucursalController {
    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    @Operation(summary = "Listar sucursales", description = "Consulta todas las sucursales registradas. Se diferencia de buscar por id porque no filtra una sucursal especifica.")
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok(sucursalService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sucursal por id", description = "Consulta una sucursal especifica por id. Se diferencia de listar porque retorna un solo registro.")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        if (sucursalService.findById(id) == null) return  ResponseEntity.notFound().build();
        return ResponseEntity.ok(sucursalService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Registrar sucursal", description = "Crea una nueva sucursal. Se diferencia de modificar porque inserta un registro nuevo.")
    public ResponseEntity<?> RegistrarSucursal(@Valid @RequestBody Sucursal sucursal) {
        return new ResponseEntity<>(sucursalService.save(sucursal), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar sucursal", description = "Elimina una sucursal existente por id. Se diferencia de modificar porque remueve el registro.")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        sucursalService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar sucursal", description = "Actualiza los datos de una sucursal existente. Se diferencia de registrar porque requiere un id previamente creado.")
    public  ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Sucursal sucursal){
        return ResponseEntity.ok(sucursalService.update(id, sucursal));
    }

}
