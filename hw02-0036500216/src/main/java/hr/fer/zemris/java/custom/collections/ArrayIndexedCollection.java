package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * Concrete implementation of class Collection
 * 
 * @author ilovrencic
 *
 */
public class ArrayIndexedCollection extends Collection {
	
	private Object[] elements;
	private int size = 0;
	private static final int DEFAULT_CAPACITY = 16;
	
	/**
	 * Default constructor that initializes array to default capacity
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Constructor that initializes array to set value of @value initalCapcity
	 * If the @value initalCapacity is lower than 1, the method will throw IllegalArgumentException
	 * @param initalCapacity initial value of collection capacity
	 */
	public ArrayIndexedCollection(int initalCapacity) {
		if(initalCapacity < 1) {
			throw new IllegalArgumentException();
		}
		this.elements = new Object[initalCapacity];
	}
	
	/**
	 * Constructor that initializes array to the size of the collection we are passing
	 * 
	 * @param collection instance we are passing through the constructor
	 */
	public ArrayIndexedCollection(Collection collection) {
		this(collection,DEFAULT_CAPACITY);
	}
	
	/**
	 * Constructor that initializes array to the initalCapacity, but in the case that
	 * the collection is bigger than initalCapacity - Capacity of the collection will be 
	 * the size of the passed collection.
	 * 
	 * @param collection instance we are passing through the constructor
	 * @param initalCapacity initial value for Collection capacity
	 */
	public ArrayIndexedCollection(Collection collection, int initalCapacity) {
		if(collection == null) {
			throw new NullPointerException("Passed collection can't be null value!");
		}
		
		int capacityOfOtherCollection = collection.size();
		
		if(capacityOfOtherCollection > initalCapacity) {
			initalCapacity = capacityOfOtherCollection;
		}
		
		this.elements = new Object[initalCapacity];
		this.addAll(collection);
	}
	
	/**
	 * Method used for adding new value to Collection.
	 * If the value is null it throws NullPointerException.
	 * If there is not enough space to add value, collection will call ensureCapacity() method
	 * that will double the collection capacity.
	 * 
	 * @param value - Value we want to add into Collection.
	 */
	@Override
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException("Passed object can't be null value!");
		}
		
		ensureCapacity();
		
		elements[size] = value;
		size++;
	}
	
	/**
	 * Method that check whether the collection contains @param value.
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
	 * @return length of elements array
	 */
	public int capacity() {
		return this.elements.length;
	}
	
	/**
	 * Method that returns an element on index position. If 
	 * an index is smaller than zero or larger than size, the method will
	 * throw IndexOutOfBoundsException.
	 * 
	 * @param index of the object we want to retrieve
	 * @return object if the index is inside allowed scope.
	 */
	public Object get(int index) {
		if(index < 0 || index > this.size()) {
			throw new IndexOutOfBoundsException();
		}
		
		return this.elements[index];
	}
	
	/**
	 * Method that removes all the elements from the collection.
	 */
	@Override
	public void clear() {
		for(int i = 0; i < this.size; i++) {
			this.elements[i] = null;
		}
		this.size = 0;
	}
	
	/**
	 * Method that inserts element to certain position in collection.
	 * 
	 * @param value we want to insert
	 * @param index where we want to insert value
	 */
	public void insert(Object value, int index) {
		if(value == null) {
			throw new NullPointerException("Passed value can't be null!");
		}
		
		if(index > size-1) {
			throw new IndexOutOfBoundsException();
		}
		
		ensureCapacity();
		
		for( int i = size; i > index; i--) {
			elements[i] = elements[i-1];
		}
		
		elements[index] = value;
		size++;
	}
	
	/**
	 * Method that returns the index of the value if collection.
	 * If the value is null or it can't be found, the method returns -1.
	 * @param value
	 * @return
	 */
	public int indexOf(Object value) {
		if(value == null) {
			return -1;
		}
		
		for(int i = 0; i < size; i++) {
			if(elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Method removes an object at the given index.
	 * @param index of the object we want to remove
	 */
	public void remove(int index) {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		for(int i = index; i < size; i++) {
			elements[i] = elements[i+1];
		}
		
		elements[size-1] = null;
		size--;
	}
	
	/**
	 * Method returns an array of all elements in the collection.
	 */
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements,size);
	}
	
	/**
	 * Method that iterates through the collection and invokes processor for each element.
	 */
	@Override
	public void forEach(Processor processor) {
		for(int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}
	
	/**
	 * Method that removes an object value from the collection.
	 */
	@Override
	public boolean remove(Object value) {
		int index = this.indexOf(value);
		
		if(index == -1) {
			return false;
		}
		
		this.remove(index);
		return true;
	}
	
	/**
	 * This method ensures that the capacity of elements is enough to accept new element.
	 * It doubles the size of the array.
	 */
	private void ensureCapacity() {
		if(this.size >= this.elements.length-1) {
			this.elements = Arrays.copyOf(this.elements, this.elements.length*2);
		}
	}
}

