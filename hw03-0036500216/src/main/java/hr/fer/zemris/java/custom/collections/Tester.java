package hr.fer.zemris.java.custom.collections;

/**
 * Interface that models testing behavior
 * @author ilovrencic
 *
 */
public interface Tester {
	
	/**
	 * Method that tests {@link Object} and returns true if the test passed 
	 * @param object
	 * @return
	 */
	boolean test(Object object);

}
