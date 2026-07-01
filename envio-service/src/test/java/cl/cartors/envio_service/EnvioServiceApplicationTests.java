package cl.cartors.envio_service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EnvioServiceApplicationTests {

	@Test
	void applicationMainClassExiste() {
		assertDoesNotThrow(() -> Class.forName("cl.cartors.envio_service.EnvioServiceApplication"));
	}

}
