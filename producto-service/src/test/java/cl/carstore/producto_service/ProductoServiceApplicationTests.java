package cl.carstore.producto_service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProductoServiceApplicationTests {

	@Test
	void applicationMainClassExiste() {
		assertDoesNotThrow(() -> Class.forName("cl.carstore.producto_service.ProductoServiceApplication"));
	}

}
