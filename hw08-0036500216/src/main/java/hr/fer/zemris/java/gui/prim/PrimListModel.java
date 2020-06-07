package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Class that models {@link ListModel} with Prime {@link Integer} numbers.
 * 
 * @author ilovrencic
 *
 */
public class PrimListModel implements ListModel<Integer> {

	/**
	 * Represents a list of all listeners
	 */
	private List<ListDataListener> listeners;

	/**
	 * Represents a list of all prime numbers
	 */
	private List<Integer> primeNumbers;

	/**
	 * Represents a counter of prime numbers
	 */
	private int primeCounter;

	/**
	 * Default constructor
	 */
	public PrimListModel() {
		primeNumbers = new ArrayList<Integer>();
		listeners = new ArrayList<ListDataListener>();

		primeNumbers.add(1);
		primeCounter = 1;
	}

	@Override
	public int getSize() {
		return primeNumbers.size();
	}

	@Override
	public Integer getElementAt(int index) {
		if (index < 0 || index >= primeNumbers.size()) {
			throw new IndexOutOfBoundsException("Index out of bounds of the prime numbers!");
		}

		return primeNumbers.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		Objects.requireNonNull(l);
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		Objects.requireNonNull(l);
		listeners.remove(l);
	}

	/**
	 * Method that calculates and returns the next prime number. It also informs all
	 * the listeners that the list has changed.
	 */
	public void next() {
		int firstNextNumber = primeNumbers.get(primeCounter - 1) + 1;
		while (true) {
			if (isPrime(firstNextNumber)) {
				primeNumbers.add(firstNextNumber);
				primeCounter++;
				break;
			}
			firstNextNumber++;
		}
		informListeners();
	}

	/**
	 * Method that informs listeners that the list has changed.
	 */
	private void informListeners() {
		listeners.forEach(
				l -> l.contentsChanged(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, getSize(), getSize())));
	}

	/**
	 * Method that checks whether the number is prime or not.
	 * 
	 * @param number - integer
	 * @return - true if number is prime, othervise false
	 */
	private boolean isPrime(int number) {
		for (int i = 2; i < number; i++) {
			if (number % i == 0) {
				return false;
			}
		}

		return true;
	}

}
