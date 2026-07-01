package cl.carstore.empleado_service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EmpleadoServiceApplicationTests {

	@Test
	void applicationMainClassExiste() {
		assertDoesNotThrow(() -> Class.forName("cl.carstore.empleado_service.EmpleadoServiceApplication"));
	}

}
