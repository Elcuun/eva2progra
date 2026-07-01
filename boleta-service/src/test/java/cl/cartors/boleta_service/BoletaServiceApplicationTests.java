package cl.cartors.boleta_service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class BoletaServiceApplicationTests {

	@Test
	void applicationMainClassExiste() {
		assertDoesNotThrow(() -> Class.forName("cl.cartors.boleta_service.BoletaServiceApplication"));
	}

}
