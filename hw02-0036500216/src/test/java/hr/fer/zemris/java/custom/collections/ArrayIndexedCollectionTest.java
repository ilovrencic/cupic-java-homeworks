package hr.fer.zemris.java.custom.collections;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayIndexedCollectionTest {
	
	Integer i1 = Integer.valueOf(1);
	Integer i2 = Integer.valueOf(2);
	Integer i3 = Integer.valueOf(3);
	Integer i4 = Integer.valueOf(4);
	Integer i5 = Integer.valueOf(5);
	
	@Test
	public void testIsEmpty() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertEquals(true,collection.isEmpty());
		
		collection.add(i1);
		assertEquals(false,collection.isEmpty());
	}

	@Test
	public void testSize() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertEquals(collection.size(),0);
		
		collection.add(i1);
		collection.add(i2);
		assertEquals(collection.size(),2);
	}

	@Test
	public void testAdd() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();

		assertEquals(collection.contains(i1),false);
		collection.add(i1);
		assertEquals(collection.contains(i1),true);
	}

	@Test
	public void testContains() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		
		collection.add(i1);
		collection.add(i2);
		assertEquals(collection.contains(i1),true);
		assertEquals(collection.contains(i3),false);
	}

	@Test
	public void testRemoveObject() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		
		collection.add(i1);
		collection.add(i2);
		assertEquals(collection.contains(i1),true);
		
		collection.remove(i1);
		assertEquals(collection.contains(i1),false);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testToArray() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		
		collection.add(i1);
		collection.add(i2);
		Object[] elements = {i1,i2};
		
		assertEquals(elements,collection.toArray());
	}

	@Test
	public void testClear() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		
		collection.add(i1);
		collection.add(i2);
		assertEquals(collection.size(),2);
		
		collection.clear();
		assertEquals(collection.size(),0);
		assertEquals(collection.contains(i1),false);
	}

	@Test
	public void testArrayIndexedCollection() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertEquals(collection.capacity(),16);
	}

	@Test
	public void testArrayIndexedCollectionInt() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(25);
		assertEquals(collection.capacity(),25);
	}

	@Test
	public void testGet() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		
		collection.add(i1);
		collection.add(i2);
		assertEquals(collection.get(0),i1);
		assertEquals(collection.get(1),i2);
	}

	@Test
	public void testInsert() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		
		collection.add(i1);
		collection.add(i2);	
		collection.add(i3);
		collection.add(i4);
		
		assertEquals(collection.get(1),i2);
		collection.insert(i5,1);
		assertEquals(collection.get(1),i5);
	}

	@Test
	public void testIndexOf() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		
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
	public void testEnsureCapacity() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
		
		collection.add(i1);
		collection.add(i2);	
		collection.add(i3);
		collection.add(i4);
		
		assertEquals(collection.size(),4);
	}

	@Test
	public void testRemoveInt() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		
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
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testNullInsert() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.insert(null,1);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testInsertTooBigIndex() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.insert(i1,2);
	}
}
