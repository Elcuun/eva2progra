package cl.cartors.boleta_service.controller;

import cl.cartors.boleta_service.model.Boleta;
import cl.cartors.boleta_service.service.BoletaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/boletas")
@Tag(name = "Boletas", description = "Gestion de boletas y reportes de venta")
public class BoletaController {
    @Autowired
    private BoletaService boletaService;

    @GetMapping
    @Operation(summary = "Listar boletas", description = "Consulta todas las boletas registradas. Se diferencia de los reportes porque no aplica filtros ni calculos agregados.")
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(boletaService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar boleta por id", description = "Consulta una boleta especifica por id e incluye datos del pedido asociado. Se diferencia de listar porque retorna un solo registro.")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(boletaService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Registrar boleta", description = "Crea una nueva boleta asociada a un pedido. Se diferencia de modificar porque inserta un registro nuevo.")
    public ResponseEntity<?> registrar(@Valid @RequestBody Boleta boleta) {
        return new ResponseEntity<>(boletaService.save(boleta), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar boleta", description = "Actualiza fecha, total, metodo de pago y pedido de una boleta existente. Se diferencia de registrar porque requiere un id ya creado.")
    public ResponseEntity<?> modificar(@PathVariable Long id, @Valid @RequestBody Boleta boleta) {
        return ResponseEntity.ok(boletaService.update(id, boleta));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar boleta", description = "Elimina una boleta existente por id. Se diferencia de modificar porque remueve el registro completo.")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        boletaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reportes/metodo-pago/{metodoPago}")
    @Operation(summary = "Reporte de boletas por metodo de pago", description = "Lista boletas filtradas por metodo de pago. Se diferencia de listar porque retorna solo las boletas que coinciden con el metodo indicado.")
    public ResponseEntity<?> listarPorMetodoPago(@PathVariable String metodoPago) {
        return ResponseEntity.ok(boletaService.findByMetodoPago(metodoPago));
    }

    @GetMapping("/reportes/fechas")
    @Operation(summary = "Reporte de boletas por rango de fechas", description = "Lista boletas emitidas entre desde y hasta. Se diferencia del reporte por metodo de pago porque filtra por periodo.")
    public ResponseEntity<?> listarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ResponseEntity.ok(boletaService.findByFechaBetween(desde, hasta));
    }

    @GetMapping("/reportes/total")
    @Operation(summary = "Reporte de boletas por rango de total", description = "Lista boletas cuyo total esta entre minimo y maximo. Se diferencia de total vendido porque retorna boletas, no una suma.")
    public ResponseEntity<?> listarPorRangoTotal(@RequestParam BigDecimal minimo, @RequestParam BigDecimal maximo) {
        return ResponseEntity.ok(boletaService.findByTotalBetween(minimo, maximo));
    }

    @GetMapping("/reportes/pedido/{idPedido}")
    @Operation(summary = "Reporte de boletas por pedido", description = "Lista boletas asociadas a un pedido especifico. Se diferencia de buscar boleta por id porque el criterio es el id del pedido.")
    public ResponseEntity<?> listarPorPedido(@PathVariable Long idPedido) {
        return ResponseEntity.ok(boletaService.findByPedido(idPedido));
    }

    @GetMapping("/reportes/resumen-metodo-pago")
    @Operation(summary = "Resumen por metodo de pago", description = "Agrupa boletas por metodo de pago, cantidad y monto. Se diferencia de los listados porque retorna datos agregados.")
    public ResponseEntity<?> resumenPorMetodoPago() {
        return ResponseEntity.ok(boletaService.resumenPorMetodoPago());
    }

    @GetMapping("/reportes/total-vendido")
    @Operation(summary = "Total vendido entre fechas", description = "Calcula la suma vendida entre desde y hasta. Se diferencia del reporte por fechas porque retorna un total agregado.")
    public ResponseEntity<?> totalVendido(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ResponseEntity.ok(boletaService.totalVendidoEntreFechas(desde, hasta));
    }
}
