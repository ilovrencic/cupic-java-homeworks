package hr.fer.zemris.hw05.db;

/**
 * Class that represents Token. Token is a smallest unit in lexical analysis. It
 * is represented by its {@link TokenType} and {@link Object} value.
 * 
 * @author ilovrencic
 *
 */
public class Token {

	private TokenType type;
	private Object value;

	/**
	 * Default constructor for token. We have to pass its {@link TokenType} and its
	 * {@link Object} value.
	 * 
	 * @param type
	 * @param value
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Getter method for {@link TokenType}
	 * @return
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * Getter method for {@link Object} value
	 * @return
	 */
	public Object getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		String output = "";
		output += "( "+type.toString()+", "+value+" )";
		return output;
	}
}
