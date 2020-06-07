package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class that represents a simple implementation of hashtable.
 * 
 * @author ilovrencic
 *
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * Represent the number of elements in the table
	 */
	private int size;

	/**
	 * Variable that counts how many times has instance been structurally modified.
	 */
	private int modificationCount = 0;

	/**
	 * The table that stores all the entries in the {@link SimpleHashtable}
	 */
	private TableEntry<K, V>[] table;

	/**
	 * Size of the default table
	 */
	private static final int DEFAULT_TABLE = 16;

	/**
	 * Default constructor that initializes the table with 16 available slots.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		table = new TableEntry[DEFAULT_TABLE];
		size = 0;
	}

	/**
	 * Default constructor that initializes the table with first equal or bigger
	 * power of 2 than the passed @param capacity.
	 * 
	 * @param capacity - number of slots we want
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException("Capacity can't be lower than one!");
		}

		capacity = powerOfTwo(capacity);
		table = new TableEntry[capacity];
		size = 0;
	}

	/**
	 * Method that returns number of elements inside {@link SimpleHashtable}
	 * 
	 * @return number of elements
	 */
	public int size() {
		return size;
	}

	/**
	 * Method that puts a new {@link TableEntry} into {@link SimpleHashtable}. If
	 * there is a {@link TableEntry} with the same key, than the value is just
	 * replaced with the new passed value.
	 * 
	 * @param key
	 * @param value
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new NullPointerException("Key can't be null value!");
		}

		ensureCapacity();

		TableEntry<K, V> entry = new TableEntry<>(key, value);
		int slot = Math.abs(key.hashCode()) % table.length;

		if (table[slot] == null) { // checking whether the slot is empty
			table[slot] = entry;
			size++;
			modificationCount++;
			return;
		}

		TableEntry<K, V> head = table[slot];
		while (head.next != null) { // the slot wasn't empty so we have to make a linked list
			if (head.key.equals(key)) {
				head.setValue(value);
				return;
			}
			head = head.next;
		}

		if (head.key.equals(key)) { // there was an element with the same key so we are not adding, only replacing
									// value.
			head.setValue(value);
			return;
		}

		head.next = entry;
		size++;
		modificationCount++;
	}

	/**
	 * Method that returns value that corresponds with the passed key. If there is
	 * no key, the method return null.
	 * 
	 * @param key
	 * @return
	 */
	public V get(Object key) {
		if (key == null) {
			throw new NullPointerException("Key shouldn't be null!");
		}

		TableEntry<K, V> entry = find(key);
		if (entry == null) {
			return null;
		} else {
			return entry.getValue();
		}
	}

	/**
	 * Method that checks whether the {@link SimpleHashtable} contains passed key.
	 * 
	 * @param key
	 * @return true if there is such key, otherwise false
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			throw new NullPointerException("Key can't be null!");
		}

		return find(key) != null;
	}

	/**
	 * Method that checks whether the {@link SimpleHashtable} contains passed value.
	 * 
	 * @param value
	 * @return true if there is such value, otherwise false
	 */
	public boolean containsValue(Object value) {
		for (TableEntry<K, V> entry : table) {
			if (entry == null) {
				continue;
			}

			if (entry.getValue().equals(value)) {
				return true;
			}

			while (entry.next != null) {
				if (entry.getValue().equals(value)) {
					return true;
				}
				entry = entry.next;
			}
		}
		return false;
	}

	/**
	 * Method that removes a passed object from the {@link SimpleHashtable}.
	 * 
	 * @param key - key of the object we want to erase
	 */
	public void remove(Object key) {
		if (key == null) {
			throw new NullPointerException("Key can't be null!");
		}

		int slot = Math.abs(key.hashCode()) % table.length;

		if (table[slot] == null) {
			return;
		}

		TableEntry<K, V> entry = table[slot];
		if (entry.key.equals(key)) {
			table[slot] = entry.next;
			size--;
			modificationCount++;
			return;
		}

		while (entry.next != null) {
			if (entry.next.key.equals(key)) {
				break;
			}
			entry = entry.next;
		}

		if (entry.next != null) {
			entry.next = entry.next.next;
			size--;
			modificationCount++;
		}

	}

	/**
	 * Method that checks whether the {@link SimpleHashtable} is empty or not.
	 * 
	 * @return true if there is no elements, otherwise false.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public String toString() {
		String output = "[";
		if (!isEmpty()) {
			for (TableEntry<K, V> entry : table) {
				while (entry != null) {
					output += " " + entry.toString() + " ";
					entry = entry.next;
				}
			}
		}
		output += "]";
		return output;
	}

	/**
	 * Returns number of slots in the table.
	 * 
	 * @return - number of slots
	 */
	public int capacity() {
		return table.length;
	}

	/**
	 * Method that erases all elements from {@link SimpleHashtable}
	 */
	public void clear() {
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		size = 0;
		modificationCount++;
	}

	/**
	 * Method that creates {@link Iterator} for {@link SimpleHashtable}.
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Helper method that helps to find {@link TableEntry} that corresponds with
	 * passed key.
	 * 
	 * @param key
	 * @return
	 */
	private TableEntry<K, V> find(Object key) {
		int slot = Math.abs(key.hashCode()) % table.length;

		if (table[slot] == null) {
			return null;
		}

		TableEntry<K, V> entry = table[slot];
		while (entry.next != null) {
			if (entry.key.equals(key))
				return entry;
			entry = entry.next;
		}

		if (entry.key.equals(key)) {
			return entry;
		}

		return null;
	}

	/**
	 * Method that checks whether the table is occupied more than 75 percent. If
	 * that's the case then this method expands the table length by 2.
	 */
	@SuppressWarnings("unchecked")
	private void ensureCapacity() {
		if (percentageOfSlots() < 0.75) {
			return;
		}

		TableEntry<K, V>[] expendedTable = new TableEntry[table.length * 2];

		for (TableEntry<K, V> entry : table) {
			if (entry != null) {
				int slot = Math.abs(entry.key.hashCode()) % expendedTable.length;
				expendedTable[slot] = entry;
			}
		}

		table = expendedTable;
	}

	/**
	 * Method that calculates the percentage of table occupation.
	 * 
	 * @return - percentage of how much of the table is occupied.
	 */
	private double percentageOfSlots() {
		int number = 0;
		for (TableEntry<K, V> entry : table) {
			if (entry != null)
				number++;
		}
		return (double) number / table.length;
	}

	/**
	 * Function that returns first equal or bigger power of 2 value than the passed
	 * {@link Integer}.
	 * 
	 * @param capacity - capacity of the table we want
	 * @return - capacity thats first equal or bigger power of 2 than the passed
	 *         parameter
	 */
	private int powerOfTwo(int capacity) {
		int power = 1;
		while (capacity > power) {
			power *= 2;
		}
		return power;
	}

	/**
	 * Static class that represent an entry inside {@link SimpleHashtable}.
	 * 
	 * @author ilovrencic
	 *
	 * @param <K> - parameter of the key
	 * @param <V> - parameter of the value
	 */
	public static class TableEntry<K, V> {

		/**
		 * Represents the key in the entry
		 */
		private K key;

		/**
		 * Represents the value in the entry
		 */
		private V value;

		/**
		 * Represent a reference to the next {@link TableEntry}
		 */
		private TableEntry<K, V> next;

		/**
		 * Default constructor for {@link TableEntry}
		 * 
		 * @param key
		 * @param value
		 */
		public TableEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/* ------- GETTERS --------- */

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public TableEntry<K, V> getNext() {
			return next;
		}

		/* -------- SETTERS ---------- */

		public void setValue(V value) {
			this.value = value;
		}

		public void setNext(TableEntry<K, V> next) {
			this.next = next;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof TableEntry) {
				TableEntry<K, V> entry = (TableEntry<K, V>) obj;
				return entry.getKey().equals(key);
			}
			return false;
		}

		@Override
		public String toString() {
			String output = "";
			output += key.toString();
			output += "=";
			if (value == null) {
				output += "null";
			} else {
				output += value.toString();
			}
			return output;
		}
	}

	/**
	 * Implementation of the {@link Iterator} interface.
	 * 
	 * @author ilovrencic
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		/**
		 * Cursor that moves through the table in {@link SimpleHashtable}
		 */
		private int slotCursor;

		/**
		 * Variable that counts number of elements we have visited
		 */
		private int numberOfElements;

		/**
		 * Variable that shows how many times it is expected for the instance to be
		 * structurally modified.
		 */
		private int expectedModificationCount = modificationCount;

		/**
		 * Instance of the last returned element
		 */
		private TableEntry<K, V> lastReturned;

		/**
		 * Instance of the element we are currently on
		 */
		private TableEntry<K, V> head;

		/**
		 * Empty constructor
		 */
		IteratorImpl() {
		}

		/**
		 * Method that checks whether we have next element.
		 * 
		 * @return - method returns true if there is element, otherwise false
		 */
		@Override
		public boolean hasNext() {
			checkForModification();
			return numberOfElements < size;
		}

		/**
		 * Method that checks what the next element is in the table. We are doing that
		 * by moving down on the table and checking whether the there is a list in the
		 * slot. If there is a list, then we are going to move through the whole list.
		 * 
		 * @return - next element in the {@link SimpleHashtable}
		 */
		@Override
		public TableEntry<K, V> next() {
			if (!hasNext())
				throw new NoSuchElementException("No more elements in table!");

			checkForModification();

			if (head != null) { // here we are basically going through the list in the slot.
				if (head.next != null) {
					head = head.next;
					numberOfElements++;
					lastReturned = head;
					return head;
				} else {
					head = null;
					slotCursor++;
				}

			}

			while (table[slotCursor] == null && slotCursor <= table.length)
				slotCursor++;
			if (slotCursor >= table.length)
				throw new NoSuchElementException();

			numberOfElements++;
			head = table[slotCursor];
			lastReturned = head;
			return head;
		}

		/**
		 * Method that removes only the current element from the
		 * {@link SimpleHashtable}. This method does it in a safe way, so that the
		 * {@link Iterator} doesn't throw {@link ConcurrentModificationException}.
		 */
		@Override
		public void remove() {
			if (lastReturned == null) {
				throw new IllegalStateException("You can't remove more than once!");
			}

			checkForModification();

			TableEntry<K, V> entry = table[slotCursor];
			if (entry.key.equals(lastReturned.key)) {
				SimpleHashtable.this.remove(lastReturned.key);
				head = null;
				lastReturned = null;
				numberOfElements--;
				expectedModificationCount++;
				return;
			}

			while (entry.next != null) {
				if (entry.next.key.equals(lastReturned.key)) {
					break;
				}
				entry = entry.next;
			}

			SimpleHashtable.this.remove(lastReturned.key);
			head = entry;
			lastReturned = null;
			numberOfElements--;
			expectedModificationCount++;
		}

		private void checkForModification() {
			if (expectedModificationCount != modificationCount) {
				throw new ConcurrentModificationException();
			}
		}
	}
}
