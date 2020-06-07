package hr.fer.zemris.java.custom.collections;


/**
 * Class that represents implementation of Linked List collection.
 * 
 * @author ilovrencic
 *
 */
public class LinkedListIndexedCollection extends Collection {
	
	private ListNode first;
	private ListNode last;
	private int size = 0;
	
	/**
	 * Default constructor that initializes first and last to null.
	 */
	public LinkedListIndexedCollection() {
		this.first = this.last = null;
	}
	
	/**
	 * Default constructor that copies merges passed collection with this one.
	 * @param collection
	 */
	public LinkedListIndexedCollection(Collection collection) {
		if(collection == null) {
			throw new NullPointerException("Collection can't be null!");
		}
	
		this.addAll(collection);
	}
	
	/**
	 * Method that adds value into collection in O(1) time.
	 */
	@Override
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException("Passed value can't be null!");
		}
		
		ListNode newValue = new ListNode(value);
		
		if(this.isEmpty()) {
			first = last = newValue;
		} else {
			last.next = newValue;
			newValue.previous = last;
			last = newValue;
		}
		size++;
	}
	
	/**
	 * Method that returns a object at given index.
	 * @param index - position in collection from where we want an object
	 * @return object at position index
	 */
	public Object get(int index) {
		if(index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException();
		}
		
		int currentIndex = 0;
		ListNode iterator;
		
		//if index is in the second half then use last,
		//and if it is in the first half the use first.
		if(index > (size/2+1)) {
			iterator = last;
			
			while(currentIndex != index) {
				iterator = iterator.previous;
				currentIndex++;
			}
		} else {
			iterator = first;
			
			while(currentIndex != index) {
				iterator = iterator.next;
				currentIndex++;
			}
		}
		
		return iterator.value;
	}
	
	/**
	 * Method used to clear whole collection.
	 * This method will erase all references between nodes.
	 */
	@Override
	public void clear() {
		//we have to set all references to null
		
		ListNode iterator = first;
		ListNode nextElement;
		for(int i = 0; i < size; i++) {
			iterator.previous = null;
			nextElement = iterator.next;
			iterator.next = null;
			iterator = nextElement;
		}
		
		first = last = null;
		size = 0;
	}
	
	/**
	 * Method that inserts Object value at @param index.
	 *
	 * @param value we want to insert into collection
	 * @param index at which we want to insert index into collection.
	 */
	public void insertAt(Object value, int index) {
		if(value == null) {
			throw new NullPointerException("Passed value can't be null!");
		}
		
		if(index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException();
		}
		
		ListNode newValue = new ListNode(value);
		
		if(index == 0) {
			newValue.next = first;
			first = newValue;
		}
		else if(index == size-1) {
			newValue.previous = last;
			last = newValue;
		} else {
			ListNode iterator = first;
			int currentIndex = 0;
			
			while(currentIndex != index) {
				iterator = iterator.next;
				currentIndex++;
			}
			
			newValue.next = iterator;
			newValue.previous = iterator.previous;
			iterator.previous.next = newValue;
			iterator.previous = newValue;
		}
		size++;
	}
	
	/**
	 * Method that returns index of the passed value. If there is no
	 * passed value inside collection, method return -1
	 * @param value - of the element we are trying to find inside collection
	 * @return index of the value inside collection
	 */
	public int indexOf(Object value) {
		if(value == null) {
			return -1;
		}
		
		ListNode iterator = first;
		
		for(int i = 0; i < size; i++) {
			if(iterator.value != null) {
				if(iterator.value.equals(value)) {
					return i;
				}
			}
			iterator = iterator.next;
		}
		return -1;
	}
	
	/**
	 * Method that removes a value that is 
	 * @param index
	 */
	public void remove(int index) {
		if(index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException();
		}
		
		if(index == 0) {
			first = first.next;
			
			if(size == 1) {
				last = null;
			}
		} else if(index == size-1) {
			last = last.previous;
			last.next = null;
		} else {
			ListNode iterator = first;
			int currentIndex = 0;
			while(currentIndex != index) {
				iterator = iterator.next;
				currentIndex++;
			}
			
			iterator.previous.next = iterator.next;
			iterator.next.previous = iterator.previous;
		}
		size--;
	}
	
	/**
	 * Method that checks whether the collection contains passed value.
	 */
	@Override
	public boolean contains(Object value) {
		return this.indexOf(value) != -1;
	}
	
	/**
	 * Method that returns number of elements inside the Collection.
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * Method that for each elements call Processor
	 */
	@Override
	public void forEach(Processor processor) {
		ListNode iterator = first;
		
		for(int i = 0; i < size; i++) {
			processor.process(iterator.value);
			iterator = iterator.next;
		}
	}
	
	/**
	 * Method that removes a passed object from the Collection.
	 */
	@Override
	public boolean remove(Object value) {
		int index = this.indexOf(value);
		if(index != -1) {
			this.remove(index);
			return true;
		}
		return false;
	}
	
	/**
	 * Method that creates and returns Object array based on a collection
	 * elements.
	 */
	@Override
	public Object[] toArray() {
		if(size == 0) {
			return null;
		}
		
		Object[] elements = new Object[size];
		ListNode iterator = first;
		
		for(int i = 0; i < size; i++) {
			elements[i] = iterator.value;
			iterator = iterator.next;
		}
		return elements;
	}
	
	
	
	/**
	 * Class that represents a node inside Linked List
	 * @author ilovrencic
	 *
	 */
	private static class ListNode{
		ListNode next;
		ListNode previous;
		Object value;
		
		/**
		 * Default constructor for ListNode class 
		 * @param value
		 */
		public ListNode(Object value) {
			this.value = value;
			this.next = this.previous = null;
		}
	}

}
