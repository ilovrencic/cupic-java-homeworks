package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Class that represents a single digit button on the {@link Calculator}.
 * 
 * @author ilovrencic
 *
 */
public class DigitButton extends JButton {

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -3356455522236611177L;

	/**
	 * Digit that will represent this button
	 */
	private int digit;

	/**
	 * Represents a instance of {@link CalcModel}
	 */
	private CalcModel model;

	/**
	 * Default constructor
	 * 
	 * @param digit - number between 0 and 9
	 */
	public DigitButton(CalcModel model, int digit) {
		this.digit = digit;
		this.model = model;

		initGUI();
		setOnClickListener();
	}

	/**
	 * Method that adds listener, and when someone presses the button it will
	 * trigger the model.
	 */
	private void setOnClickListener() {
		addActionListener(l -> {
			try {
				model.insertDigit(digit);
			} catch (CalculatorInputException e) {
				e.getStackTrace();
			}
		});
	}

	/**
	 * Method that initializes GUI for this button.
	 */
	private void initGUI() {
		setBackground(Color.DARK_GRAY);
		setHorizontalAlignment(SwingConstants.CENTER);
		setOpaque(true);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		setFont(getFont().deriveFont(30f));
		setText(String.valueOf(digit));
	}
}
