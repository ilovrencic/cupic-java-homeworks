package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Concrete parameterized implementation of class Collection
 * 
 * @author ilovrencic
 *
 */
public class ArrayIndexedCollection<T> implements List<T> {

	private T[] elements;
	private long modificationCount = 0L;
	private int size = 0;
	private static final int DEFAULT_CAPACITY = 16;

	/**
	 * Default constructor that initializes array to default capacity
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructor that initializes array to set value of @value initalCapcity If
	 * the @value initalCapacity is lower than 1, the method will throw
	 * IllegalArgumentException
	 * 
	 * @param initalCapacity initial value of collection capacity
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initalCapacity) {
		if (initalCapacity < 1) {
			throw new IllegalArgumentException();
		}
		this.elements = (T[])new Object[initalCapacity];
	}

	/**
	 * Constructor that initializes array to the size of the collection we are
	 * passing
	 * 
	 * @param collection instance we are passing through the constructor
	 */
	public ArrayIndexedCollection(Collection<T> collection) {
		this(collection, DEFAULT_CAPACITY);
	}

	/**
	 * Constructor that initializes array to the initalCapacity, but in the case
	 * that the collection is bigger than initalCapacity - Capacity of the
	 * collection will be the size of the passed collection.
	 * 
	 * @param collection     instance we are passing through the constructor
	 * @param initalCapacity initial value for Collection capacity
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<T> collection, int initalCapacity) {
		if (collection == null) {
			throw new NullPointerException("Passed collection can't be null value!");
		}

		int capacityOfOtherCollection = collection.size();

		if (capacityOfOtherCollection > initalCapacity) {
			initalCapacity = capacityOfOtherCollection;
		}

		this.elements = (T[])new Object[initalCapacity];
		this.addAll(collection);
	}

	/**
	 * Method used for adding new value to Collection. If the value is null it
	 * throws NullPointerException. If there is not enough space to add value,
	 * collection will call ensureCapacity() method that will double the collection
	 * capacity.
	 * 
	 * @param value - Value we want to add into Collection.
	 */
	@Override
	public void add(T value) {
		if (value == null) {
			throw new NullPointerException("Passed object can't be null value!");
		}

		ensureCapacity();

		elements[size] = value;
		size++;
		modificationCount++;
	}

	/**
	 * Method that check whether the collection contains @param value.
	 * 
	 * @param value - The value for what we want to check if it is in collection.
	 */
	@Override
	public boolean contains(Object value) {
		return this.indexOf(value) != -1;
	}

	/**
	 * Method that returns the number of elements inside the collection.
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * Method that returns the length of the elements array
	 * 
	 * @return length of elements array
	 */
	public int capacity() {
		return this.elements.length;
	}

	/**
	 * Method that returns an element on index position. If an index is smaller than
	 * zero or larger than size, the method will throw IndexOutOfBoundsException.
	 * 
	 * @param index of the object we want to retrieve
	 * @return object if the index is inside allowed scope.
	 */
	@Override
	public T get(int index) {
		if (index < 0 || index > this.size()) {
			throw new IndexOutOfBoundsException();
		}

		return this.elements[index];
	}

	/**
	 * Method that removes all the elements from the collection.
	 */
	@Override
	public void clear() {
		for (int i = 0; i < this.size; i++) {
			this.elements[i] = null;
		}
		this.size = 0;
		modificationCount++;
	}

	/**
	 * Method that inserts element to certain position in collection.
	 * 
	 * @param value we want to insert
	 * @param index where we want to insert value
	 */
	@Override
	public void insert(T value, int index) {
		if (value == null) {
			throw new NullPointerException("Passed value can't be null!");
		}

		if (index > size - 1) {
			throw new IndexOutOfBoundsException();
		}

		ensureCapacity();

		for (int i = size; i > index; i--) {
			elements[i] = elements[i - 1];
		}

		elements[index] = value;
		size++;
		modificationCount++;
	}

	/**
	 * Method that returns the index of the value if collection. If the value is
	 * null or it can't be found, the method returns -1.
	 * 
	 * @param value
	 * @return
	 */
	@Override
	public int indexOf(Object value) {
		if (value == null) {
			return -1;
		}

		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Method removes an object at the given index.
	 * 
	 * @param index of the object we want to remove
	 */
	@Override
	public void remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		for (int i = index; i < size; i++) {
			elements[i] = elements[i + 1];
		}

		elements[size - 1] = null;
		size--;
		modificationCount++;
	}

	/**
	 * Method returns an array of all elements in the collection.
	 */
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size);
	}

	/**
	 * Method that removes an object value from the collection.
	 */
	@Override
	public boolean remove(Object value) {
		int index = this.indexOf(value);

		if (index == -1) {
			return false;
		}

		modificationCount++;
		this.remove(index);
		return true;
	}

	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new ArrayElementsGetter<T>(this);
	}

	/**
	 * This method ensures that the capacity of elements is enough to accept new
	 * element. It doubles the size of the array.
	 */
	private void ensureCapacity() {
		if (this.size >= this.elements.length - 1) {
			this.elements = Arrays.copyOf(this.elements, this.elements.length * 2);
		}
	}

	/**
	 * Concrete implementation of ElementsGetter interface that works with
	 * ArrayIndexedCollection.
	 * 
	 * @author ilovrencic
	 *
	 */
	private static class ArrayElementsGetter<T> implements ElementsGetter<T> {

		private T[] elements;
		private ArrayIndexedCollection<T> collection;
		private long savedModificationCount;
		private int counter;

		/**
		 * Default constructor for LinkedElementsGetter. We are passing collection
		 * reference, but we are only using it to get new data for modificationCount.
		 * 
		 * @param collection reference
		 */
		public ArrayElementsGetter(ArrayIndexedCollection<T> collection) {
			this.elements = collection.elements;
			this.savedModificationCount = collection.modificationCount;
			this.collection = collection;
			this.counter = 0;
		}

		/**
		 * Checks whether there is next element in the list. Throws
		 * {@link ConcurrentModificationException} if the collection has been in any way
		 * changed.
		 */
		@Override
		public boolean hasNextElement() {
			if (savedModificationCount != collection.modificationCount) {
				throw new ConcurrentModificationException();
			}
			if (counter == elements.length - 1 || elements[counter] == null) {
				return false;
			}
			return true;
		}

		/**
		 * Returns next element in the list, if there is one. Otherwise, it throws
		 * {@link NoSuchElementException}
		 */
		@Override
		public T getNextElement() throws NoSuchElementException {
			if (savedModificationCount != collection.modificationCount) {
				throw new ConcurrentModificationException();
			}

			if (!hasNextElement()) {
				throw new NoSuchElementException();
			}

			T object = elements[counter];
			counter++;
			return object;
		}
	}
}
