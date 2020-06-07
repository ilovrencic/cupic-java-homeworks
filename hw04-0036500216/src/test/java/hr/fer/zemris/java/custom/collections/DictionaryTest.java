package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DictionaryTest {

	@Test
	void testDictionary() {
		Dictionary<String, Integer> map = new Dictionary<>();

		assertEquals(map.size(), 0);
		assertEquals(map.isEmpty(), true);
	}

	@Test
	void testClear() {
		Dictionary<String, String> map = new Dictionary<String, String>();
		map.put("Petar", "Preradović");
		map.put("Marko", "Čupić");
		map.put("Stipe", "Mesić");

		assertEquals(map.size(), 3);
		assertEquals(map.isEmpty(), false);

		map.clear();

		assertEquals(map.size(), 0);
		assertEquals(map.isEmpty(), true);
	}

	@Test
	void testPutAndGet() {
		Dictionary<String, String> map = new Dictionary<String, String>();
		map.put("Petar", "Preradović");
		map.put("Marko", "Čupić");
		map.put("Stipe", "Mesić");

		assertEquals(map.get("Petar"), "Preradović");

		map.put("Petar", "Grašo");

		assertEquals(map.get("Petar"), "Grašo");

		map.clear();

		assertEquals(map.get("Petar"), null);

		Assertions.assertThrows(NullPointerException.class, () -> map.put(null, null));
		Assertions.assertThrows(NullPointerException.class, () -> map.get(null));
	}
}
