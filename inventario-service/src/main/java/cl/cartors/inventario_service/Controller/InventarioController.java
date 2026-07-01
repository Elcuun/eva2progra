package cl.cartors.inventario_service.Controller;

import cl.cartors.inventario_service.model.Inventario;
import cl.cartors.inventario_service.service.InventarioService;
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
@RequestMapping("api/v1/inventarios")
@Tag(name = "Inventarios", description = "Gestion de inventario por producto y sucursal")
public class InventarioController {
    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    @Operation(summary = "Listar inventarios", description = "Consulta todos los registros de inventario e incluye producto y sucursal remotos. Se diferencia de los reportes porque no aplica filtros.")
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(inventarioService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar inventario por id", description = "Consulta un registro de inventario especifico por id. Se diferencia de listar porque retorna un solo registro.")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        if (inventarioService.findById(id) == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(inventarioService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Registrar inventario", description = "Crea un nuevo registro de stock para producto y sucursal. Se diferencia de modificar porque inserta un registro nuevo.")
    public ResponseEntity<?> registrarInventario(@Valid @RequestBody Inventario inventario) {
        return new ResponseEntity<>(inventarioService.save(inventario), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar inventario", description = "Elimina un registro de inventario por id. Se diferencia de modificar porque remueve el registro completo.")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        inventarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar inventario", description = "Actualiza producto, sucursal, stock y fecha de ingreso de un inventario existente. Se diferencia de registrar porque requiere un id ya creado.")
    public ResponseEntity<?> modificar(@RequestBody Inventario inventario, @PathVariable Long id) {
        return ResponseEntity.ok(inventarioService.update(id, inventario));
    }

    @GetMapping("/reportes/producto/{idProducto}")
    @Operation(summary = "Reporte de inventario por producto", description = "Lista registros de inventario asociados a un producto. Se diferencia de listar porque filtra por idProducto.")
    public ResponseEntity<?> listarPorProducto(@PathVariable Integer idProducto) {
        return ResponseEntity.ok(inventarioService.findByProducto(idProducto));
    }

    @GetMapping("/reportes/sucursal/{idSucursal}")
    @Operation(summary = "Reporte de inventario por sucursal", description = "Lista registros de inventario asociados a una sucursal. Se diferencia del reporte por producto porque filtra por ubicacion.")
    public ResponseEntity<?> listarPorSucursal(@PathVariable Integer idSucursal) {
        return ResponseEntity.ok(inventarioService.findBySucursal(idSucursal));
    }

    @GetMapping("/reportes/stock-bajo/{stock}")
    @Operation(summary = "Reporte de stock bajo", description = "Lista productos con stock menor o igual al valor indicado. Se diferencia de listar porque permite detectar inventario critico.")
    public ResponseEntity<?> listarPorStockBajo(@PathVariable Integer stock) {
        return ResponseEntity.ok(inventarioService.findByStockBajo(stock));
    }

    @GetMapping("/reportes/fecha-ingreso")
    @Operation(summary = "Reporte por fecha de ingreso", description = "Lista registros ingresados entre desde y hasta. Se diferencia de stock bajo porque filtra por periodo.")
    public ResponseEntity<?> listarPorFechaIngreso(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ResponseEntity.ok(inventarioService.findByFechaIngreso(desde, hasta));
    }
}
