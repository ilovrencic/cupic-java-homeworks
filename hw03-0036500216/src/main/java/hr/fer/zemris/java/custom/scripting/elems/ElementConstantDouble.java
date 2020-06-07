package hr.fer.zemris.java.custom.scripting.elems;

/**
 * {@link Element} that represents a double value.
 * 
 * @author ilovrencic
 *
 */
public class ElementConstantDouble extends Element {

	private double value;

	/**
	 * Default constructor
	 * 
	 * @param value
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	/**
	 * Overridden method that return {@link Element} in {@link String} format.
	 */
	@Override
	public String asText() {
		return String.valueOf(value);
	}

	public double getValue() {
		return value;
	}

}
