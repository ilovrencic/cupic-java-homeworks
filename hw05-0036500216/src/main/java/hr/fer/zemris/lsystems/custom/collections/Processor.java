package hr.fer.zemris.lsystems.custom.collections;


/**
 * 
 * Processor interface
 * 
 * @author ilovrencic
 *
 */
public interface Processor<T> {
	
	/**
	 * Method that process Object value
	 * @param currentObject value meant to be processed
	 */
	void process(T currentObject);
	
}
