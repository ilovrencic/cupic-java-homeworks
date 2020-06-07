package hr.fer.zemris.java.gui.calc.model;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * Listener that notifies buttons to change their text value
 * 
 * @author ilovrencic
 *
 */
public interface InvertListener {

	/**
	 * Method that will be called when user taps on inverted checkbox on the
	 * {@link Calculator}.
	 */
	void invertCheckPressed();
}
