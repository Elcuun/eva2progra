package cl.carstore.empleado_service.controller;

import cl.carstore.empleado_service.model.Empleado;
import cl.carstore.empleado_service.service.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/empleados")
@Tag(name = "Empleados", description = "Gestion de empleados y su sucursal asociada")
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    @Operation(summary = "Listar empleados", description = "Consulta todos los empleados e incluye datos de sucursal obtenidos desde otro microservicio. Se diferencia de buscar por id porque no filtra un empleado especifico.")
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok(empleadoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar empleado por id", description = "Consulta un empleado especifico por id. Se diferencia de listar porque retorna un solo registro.")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        if (empleadoService.findById(id) == null) return  ResponseEntity.notFound().build();
        return ResponseEntity.ok(empleadoService.findById(id));
    }
    @PostMapping
    @Operation(summary = "Registrar empleado", description = "Crea un nuevo empleado asociado a una sucursal. Se diferencia de modificar porque no requiere que el empleado exista previamente.")
    public ResponseEntity<?> RegistrarEmpleado(@RequestBody Empleado empleado) {
        return new ResponseEntity<>(empleadoService.save(empleado), HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar empleado", description = "Elimina un empleado existente por id. Se diferencia de modificar porque remueve el registro completo.")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        empleadoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar empleado", description = "Actualiza los datos de un empleado existente. Se diferencia de registrar porque trabaja sobre un id ya creado.")
    public  ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Empleado empleado){
        return ResponseEntity.ok(empleadoService.update(id, empleado));
    }



}
