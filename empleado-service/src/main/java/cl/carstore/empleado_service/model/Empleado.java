package cl.carstore.empleado_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El Nombre no puede estar vacio.")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacio.")
    private String apellido;


    @NotBlank(message = "El cargo no puede estar vacio.")
    private String cargo;

    @NotBlank(message = "El telefono no puede estar vacio")
    @Size(min = 9, max = 9, message = "El telefono debe tener 9 caracteres")

    private String telefono;

    @NotBlank(message = "El email no puede estar vacio.")
    @Email(message = "Debes ingresar un email valido")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "El password no puede estar vacio.")
    private String password;

    @NotNull(message = "sucursal no puede estar vacio.")

    private Long sucursal;

}
