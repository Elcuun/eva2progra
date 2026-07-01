package cl.carstore.producto_service.controller;

import cl.carstore.producto_service.model.Producto;
import cl.carstore.producto_service.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/productos")
@Tag(name = "Productos", description = "Gestion de vehiculos disponibles para venta")

public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @Operation(
            summary = "Listar productos",
            description = "Obtiene todos los vehiculos registrados. Se diferencia de buscar por id porque no filtra un producto especifico.",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Listado obtenido correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    [{"marca":"Toyota","modelo":"Corolla","anio":2023,"color":"Rojo","precio":14500000,"estado":"DISPONIBLE"}]
                                    """)
                    )
            )
    )
    @GetMapping
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok(productoService.findall());
    }

    @Operation(
            summary = "Buscar producto por id",
            description = "Obtiene un vehiculo por su identificador. Se diferencia de listar porque retorna un solo producto.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Producto encontrado", content = @Content(schema = @Schema(implementation = Producto.class))),
                    @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        if (productoService.findByid(id) == null) return  ResponseEntity.notFound().build();
        return ResponseEntity.ok(productoService.findByid(id));
    }

    @Operation(
            summary = "Registrar producto",
            description = "Crea un nuevo vehiculo validando marca, modelo, anio, color, precio y estado. Se diferencia de modificar porque inserta un registro nuevo.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Producto creado", content = @Content(schema = @Schema(implementation = Producto.class))),
                    @ApiResponse(responseCode = "400", description = "Solicitud invalida", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<?> RegistrarProducto(@Valid @RequestBody Producto producto) {
        return new ResponseEntity<>(productoService.save(producto), HttpStatus.CREATED);
    }

    @Operation(summary = "Eliminar producto", description = "Elimina un vehiculo existente por id. Se diferencia de modificar porque remueve el registro completo.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Modificar producto", description = "Actualiza los datos de un vehiculo existente. Se diferencia de registrar porque requiere un id ya creado.")
    @PutMapping("/{id}")
    public  ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Producto producto){
        return ResponseEntity.ok(productoService.modificar(id, producto));
    }

    @Operation(summary = "Producto mas caro", description = "Obtiene el vehiculo con mayor precio registrado. Se diferencia de listar porque retorna solo el producto de precio maximo.")
    @GetMapping("/mas-caro")
    public ResponseEntity<?> productoMasCaro(){

        return ResponseEntity.ok(
                productoService.productoMasCaro()
        );
    }

    @Operation(summary = "Buscar por color", description = "Lista vehiculos filtrados por color. Se diferencia de buscar por id porque puede retornar varios productos.")
    @GetMapping("/buscar-por-color/{color}")
    public ResponseEntity<?> buscarPorColor(@PathVariable String color){

        return ResponseEntity.ok(
                productoService.findAllByColor(color)
        );
    }

    @Operation(summary = "Buscar por marca", description = "Lista vehiculos filtrados por marca. Se diferencia de buscar por color porque usa la marca como criterio de busqueda.")
    @GetMapping("/buscar-por-marca/{marca}")
    public ResponseEntity<?> buscarPorMarca(@PathVariable String marca){
        return ResponseEntity.ok(productoService.findAllByMarca(marca));
    }




}
