package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enum that represents all the types a Token can take.
 * 
 * @author ilovrencic
 *
 */
public enum SmartScriptTokenType {
	/**
	 * Represents a text thats outside of the tag.
	 */
	TEXT,

	/**
	 * Represents a number inside the tag.
	 */
	NUMBER,

	/**
	 * Represents a keyword like FOR,END or =.
	 */
	KEYWORD,

	/**
	 * Represents a ID inside the tag. Like the name variable.
	 */
	ID,

	/**
	 * Represents a open parentheses "{".
	 */
	OPEN,

	/**
	 * Represents a closed parentheses "}".
	 */
	CLOSE,

	/**
	 * Represents a dollar sign "$".
	 */
	DOLLAR_SYMBOL,

	/**
	 * Represents a function name after @ symbol.
	 */
	FUNCTION,

	/**
	 * Represents any other symbol that can appear. (inside a tag).
	 */
	SYMBOL,

	/**
	 * Represents tag text.
	 */
	TAG_TEXT,

	/**
	 * Represents an end-of-file.
	 */
	EOF;
}
