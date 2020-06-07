package hr.fer.zemris.java.custom.scripting.nodes;


/**
 * Class that represents custom made collection
 * of object
 * 
 * @author ilovrencic
 *
 */
public class Collection {
		
	/**
	 *  Empty protected constructor
	 */
	protected Collection() {
		super();
	}
	
	/**
	 * Method that determines whether the
	 * collection is empty
	 * 
	 * @return boolean value - True if it is empty, otherwise false
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * Returns the size of the collection (in this case always zero)
	 * @return
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Adds an object to the collection (here it does nothing)
	 * @param value
	 */
	public void add(Object value) {
		//do nothing
	}
	
	/**
	 * Checks whether the object is in collection
	 * @param value object we are checking for
	 * @return boolean value - True if collection contains the object, otherwise false
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Removes the object from the collection, if there is the same instance
	 * inside the collection
	 * 
	 * @param value object we want to remove
	 * @return boolean value - True if there is object inside collection and if we successfully remove it,
	 * otherwise false
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Allocates new array with size equals to the size of this collections, 
	 * fills it with collection content and returns the array. This method never returns null.
	 * 
	 * @return object array
	 * @throws UnsupportedOperationException
	 */
	public Object[] toArray() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Method calls processor.process() for each processor given
	 * @param processor Processor class that process data
	 */
	public void forEach(Processor processor) {
		//do nothing here
	}
	
	/**
	 * Method merges other collection with itself. The other collection will 
	 * stay unchanged. Current implementation is with local class LocalProcessor.
	 * 
	 * @param other collection we want to merge
	 */
	public void addAll(Collection other) {
		
		/**
		 * LocalProcessor extends Processor and in its process method adds objects to current
		 * collection.
		 * 
		 * @author ilovrencic
		 *
		 */
		class LocalProcessor extends Processor {
			private Collection collection;
			
			public LocalProcessor(Collection collection) {
				this.collection = collection;
			}
			
			/**
			 * Used for adding objects to collection in this case.
			 */
			@Override
			public void process(Object obj) {
				collection.add(obj);
			}
		}
		
		other.forEach(new LocalProcessor(this));
	}
	
	/**
	 * Removes all elements from the collection
	 */
	public void clear() {
		//do nothing here
	}
}
