package hr.fer.zemris.java.custom.collection.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;

/**
 * Demo class to show the usage of the parameterized collections.
 * 
 * @author ilovrencic
 *
 */
public class CollectionsDemo {

	/**
	 * Main function where the demo will be shown
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayIndexedCollection<Integer> collection = new ArrayIndexedCollection<>();

		collection.add(23);
		collection.add(123);
		collection.add(33);

		ElementsGetter<Integer> getter = collection.createElementsGetter();
		while (getter.hasNextElement()) {
			System.out.println(getter.getNextElement());
		}

		System.out.println(collection.contains(23));

		LinkedListIndexedCollection<String> strings = new LinkedListIndexedCollection<String>();
		strings.add("boeing");

		Avion avion = new Avion("boeing");
		System.out.println(strings.contains(avion));

	}

	/**
	 * Simple class to demonstrate how the parameterized collections work.
	 * 
	 * @author ilovrencic
	 *
	 */
	private static class Avion {
		private String name;

		public Avion(String name) {
			this.name = name;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Avion) {
				return name.equals(((Avion) obj).name);
			}
			return false;
		}
	}

}
