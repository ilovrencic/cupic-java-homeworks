package hr.fer.zemris.java.custom.scripting.elems;

/**
 * {@link Element} that represents operator
 * 
 * @author ilovrencic
 *
 */
public class ElementOperator extends Element {

	private String symbol;

	/**
	 * Default constructor
	 * 
	 * @param symbol
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Overridden method that represents {@link Element} as {@link String}.
	 */
	@Override
	public String asText() {
		return symbol;
	}

	public String getSymbol() {
		return symbol;
	}

}
