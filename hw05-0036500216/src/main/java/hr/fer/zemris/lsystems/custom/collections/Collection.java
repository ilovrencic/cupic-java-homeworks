package hr.fer.zemris.lsystems.custom.collections;

/**
 * Parameterized Interface Collection that describes behaviour of our collection
 * implementations
 * 
 * @author ilovrencic
 * 
 */
public interface Collection<T> {

	/**
	 * Method that determines whether the collection is empty
	 * 
	 * @return boolean value - True if it is empty, otherwise false
	 */
	default boolean isEmpty() {
		return this.size() == 0;
	}

	/**
	 * Method merges other collection with itself. The other collection will stay
	 * unchanged. Current implementation is with local class LocalProcessor.
	 * 
	 * @param other collection we want to merge
	 */
	default void addAll(Collection<T> other) {

		/**
		 * LocalProcessor extends Processor and in its process method adds objects to
		 * current collection.
		 * 
		 * @author ilovrencic
		 *
		 */
		class LocalProcessor implements Processor<T> {
			private Collection<T> collection;

			public LocalProcessor(Collection<T> collection) {
				this.collection = collection;
			}

			/**
			 * Used for adding objects to collection in this case.
			 */
			@Override
			public void process(T obj) {
				collection.add(obj);
			}
		}

		other.forEach(new LocalProcessor(this));
	}

	/**
	 * Returns the size of the collection (in this case always zero)
	 */
	int size();

	/**
	 * Adds an parameterized value to the collection
	 * 
	 * @param value
	 */
	void add(T value);

	/**
	 * Checks whether the object is in collection
	 * 
	 * @param value object we are checking for
	 * @return boolean value - True if collection contains the object, otherwise
	 *         false
	 */
	boolean contains(Object value);

	/**
	 * Removes the object from the collection, if there is the same instance inside
	 * the collection
	 * 
	 * @param value object we want to remove
	 * @return boolean value - True if there is object inside collection and if we
	 *         successfully remove it, otherwise false
	 */
	boolean remove(Object value);

	/**
	 * Allocates new array with size equals to the size of this collections, fills
	 * it with collection content and returns the array. This method never returns
	 * null.
	 * 
	 * @return object array
	 */
	Object[] toArray();

	/**
	 * Method calls processor.process() for each processor given
	 * 
	 * @param processor Processor class that process data
	 */
	default void forEach(Processor<T> processor) {
		ElementsGetter<T> getter = this.createElementsGetter();
		while (getter.hasNextElement()) {
			T currentObject = getter.getNextElement();
			processor.process(currentObject);
		}
	}

	/**
	 * Removes all elements from the collection
	 */
	void clear();

	/**
	 * Method that creates ElementsGetter instance
	 * 
	 * @return Elements getter
	 */
	ElementsGetter<T> createElementsGetter();

	/**
	 * Method that adds all satisfying objects from other collection.
	 * 
	 * @param col    - collection from which we want to take and add all objects
	 *               that satisfy test
	 * @param tester - tester with which we will check whether the objects pass the
	 *               test
	 */
	default void addAllSatisfying(Collection<T> col, Tester<T> tester) {
		ElementsGetter<T> getter = col.createElementsGetter();
		while (getter.hasNextElement()) {
			T currentObject = getter.getNextElement();
			if (tester.test(currentObject)) {
				this.add(currentObject);
			}
		}
	}
}
