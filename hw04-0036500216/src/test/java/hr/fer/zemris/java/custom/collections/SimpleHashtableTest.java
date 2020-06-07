package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.collections.SimpleHashtable.TableEntry;

class SimpleHashtableTest {

	@Test
	void testSimpleHashtable() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>();

		assertEquals(table.size(), 0);
		assertEquals(table.capacity(), 16);
	}

	@Test
	void testSimpleHashtableInt() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(30);

		assertEquals(table.size(), 0);
		assertEquals(table.capacity(), 32);
	}

	@Test
	void testSize() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>();

		assertEquals(table.size(), 0);
		assertEquals(table.capacity(), 16);

		table.put("Ivan", 10);
		table.put("Petar", 2);

		assertEquals(table.size(), 2);
		assertEquals(table.capacity(), 16);

		table.put("Ratko", 3210);
		table.put("Šime", 22);

		assertEquals(table.size(), 4);
		assertEquals(table.capacity(), 16);
	}

	@Test
	void testGetAndPut() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>();

		table.put("Ivan", 10);
		table.put("Petar", 2);
		table.put("Ratko", 3210);
		table.put("Šime", 22);

		assertEquals(table.get("Ivan"), 10);
		assertEquals(table.get("Petar"), 2);

		table.put("Ivan", 420);
		table.put("Petar", 69);

		assertEquals(table.get("Ivan"), 420);
		assertEquals(table.get("Petar"), 69);

		Assertions.assertThrows(NullPointerException.class, () -> table.put(null, null));
	}

	@Test
	void testContainsKey() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>();

		table.put("Ivan", 10);
		table.put("Petar", 2);
		table.put("Ratko", 3210);
		table.put("Šime", 22);

		assertTrue(table.containsKey("Ratko"));
		assertFalse(table.containsKey("Luka"));
	}

	@Test
	void testContainsValue() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>();

		table.put("Ivan", 10);
		table.put("Petar", 2);
		table.put("Ratko", 3210);
		table.put("Šime", 22);

		assertTrue(table.containsValue(3210));
		assertFalse(table.containsValue(420));

		table.put("Ivan", 420);
		table.put("Ratko", 69);

		assertTrue(table.containsValue(420));
		assertFalse(table.containsValue(3210));
	}

	@Test
	void testRemove() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>();

		table.put("Ivan", 10);
		table.put("Petar", 2);
		table.put("Ratko", 3210);
		table.put("Šime", 22);

		assertTrue(table.containsKey("Ratko"));
		assertEquals(table.size(), 4);

		table.remove("Ratko");
		table.remove("Ivan");
		table.remove("Šime");
		table.remove("Petar");

		assertFalse(table.containsKey("Ratko"));
		assertEquals(table.size(), 0);

		Assertions.assertThrows(NullPointerException.class, () -> table.remove(null));

	}

	@Test
	void testIsEmpty() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>();

		table.put("Ivan", 10);
		table.put("Petar", 2);
		table.put("Ratko", 3210);
		table.put("Šime", 22);

		assertFalse(table.isEmpty());

		table.remove("Ratko");
		table.remove("Ivan");
		table.remove("Šime");
		table.remove("Petar");

		assertTrue(table.isEmpty());
	}

	@Test
	void testEnsureCapacity() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(4);

		assertEquals(table.capacity(), 4);
		assertEquals(table.size(), 0);

		table.put("Ivan", 10);
		table.put("Petar", 2);
		table.put("Ratko", 3210);
		table.put("Šime", 22);

		assertEquals(table.capacity(), 8);
		assertEquals(table.size(), 4);

		table.put("Iva", 10);
		table.put("Pero", 2);
		table.put("Ratimir", 3210);
		table.put("Šimun", 22);

		assertEquals(table.capacity(), 8);
		assertEquals(table.size(), 8);

		table.put("Ivo", 10);
		table.put("Pera", 2);
		table.put("Ratomir", 3210);
		table.put("Šimo", 22);

		assertEquals(table.capacity(), 16);
		assertEquals(table.size(), 12);
	}

	@Test
	void testClear() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(4);

		assertEquals(table.size(), 0);
		assertEquals(table.capacity(), 4);

		table.put("Ivan", 10);
		table.put("Petar", 2);
		table.put("Ratko", 3210);
		table.put("Šime", 22);

		assertEquals(table.size(), 4);
		assertEquals(table.capacity(), 8);

		table.clear();

		assertEquals(table.size(), 0);
		assertEquals(table.capacity(), 8);

		table.put("Petar", 2);
		table.put("Ratko", 3210);

		assertEquals(table.size(), 2);
		assertEquals(table.capacity(), 8);
	}

	@Test
	void testIterator() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(4);

		assertEquals(table.size(), 0);

		table.put("Ivana", 2);
		table.put("Ante", 2);
		table.put("Jasna", 2);
		table.put("Kristina", 5);

		Iterator<TableEntry<String, Integer>> iter1 = table.iterator();

		assertEquals(iter1.next().getKey(), "Ante");
		assertEquals(iter1.next().getKey(), "Ivana");
		assertEquals(iter1.next().getKey(), "Jasna");
		assertEquals(iter1.next().getKey(), "Kristina");

		Assertions.assertThrows(NoSuchElementException.class, () -> iter1.next());

		Iterator<TableEntry<String, Integer>> iter2 = table.iterator();

		iter2.next();
		iter2.next();

		table.remove("Jasna");

		Assertions.assertThrows(ConcurrentModificationException.class, () -> iter2.next());

		Iterator<TableEntry<String, Integer>> iter3 = table.iterator();

		iter3.next();
		iter3.remove();

		iter3.next();
		iter3.remove();

		iter3.next();
		iter3.remove();

		// only three removals because we have removed "Jasna" in a example above.
		assertEquals(table.size(), 0);
		
		table.put("Ivana", 2);
		table.put("Ante", 2);
		table.put("Jasna", 2);
		table.put("Kristina", 5);
		
		Iterator<TableEntry<String, Integer>> iter4 = table.iterator();
		
		iter4.next();
		iter4.remove();
		
		Assertions.assertThrows(IllegalStateException.class, () -> iter4.remove());

	}
}
