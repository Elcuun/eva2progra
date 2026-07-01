package cl.cartors.inventario_service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class InventarioServiceApplicationTests {

	@Test
	void applicationMainClassExiste() {
		assertDoesNotThrow(() -> Class.forName("cl.cartors.inventario_service.InventarioServiceApplication"));
	}

}
