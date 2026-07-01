package cl.cartors.inventario_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@OpenAPIDefinition(
		info = @Info(
				title = "Inventario Service API",
				version = "1.0.0",
				description = "Microservicio de gestion de inventario. Autor: Duoc",
				contact = @Contact(name = "Autor: Duoc")
		)
)
@SpringBootApplication
@EnableDiscoveryClient
public class InventarioServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventarioServiceApplication.class, args);
	}

}
