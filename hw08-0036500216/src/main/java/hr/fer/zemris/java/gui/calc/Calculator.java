package hr.fer.zemris.java.gui.calc;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.buttons.ActionButton;
import hr.fer.zemris.java.gui.calc.buttons.BinaryOperationButton;
import hr.fer.zemris.java.gui.calc.buttons.DigitButton;
import hr.fer.zemris.java.gui.calc.buttons.EqualsButton;
import hr.fer.zemris.java.gui.calc.buttons.PowerButton;
import hr.fer.zemris.java.gui.calc.buttons.UnaryOperationButton;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.InvertListener;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Class that represents {@link Calculator} frame. Here we are composing the
 * layout of different buttons and label, and in the background we have a
 * subject {@link CalcModel} that does the logic of this {@link Calculator}. We
 * have a couple of listeners to different parts of the GUI.
 * 
 * @author ilovrencic
 *
 */
public class Calculator extends JFrame {

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 5366053145015526754L;

	/**
	 * Represents a stack for saving states of the calculator
	 */
	private Stack<Double> stack;

	/**
	 * Represents an instance of the {@link CalcModel} that does all the logic for
	 * us.
	 */
	private CalcModel model;

	/**
	 * List of listeners for invert {@link Checkbox}. If user presses the checkbox,
	 * all the buttons that have an inverse has to invert.
	 */
	private List<InvertListener> listeners = new ArrayList<>();

	/**
	 * Default constructor
	 */
	public Calculator() {
		model = new CalcModelImpl();
		stack = new Stack<>();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		initGUI();
	}

	/**
	 * Method that will initialize the GUI for the {@link Calculator}.
	 */
	private void initGUI() {
		Container c = getContentPane();
		c.setLayout(new CalcLayout(3));

		CalcDisplay display = new CalcDisplay();
		model.addCalcValueListener(display);
		c.add(display, new RCPosition(1, 1));

		addButtons(c);

		setSize(getPreferredSize());
	}

	/**
	 * Method that will add all the buttons on the screen.
	 * 
	 * @param c - instance of the {@link Container}
	 */
	private void addButtons(Container c) {
		addDigits(c);
		addBinaryOperations(c);
		addUnaryOperations(c);
		addActionOperations(c);

		JButton signChange = new JButton("+/-");
		signChange.setBackground(Color.DARK_GRAY);
		signChange.setHorizontalAlignment(SwingConstants.CENTER);
		signChange.setOpaque(true);
		signChange.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		signChange.setFont(signChange.getFont().deriveFont(30f));
		signChange.addActionListener(l -> {
			model.swapSign();
		});
		c.add(signChange, new RCPosition(5, 4));

		JButton insertDot = new JButton(".");
		insertDot.setBackground(Color.DARK_GRAY);
		insertDot.setHorizontalAlignment(SwingConstants.CENTER);
		insertDot.setOpaque(true);
		insertDot.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		insertDot.setFont(signChange.getFont().deriveFont(30f));
		insertDot.addActionListener(l -> {
			model.insertDecimalPoint();
		});
		c.add(insertDot, new RCPosition(5, 5));
	}

	/**
	 * Method that will add all the action buttons like "clear", "reset", etc.
	 * 
	 * @param c - instance of the {@link Container}.
	 */
	private void addActionOperations(Container c) {
		ActionButton clear = new ActionButton("clr", l -> {
			model.clear();
		});
		c.add(clear, new RCPosition(1, 7));

		ActionButton reset = new ActionButton("reset", l -> {
			model.clearAll();
		});
		c.add(reset, new RCPosition(2, 7));

		ActionButton push = new ActionButton("push", l -> {
			stack.push(model.getValue());
		});
		c.add(push, new RCPosition(3, 7));

		ActionButton pop = new ActionButton("pop", l -> {
			if (!stack.isEmpty()) {
				model.setValue(stack.pop());
			}
		});
		c.add(pop, new RCPosition(4, 7));
	}

