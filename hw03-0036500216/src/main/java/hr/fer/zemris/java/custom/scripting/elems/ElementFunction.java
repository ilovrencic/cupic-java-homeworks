package hr.fer.zemris.java.custom.scripting.elems;

/**
 * {@link Element} that represents Function.
 * 
 * @author ilovrencic
 *
 */
public class ElementFunction extends Element {

	private String name;

	/**
	 * Default constructor
	 * 
	 * @param name
	 */
	public ElementFunction(String name) {
		this.name = name;
	}

	/**
	 * Overridden method that represents {@link Element} as {@link String}.
	 */
	@Override
	public String asText() {
		return name;
	}

	public String getName() {
		return name;
	}
}
