package hr.fer.zemris.lsystems.custom.collections;

/**
 * Parameterized list interface that extends Collection
 * 
 * @author ilovrencic
 *
 */
public interface List<T> extends Collection<T> {

	/**
	 * Method that return object that on the index position.
	 * 
	 * @param index - position at which we are trying to get object
	 * @return - parameterized object if there is one
	 */
	T get(int index);

	/**
	 * Method that inserts object at the position we want.
	 * 
	 * @param value    - parameterized object we want to insert
	 * @param position - position in collection where we want to insert it
	 */
	void insert(T value, int position);

	/**
	 * Method that return a index of passed {@link Object}
	 * 
	 * @param value - Object for which we are trying to get index in collection
	 * @return index of object in collection. If there is no object the return is -1
	 */
	int indexOf(Object value);

	/**
	 * Method that removes object at given index.
	 * 
	 * @param index - position at which we want to remove object in collection.
	 */
	void remove(int index);

}
