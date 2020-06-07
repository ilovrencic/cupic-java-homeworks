package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;
import java.util.function.DoubleBinaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Class that models buttons that contain binary operations like "=","*","/",
 * etc.
 * 
 * @author ilovrencic
 *
 */
public class BinaryOperationButton extends JButton {

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -28374324470349832L;

	/**
	 * Represents a binary operator that will be executed over the entered numbers
	 */
	private DoubleBinaryOperator operator;

	/**
	 * Represents {@link CalcModel} that governs the logic behind {@link Calculator}
	 */
	private CalcModel model;

	/**
	 * Represents a text that will write on the button
	 */
	private String text;

	/**
	 * Default constructor
	 * 
	 * @param model    - instance of the {@link CalcModel}
	 * @param operator - instance of the {@link DoubleBinaryOperator}
	 * @param text     - text that will be written on the button
	 */
	public BinaryOperationButton(CalcModel model, String text, DoubleBinaryOperator operator) {
		this.model = model;
		this.operator = operator;
		this.text = text;

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
				double left = model.getActiveOperand();
				double right = model.getValue();
				double result = model.getPendingBinaryOperation().applyAsDouble(left, right);
				model.setActiveOperand(result);
				model.setPendingBinaryOperation(operator);
			} else {
				double value = model.getValue();
				model.setActiveOperand(value);
				model.setPendingBinaryOperation(operator);
			}
			model.clear();
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
		setText(text);
	}
}
