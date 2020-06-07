package hr.fer.zemris.java.custom.collections;

import java.util.NoSuchElementException;

/**
 * Interface that shows behavior of ElementsGetter instances
 * 
 * @author ilovrencic
 *
 */
public interface ElementsGetter<T> {

	/**
	 * Method that checks whether the collection has next element.
	 * 
	 * @return true if elements has next element,otherwise false
	 */
	boolean hasNextElement();

	/**
	 * Method that return next element from collection. If there is no element
	 * throws NoSuchElementException
	 * 
	 * @return object if there is one in collection
	 */
	T getNextElement() throws NoSuchElementException;

	/**
	 * Method that will over all remaining elements call {@link Processor}
	 * 
	 * @param p Processor instance
	 */
	default void processRemaining(Processor<T> p) {
		while (hasNextElement()) {
			p.process(getNextElement());
		}
	}

}
