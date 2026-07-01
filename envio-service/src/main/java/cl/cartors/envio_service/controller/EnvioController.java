package cl.cartors.envio_service.controller;

import cl.cartors.envio_service.model.Envio;
import cl.cartors.envio_service.service.EnvioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/envios")
@Tag(name = "Envios", description = "Gestion de envios y reportes logisticos")
public class EnvioController {
    @Autowired
    private EnvioService envioService;

    @GetMapping
    @Operation(summary = "Listar envios", description = "Consulta todos los envios registrados. Se diferencia de los reportes porque no aplica filtros por estado, fecha, direccion o pedido.")
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(envioService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar envio por id", description = "Consulta un envio especifico por id e incluye datos del pedido asociado. Se diferencia de listar porque retorna un solo registro.")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(envioService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Registrar envio", description = "Crea un nuevo envio asociado a un pedido. Se diferencia de modificar porque inserta un registro nuevo.")
    public ResponseEntity<?> registrar(@Valid @RequestBody Envio envio) {
        return new ResponseEntity<>(envioService.save(envio), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar envio", description = "Actualiza direccion, fecha, estado y pedido de un envio existente. Se diferencia de registrar porque requiere un id ya creado.")
    public ResponseEntity<?> modificar(@PathVariable Long id, @Valid @RequestBody Envio envio) {
        return ResponseEntity.ok(envioService.update(id, envio));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar envio", description = "Elimina un envio existente por id. Se diferencia de modificar porque remueve el registro completo.")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        envioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reportes/estado/{estado}")
    @Operation(summary = "Reporte de envios por estado", description = "Lista envios filtrados por estado. Se diferencia de listar porque retorna solo los envios que coinciden con el estado indicado.")
    public ResponseEntity<?> listarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(envioService.findByEstado(estado));
    }

    @GetMapping("/reportes/fechas")
    @Operation(summary = "Reporte de envios por rango de fechas", description = "Lista envios cuya fecha se encuentra entre desde y hasta. Se diferencia del reporte por estado porque filtra por periodo.")
    public ResponseEntity<?> listarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ResponseEntity.ok(envioService.findByFechaEnvioBetween(desde, hasta));
    }

    @GetMapping("/reportes/direccion")
    @Operation(summary = "Reporte de envios por direccion", description = "Busca envios que contienen el texto indicado en la direccion. Se diferencia de buscar por id porque puede retornar varios resultados.")
    public ResponseEntity<?> listarPorDireccion(@RequestParam String texto) {
        return ResponseEntity.ok(envioService.findByDireccion(texto));
    }

    @GetMapping("/reportes/pedido/{idPedido}")
    @Operation(summary = "Reporte de envios por pedido", description = "Lista envios asociados a un pedido especifico. Se diferencia de buscar envio por id porque el criterio es el id del pedido.")
    public ResponseEntity<?> listarPorPedido(@PathVariable Long idPedido) {
        return ResponseEntity.ok(envioService.findByPedido(idPedido));
    }

    @GetMapping("/reportes/resumen-estado")
    @Operation(summary = "Resumen de envios por estado", description = "Agrupa envios por estado y entrega cantidades. Se diferencia de los listados porque retorna un resumen agregado.")
    public ResponseEntity<?> resumenPorEstado() {
        return ResponseEntity.ok(envioService.resumenPorEstado());
    }
}
