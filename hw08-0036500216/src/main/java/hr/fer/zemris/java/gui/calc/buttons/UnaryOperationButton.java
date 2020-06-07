package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Checkbox;
import java.awt.Color;
import java.util.function.DoubleUnaryOperator;
import java.util.function.UnaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.InvertListener;

/**
 * Class that represents buttons that perform unary operations like "sin" or
 * "cos" over the value.
 * 
 * @author ilovrencic
 *
 */
public class UnaryOperationButton extends JButton implements InvertListener {

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 9043382055067217897L;

	/**
	 * Represents a value of normal text
	 */
	private String normalText;

	/**
	 * Represents a value of inverse text
	 */
	private String inverseText;

	/**
	 * Represents an instance of {@link CalcModel}
	 */
	private CalcModel model;

	/**
	 * Represents an {@link UnaryOperator} that will be performed over some double
	 * value
	 */
	private DoubleUnaryOperator normalOperator;

	/**
	 * Represents an inverted {@link UnaryOperator} that will be performed over some
	 * double value
	 */
	private DoubleUnaryOperator invertedOperator;

	/**
	 * {@link Checkbox} instance so that we can check whether is inverted or normal
	 * work mode.
	 */
	private JCheckBox inverted;

	/**
	 * Default constructor
	 * 
	 * @param model            - instance of the {@link CalcModel}
	 * @param normalText       - normal text
	 * @param inverseText      - inverted text
	 * @param normalOperator   - normal unary operator
	 * @param invertedOperator - inverted unary operator
	 * @param inverted         - instance of the {@link JCheckBox}
	 */
	public UnaryOperationButton(CalcModel model, String normalText, String inverseText,
			DoubleUnaryOperator normalOperator, DoubleUnaryOperator invertedOperator, JCheckBox inverted) {
		this.model = model;
		this.normalText = normalText;
		this.inverseText = inverseText;
		this.normalOperator = normalOperator;
		this.invertedOperator = invertedOperator;
		this.inverted = inverted;

		initGUI();
		setOnClickListener();
	}

	@Override
	public void invertCheckPressed() {
		if (isInverted()) {
			setText(inverseText);
		} else {
			setText(normalText);
		}
	}

	/**
	 * Method that checks whether is input is inverted
	 * 
	 * @return - true if it is, othervise false
	 */
	private boolean isInverted() {
		return inverted != null && inverted.isSelected();
	}

	/**
	 * Method that adds listener, and when someone presses the button it will
	 * trigger the model.
	 */
	private void setOnClickListener() {
		addActionListener(l -> {
			double calculatedValue;
			if (isInverted()) {
				calculatedValue = invertedOperator.applyAsDouble(model.getValue());
			} else {
				calculatedValue = normalOperator.applyAsDouble(model.getValue());
			}

			model.setValue(calculatedValue);
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
		setText(normalText);
	}
}
