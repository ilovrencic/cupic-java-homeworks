package hr.fer.zemris.java.demo;

import hr.fer.zemris.java.custom.collections.*;

/**
 * Demo file where we demonstrate usage of {@link ElementsGetter}
 * @author ilovrencic
 *
 */
public class ElementsDemo {

	/**
	 * Main method where we do all the demonstrating
	 * @param args
	 */
	public static void main(String[] args) {
		Collection col = new ArrayIndexedCollection();

		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");

		ElementsGetter getter = col.createElementsGetter();
		getter.getNextElement();
		getter.processRemaining(System.out::println);
	}
}
