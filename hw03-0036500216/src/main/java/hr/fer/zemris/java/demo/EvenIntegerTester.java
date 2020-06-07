package hr.fer.zemris.java.demo;

import hr.fer.zemris.java.custom.collections.Tester;

/**
 * Concrete implementation of {@link Tester}.
 * It's used to check if passed object is integer and even.
 * @author ilovrencic
 *
 */
public class EvenIntegerTester implements Tester {

	/**
	 * Method that checks whether passed object is integer and even.
	 * Return true if it is, otherwise false.
	 */
	@Override
	public boolean test(Object object) {
		if(!(object instanceof Integer)) return false;
	    Integer i = (Integer)object;
	    return i % 2 == 0;
	}
}
