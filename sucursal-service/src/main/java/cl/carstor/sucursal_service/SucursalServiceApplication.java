package cl.carstor.sucursal_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@OpenAPIDefinition(
		info = @Info(
				title = "Sucursal Service API",
				version = "1.0.0",
				description = "Microservicio de gestion de sucursales. Autor: Duoc",
				contact = @Contact(name = "Autor: Duoc")
		)
)
@SpringBootApplication

public class SucursalServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SucursalServiceApplication.class, args);
	}

}
