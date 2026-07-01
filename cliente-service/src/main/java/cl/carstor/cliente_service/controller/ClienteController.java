package cl.carstor.cliente_service.controller;

import cl.carstor.cliente_service.model.Cliente;
import cl.carstor.cliente_service.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/clientes")
@Tag(name = "Clientes", description = "Gestion de clientes de Carstor")

public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @Operation(summary = "Listar clientes", description = "Consulta todos los clientes. Se diferencia de buscar por id porque no filtra por un cliente especifico.")
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok(clienteService.findall());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por id", description = "Consulta un solo cliente usando su id. Se diferencia de listar porque retorna un registro especifico.")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        if (clienteService.findByid(id) == null) return  ResponseEntity.notFound().build();
        return ResponseEntity.ok(clienteService.findByid(id));
    }

    @PostMapping
    @Operation(summary = "Registrar cliente", description = "Crea un nuevo cliente. Se diferencia de modificar porque genera un registro nuevo y no actualiza uno existente.")
    public ResponseEntity<?> RegistrarCliente(@Valid @RequestBody Cliente cliente) {
        return new ResponseEntity<>(clienteService.save(cliente), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente existente por id. Se diferencia de modificar porque remueve el registro en lugar de actualizar sus datos.")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar cliente", description = "Actualiza los datos de un cliente existente. Se diferencia de registrar porque requiere un id ya creado.")
    public  ResponseEntity<?> modificar(@Valid @PathVariable Long id, @RequestBody Cliente cliente){
        return ResponseEntity.ok(clienteService.modificar(id, cliente));
    }

    @GetMapping("/buscar-por-email/{email}")
    @Operation(summary = "Buscar cliente por email", description = "Consulta un cliente usando su email. Se diferencia de buscar por id porque usa un dato unico de contacto como criterio.")
    public ResponseEntity<?> buscarPorEmail(@PathVariable String email){
        return ResponseEntity.ok(clienteService.findByEmail(email));
    }

}
