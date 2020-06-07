package hr.fer.zemris.java.custom.scripting.elems;

/**
 * {@link Element} that represents {@link String} value.
 * @author ilovrencic
 *
 */
public class ElementString extends Element {

	private String value;

	/**
	 * Default constructor
	 * @param value
	 */
	public ElementString(String value) {
		this.value = value;
	}

	/**
	 * Overridden method that returns {@link Element} in {@link String} format.
	 */
	@Override
	public String asText() {
		return value;
	}

	public String getValue() {
		return value;
	}
}
