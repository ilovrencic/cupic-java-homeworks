package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Class that represent {@link SmartScriptToken}
 * 
 * @author ilovrencic
 *
 */
public class SmartScriptToken {

	private SmartScriptTokenType type;
	private Object value;

	/**
	 * Default constructor for token. We have to pass its {@link TokenType} and its
	 * {@link Object} value.
	 * 
	 * @param type
	 * @param value
	 */
	public SmartScriptToken(SmartScriptTokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Getter method for {@link TokenType}
	 * 
	 * @return
	 */
	public SmartScriptTokenType getType() {
		return type;
	}

	/**
	 * Getter method for {@link Object} value
	 * 
	 * @return
	 */
	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		String output = "";
		output += "( " + type.toString() + ", " + value + " )";
		return output;
	}

}
