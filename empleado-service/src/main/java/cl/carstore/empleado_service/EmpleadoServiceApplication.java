package cl.carstore.empleado_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@OpenAPIDefinition(
		info = @Info(
				title = "Empleado Service API",
				version = "1.0.0",
				description = "Microservicio de gestion de empleados. Autor: Duoc",
				contact = @Contact(name = "Autor: Duoc")
		)
)
@SpringBootApplication
@EnableFeignClients
public class EmpleadoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpleadoServiceApplication.class, args);
	}

}