	/**
	 * Method that will add all the unary operation buttons like "sin","cos", etc.
	 * All the buttons from this group, we are also adding to the listeners list, so
	 * that when user presses the {@link Checkbox} all the buttons change their
	 * text.
	 * 
	 * @param c - instance of the {@link Container}.
	 */
	private void addUnaryOperations(Container c) {
		JCheckBox inv = new JCheckBox("Inv");
		inv.addActionListener(l -> {
			listeners.forEach(i -> i.invertCheckPressed());
		});
		c.add(inv, new RCPosition(5, 7));

		UnaryOperationButton inverse = new UnaryOperationButton(model, "1/x", "x", v -> (1 / v), v -> v, inv);
		listeners.add(inverse);
		c.add(inverse, new RCPosition(2, 1));

		UnaryOperationButton sin = new UnaryOperationButton(model, "sin", "asin", v -> (Math.sin(v)),
				v -> (Math.asin(v)), inv);
		listeners.add(sin);
		c.add(sin, new RCPosition(2, 2));

		UnaryOperationButton cos = new UnaryOperationButton(model, "cos", "acos", v -> (Math.cos(v)),
				v -> (Math.acos(v)), inv);
		listeners.add(cos);
		c.add(cos, new RCPosition(3, 2));

		UnaryOperationButton tan = new UnaryOperationButton(model, "tan", "atan", v -> (Math.tan(v)),
				v -> (Math.atan(v)), inv);
		listeners.add(tan);
		c.add(tan, new RCPosition(4, 2));

		UnaryOperationButton ctg = new UnaryOperationButton(model, "ctg", "actg", v -> (1 / Math.tan(v)),
				v -> (Math.PI / 2 - Math.atan(v)), inv);
		listeners.add(ctg);
		c.add(ctg, new RCPosition(5, 2));

		UnaryOperationButton log = new UnaryOperationButton(model, "log", "10^x", v -> (Math.log10(v)),
				v -> (Math.pow(10, v)), inv);
		listeners.add(log);
		c.add(log, new RCPosition(3, 1));

		UnaryOperationButton ln = new UnaryOperationButton(model, "ln", "e^x", v -> (Math.log(v)),
				v -> (Math.pow(Math.E, v)), inv);
		listeners.add(ln);
		c.add(ln, new RCPosition(4, 1));

		PowerButton power = new PowerButton(model, "x^n", "x^(1/n)", (a, b) -> Math.pow(a, b),
				(a, b) -> Math.pow(a, 1 / b), inv);
		listeners.add(power);
		c.add(power, new RCPosition(5, 1));
	}

	/**
	 * Method that adds all the binary operations to the {@link Calculator}. This
	 * includes method like +, - , * and /.
	 * 
	 * @param c - instance of the {@link Container}
	 */
	private void addBinaryOperations(Container c) {
		EqualsButton equals = new EqualsButton(model);
		c.add(equals, new RCPosition(1, 6));

		BinaryOperationButton division = new BinaryOperationButton(model, "/", (a, b) -> a / b);
		c.add(division, new RCPosition(2, 6));

		BinaryOperationButton product = new BinaryOperationButton(model, "*", (a, b) -> a * b);
		c.add(product, new RCPosition(3, 6));

		BinaryOperationButton sub = new BinaryOperationButton(model, "-", (a, b) -> a - b);
		c.add(sub, new RCPosition(4, 6));

		BinaryOperationButton add = new BinaryOperationButton(model, "+", (a, b) -> a + b);
		c.add(add, new RCPosition(5, 6));
	}

	/**
	 * Method that adds digit buttons to the {@link Calculator}. Digits include
	 * numbers from 0 to 9.
	 * 
	 * @param c - instance of the {@link Container}.
	 */
	private void addDigits(Container c) {
		int currentDigit = 9;
		for (int i = 2; i <= 4; i++) {
			for (int j = 5; j >= 3; j--) {
				DigitButton button = new DigitButton(model, currentDigit);
				c.add(button, new RCPosition(i, j));
				currentDigit--;
			}
		}
		DigitButton button = new DigitButton(model, 0);
		c.add(button, new RCPosition(5, 3));
	}

	/**
	 * Main method 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Calculator calculator = new Calculator();
			calculator.setVisible(true);
		});
	}
}
