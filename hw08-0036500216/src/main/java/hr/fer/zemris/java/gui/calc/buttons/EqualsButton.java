package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Class that represents equals button on {@link Calculator}.
 * 
 * @author ilovrencic
 *
 */
public class EqualsButton extends JButton {

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 2766352884157960435L;

	/**
	 * Represents an equals sign
	 */
	private static final String EQUALS_SIGN = "=";

	/**
	 * Represents an instance of {@link CalcModel}
	 */
	private CalcModel model;

	/**
	 * Default constructor
	 * 
	 * @param model - instance of {@link CalcModel}
	 */
	public EqualsButton(CalcModel model) {
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
			if (model.isActiveOperandSet()) {
				double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(),
						model.getValue());
				model.setValue(result);
				model.clearActiveOperand();
				model.setPendingBinaryOperation(null);
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
		setText(EQUALS_SIGN);
	}

}
