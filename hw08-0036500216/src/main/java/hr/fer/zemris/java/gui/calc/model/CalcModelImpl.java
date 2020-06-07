package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * Class that implements {@link CalcModel} and serves as a subject in observer
 * design patter. Here we model {@link Calculator} behavior.
 * 
 * @author ilovrencic
 *
 */
public class CalcModelImpl implements CalcModel {

	/**
	 * Represents whether is {@link Calculator} currently editable
	 */
	private boolean isEditable;

	/**
	 * Represents whether the current input is negative
	 */
	private boolean isNegative;

	/**
	 * Represents whether the is currently active operand
	 */
	private boolean isActiveOperand;

	/**
	 * Represents a current input in calculator
	 */
	private String input;

	/**
	 * Represents frozen input on screen
	 */
	private String frozenInput;

	/**
	 * Represents numeric input in calculator
	 */
	private double numericInput;

	/**
	 * Represents what is the active operand
	 */
	private double activeOperand;

	/**
	 * Represents the pending operand
	 */
	private DoubleBinaryOperator pendingOperand;

	/**
	 * Represents a list of listeners that are subscribed on this subject
	 */
	private List<CalcValueListener> listeners;

	private final static double EPSILON = 1E-6;

	/**
	 * Default constructor
	 */
	public CalcModelImpl() {
		isEditable = true;
		isNegative = false;
		isActiveOperand = false;
		frozenInput = null;
		input = "";
		numericInput = 0;
		listeners = new ArrayList<CalcValueListener>();
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if (l == null) {
			throw new NullPointerException("Listener can't be null!");
		}

		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		if (l == null) {
			throw new NullPointerException("Listener can't be null!");
		}

		listeners.remove(l);
	}

	@Override
	public double getValue() {
		double value = numericInput;
		int integerValue = (int) value;

		if (Math.abs(value - integerValue) < EPSILON) {
			return integerValue;
		} else {
			return numericInput;
		}
	}

	@Override
	public void setValue(double value) {
		numericInput = value;

		if (value == Double.POSITIVE_INFINITY) {
			input = "Infinity";
		} else if (value == Double.NEGATIVE_INFINITY) {
			input = "-Infinity";
		} else if (value == Double.NaN) {
			input = "NaN";
		} else {
			input = String.valueOf(value);
		}
		
		freezeValue(input);
		isEditable = false;
		informListeners();
	}

	@Override
	public boolean isEditable() {
		return isEditable;
	}

	@Override
	public void clear() {
		input = "";
		numericInput = 0;
		freezeValue(null);
		isEditable = true;
		informListeners();
	}

	@Override
	public void clearAll() {
		clearActiveOperand();
		pendingOperand = null;
		clear();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (numericInput < 0) {
			numericInput *= -1;
			isNegative = false;
		} else {
			numericInput *= -1;
			isNegative = true;
		}

		input = String.valueOf(numericInput);
		freezeValue(input);
		informListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!isEditable) {
			throw new CalculatorInputException("The input is not editable!");
		}

		if (input.length() == 0) {
			throw new CalculatorInputException("There is no number to add decimal point!");
		}

		if (input.contains(".")) {
			throw new CalculatorInputException("There is already a decimal point!");
		}

		input += ".";
		freezeValue(input);
		informListeners();
	}

	@Override
	public String toString() {
		if (!hasFrozenValue())
			return isNegative ? "-0" : "0";

		return frozenInput;
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!isEditable) {
			throw new CalculatorInputException("The input is not editable!");
		}

		if (digit < 0 || digit > 9) {
			throw new IllegalArgumentException("Argument is not in allwoed interval!");
		}

		if (input.equals("0") && digit == 0)
			return;

		if (input.equals("0") && digit != 0) {
			input = "";
		}

		String current = "";
		current += input + digit;

		try {
			double parsedValue = Double.parseDouble(current);
			if (parsedValue == Double.POSITIVE_INFINITY)
				throw new NumberFormatException();

			input = current;
			numericInput = parsedValue;
			freezeValue(input);
		} catch (NumberFormatException e) {
			throw new CalculatorInputException("The number you have entered is too big!");
		}

		informListeners();
	}

	@Override
	public boolean isActiveOperandSet() {
		return isActiveOperand;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!isActiveOperand) {
			throw new IllegalStateException("The active operand is " + "not set!");
		}

		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		isActiveOperand = true;
	}

	@Override
	public void clearActiveOperand() {
		isActiveOperand = false;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperand;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperand = op;
	}

	@Override
	public void freezeValue(String value) {
		frozenInput = value;
	}

	@Override
	public boolean hasFrozenValue() {
		return frozenInput != null;
	}

	/**
	 * Method that invokes all listeners that something has happend on the subject.
	 * In this case the subject is {@link CalcModelImpl}.
	 */
	private void informListeners() {
		listeners.forEach(l -> l.valueChanged(this));
	}

}
