package hr.fer.zemris.java.custom.scripting.elems;

/**
 * {@link Element} representation of constant {@link Integer}
 * 
 * @author ilovrencic
 *
 */
public class ElementConstantInteger extends Element {

	private int value;

	/**
	 * Default constructor
	 * 
	 * @param value - {@link Integer} value
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	/**
	 * Overridden method that in this case returns {@link String} value of value.
	 */
	@Override
	public String asText() {
		return String.valueOf(value);
	}

	public int getValue() {
		return value;
	}
}
