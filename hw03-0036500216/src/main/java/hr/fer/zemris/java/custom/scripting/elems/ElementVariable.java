package hr.fer.zemris.java.custom.scripting.elems;

/**
 * ElementVariable class that represent variable name
 * 
 * @author ilovrencic
 *
 */
public class ElementVariable extends Element {

	private String name;

	/**
	 * Default constructor
	 * 
	 * @param name - {@link ElementVariable} name
	 */
	public ElementVariable(String name) {
		this.name = name;
	}

	/**
	 * Overridden method that in this case return {@link String} name.
	 */
	@Override
	public String asText() {
		return name;
	}

	public String getName() {
		return name;
	}

}
