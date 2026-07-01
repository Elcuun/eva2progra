package cl.carstor.config_server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ConfigServerApplicationTests {

	@Test
	void applicationMainClassExiste() {
		assertDoesNotThrow(() -> Class.forName("cl.carstor.config_server.ConfigServerApplication"));
	}

}
