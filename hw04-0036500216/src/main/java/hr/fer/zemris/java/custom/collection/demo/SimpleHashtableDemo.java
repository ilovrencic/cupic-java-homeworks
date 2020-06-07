package hr.fer.zemris.java.custom.collection.demo;

import java.util.Iterator;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;
import hr.fer.zemris.java.custom.collections.SimpleHashtable.TableEntry;

/**
 * Demo file to show the usage of the class {@link SimpleHashtable}
 * 
 * @author ilovrencic
 *
 */
public class SimpleHashtableDemo {

	/**
	 * Main function in the class
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(4);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);

		Integer kristinaGrade = examMarks.get("Kristina");
		System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes: 5

		System.out.println("Number of stored pairs: " + examMarks.size()); // writes: 4

		Iterator<TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			TableEntry<String, Integer> pair = iter.next();
			System.out.println(pair);
			iter.remove();
		}

		System.out.println(examMarks.size());
	}
}
