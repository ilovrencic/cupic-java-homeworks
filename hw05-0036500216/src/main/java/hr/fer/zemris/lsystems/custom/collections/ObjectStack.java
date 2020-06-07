package hr.fer.zemris.lsystems.custom.collections;

import java.util.EmptyStackException;

/**
 * Class that represents Adapter class in Adapter Design Pattern. Here we will
 * adapt class {@link ArrayIndexedCollection} to behave as parameterized Stack
 * 
 * @author ilovrencic
 *
 */
public class ObjectStack<T> {

	private ArrayIndexedCollection<T> collection;

	/**
	 * Constructor that initializes empty stack.
	 */
	public ObjectStack() {
		collection = new ArrayIndexedCollection<T>();
	}

	/**
	 * Method that checks whether the stack is empty.
	 * 
	 * @return true if stack is empty, otherwise false
	 */
	public boolean isEmpty() {
		return collection.isEmpty();
	}

	/**
	 * Returns the number of elements in stack.
	 * 
	 * @return
	 */
	public int size() {
		return collection.size();
	}

	/**
	 * Method that adds value on the top of stack.
	 * 
	 * @param value
	 */
	public void push(T value) {
		if (value == null) {
			throw new NullPointerException("Can't push null value!");
		}
		collection.add(value);
	}

	/**
	 * Method that returns value at the top of the stack and also removes it from
	 * the stack.
	 * 
	 * @return value from the top or exception if the stack is empty
	 */
	public Object pop() {
		if (collection.isEmpty()) {
			throw new EmptyStackException();
		}
		T value = collection.get(collection.size() - 1);
		collection.remove(collection.size() - 1);
		return value;
	}

	/**
	 * Method that returns value from the top of the stack but doesn't remove it.
	 * 
	 * @return
	 */
	public Object peek() {
		if (collection.isEmpty()) {
			throw new EmptyStackException();
		}
		return collection.get(collection.size() - 1);
	}

	/**
	 * Method that clears the whole stack and removes all elements.
	 */
	public void clear() {
		collection.clear();
	}
}
