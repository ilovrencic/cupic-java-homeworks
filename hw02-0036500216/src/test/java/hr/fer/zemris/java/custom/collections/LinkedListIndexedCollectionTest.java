package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.*;

import org.junit.Test;

public class LinkedListIndexedCollectionTest {
	
	Integer i1 = Integer.valueOf(1);
	Integer i2 = Integer.valueOf(2);
	Integer i3 = Integer.valueOf(3);
	Integer i4 = Integer.valueOf(4);
	Integer i5 = Integer.valueOf(5);

	@Test
	public void testSize() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		assertEquals(collection.size(),0);
		
		collection.add(i1);
		collection.add(i2);
		assertEquals(collection.size(),2);
	}

	@Test
	public void testAdd() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		assertEquals(collection.contains(i1),false);
		collection.add(i1);
		assertEquals(collection.contains(i1),true);
	}

	@Test
	public void testContains() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		collection.add(i1);
		collection.add(i2);
		assertEquals(collection.contains(i1),true);
		assertEquals(collection.contains(i3),false);
	}

	@Test
	public void testRemoveObject() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		
		collection.add(i1);
		collection.add(i2);
		assertEquals(collection.contains(i1),true);
		
		collection.remove(i1);
		assertEquals(collection.contains(i1),false);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testToArray() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		
		collection.add(i1);
		collection.add(i2);
		Object[] elements = {i1,i2};
		
		assertEquals(elements,collection.toArray());
	}

	@Test
	public void testClear() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		
		collection.add(i1);
		collection.add(i2);
		assertEquals(collection.size(),2);
		
		collection.clear();
		assertEquals(collection.size(),0);
		assertEquals(collection.contains(i1),false);
	}

	@Test
	public void testLinkedListIndexedCollection() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertEquals(collection.size(),0);
	}

	@Test
	public void testGet() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		
		collection.add(i1);
		collection.add(i2);
		assertEquals(collection.get(0),i1);
		assertEquals(collection.get(1),i2);
	}

	@Test
	public void testInsertAt() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		
		collection.add(i1);
		collection.add(i2);	
		collection.add(i3);
		collection.add(i4);
		
		assertEquals(collection.get(1),i2);
		collection.insertAt(i5,1);
		assertEquals(collection.get(1),i5);
	}

	@Test
	public void testIndexOf() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		
		collection.add(i1);
		collection.add(i2);	
		collection.add(i3);
		collection.add(i4);
		
		assertEquals(collection.indexOf(i1),0);
		assertEquals(collection.indexOf(i2),1);
		assertEquals(collection.indexOf(i3),2);
		assertEquals(collection.indexOf(i4),3);
	}

	@Test
	public void testRemoveInt() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		
		collection.add(i1);
		collection.add(i2);	
		collection.add(i3);
		collection.add(i4);
		
		assertEquals(collection.indexOf(i3),2);
		collection.remove(2);
		assertNotEquals(collection.indexOf(i3),2);
	}
	
	@Test(expected = NullPointerException.class)
	public void testNullAdd() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testNullInsert() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.insertAt(null,1);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testInsertTooBigIndex() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.insertAt(i1,2);
	}


}
