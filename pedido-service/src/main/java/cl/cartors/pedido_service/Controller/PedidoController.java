package cl.cartors.pedido_service.Controller;

import cl.cartors.pedido_service.Service.PedidoService;
import cl.cartors.pedido_service.model.Pedido;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/pedidos")
@Tag(name = "Pedidos", description = "Gestion de pedidos y reportes comerciales")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @Operation(
            summary = "Listar pedidos",
            description = "Obtiene pedidos enriquecidos con informacion remota de cliente y empleado mediante Feign. Se diferencia de buscar por id porque no filtra un pedido especifico.",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Listado obtenido correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    [{"idpedido":1,"estado":"PENDIENTE","fechapedido":"2026-06-19","cliente":{"nombre":"Ana","apellido":"Perez"},"empleado":{"nombre":"Luis","apellido":"Rojas","sucursal":null}}]
                                    """)
                    )
            )
    )
    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(pedidoService.findAll());
    }

    @Operation(
            summary = "Buscar pedido por id",
            description = "Obtiene un pedido por su identificador e incorpora datos de cliente y empleado. Se diferencia de listar porque retorna un solo registro.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
                    @ApiResponse(responseCode = "404", description = "Pedido no encontrado", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        if (pedidoService.findById(id) == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(pedidoService.findById(id));
    }

    @Operation(
            summary = "Registrar pedido",
            description = "Crea un pedido asociado a un cliente y a un empleado. Se diferencia de modificar porque inserta un registro nuevo.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pedido creado", content = @Content(schema = @Schema(implementation = Pedido.class))),
                    @ApiResponse(responseCode = "400", description = "Solicitud invalida", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<?> registrarPedido(@RequestBody Pedido pedido) {
        return new ResponseEntity<>(pedidoService.save(pedido), HttpStatus.CREATED);
    }

    @Operation(summary = "Eliminar pedido", description = "Elimina un pedido existente por id. Se diferencia de modificar porque remueve el registro completo.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Modificar pedido", description = "Actualiza fecha, estado, cliente y empleado de un pedido existente. Se diferencia de registrar porque requiere un id ya creado.")
    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Pedido pedido) {
        return ResponseEntity.ok(pedidoService.update(id, pedido));
    }

    @Operation(summary = "Reporte por estado", description = "Lista pedidos filtrados por estado. Se diferencia de listar porque retorna solo pedidos con el estado indicado.")
    @GetMapping("/reportes/estado/{estado}")
    public ResponseEntity<?> listarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(pedidoService.findByEstado(estado));
    }

    @Operation(summary = "Reporte por cliente", description = "Lista pedidos asociados a un cliente. Se diferencia del reporte por estado porque filtra por idCliente.")
    @GetMapping("/reportes/cliente/{idCliente}")
    public ResponseEntity<?> listarPorCliente(@PathVariable Long idCliente) {
        return ResponseEntity.ok(pedidoService.findByCliente(idCliente));
    }

    @Operation(summary = "Reporte por empleado", description = "Lista pedidos asociados a un empleado. Se diferencia del reporte por cliente porque filtra por idEmpleado.")
    @GetMapping("/reportes/empleado/{idEmpleado}")
    public ResponseEntity<?> listarPorEmpleado(@PathVariable Long idEmpleado) {
        return ResponseEntity.ok(pedidoService.findByEmpleado(idEmpleado));
    }

    @Operation(summary = "Reporte por rango de fechas", description = "Lista pedidos entre las fechas enviadas en los parametros desde y hasta. Se diferencia de los otros reportes porque filtra por periodo.")
    @GetMapping("/reportes/fechas")
    public ResponseEntity<?> listarPorFechas(@RequestParam LocalDate desde, @RequestParam LocalDate hasta) {
        return ResponseEntity.ok(pedidoService.findByFecha(desde, hasta));
    }
}
