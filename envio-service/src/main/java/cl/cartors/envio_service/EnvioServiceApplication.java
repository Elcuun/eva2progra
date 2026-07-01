package cl.cartors.envio_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@OpenAPIDefinition(
		info = @Info(
				title = "Envio Service API",
				version = "1.0.0",
				description = "Microservicio de gestion de envios. Autor: Duoc",
				contact = @Contact(name = "Autor: Duoc")
		)
)
@SpringBootApplication
public class EnvioServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnvioServiceApplication.class, args);
	}

}
