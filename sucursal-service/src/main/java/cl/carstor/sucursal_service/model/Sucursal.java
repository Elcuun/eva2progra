package cl.carstor.sucursal_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El nombre no puede estar vacio.")
    private  String nombre;
    @NotBlank(message = "La direccion no puede estar vacio.")
    private String direccion;
    @NotBlank(message = "El telefono no puede estar vacio.")
    @Size(min = 9, max = 9, message = "El telefono debe tener 9 caracteres")
    private String telefono;
}
