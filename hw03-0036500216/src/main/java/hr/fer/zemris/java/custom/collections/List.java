package hr.fer.zemris.java.custom.collections;

/**
 * List interface that extends Collection
 * 
 * @author ilovrencic
 *
 */
public interface List extends Collection {

	/**
	 * Method that return object that on the index position.
	 * 
	 * @param index - position at which we are trying to get object
	 * @return - object if there is one
	 */
	Object get(int index);

	/**
	 * Method that inserts object at the position we want.
	 * 
	 * @param value    - object we want to insert
	 * @param position - position in collection where we want to insert it
	 */
	void insert(Object value, int position);

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
