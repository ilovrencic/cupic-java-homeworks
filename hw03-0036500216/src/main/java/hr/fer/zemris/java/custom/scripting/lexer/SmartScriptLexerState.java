package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.hw03.prob1.Lexer;

/**
 * Enum that represents all the states {@link Lexer} can find itself.
 * 
 * @author ilovrencic
 *
 */
public enum SmartScriptLexerState {
	/**
	 * Represents a state in which the current part is text.
	 */
	TEXT,

	/**
	 * Represents a state in which we are currently inside a tag.
	 */
	TAG,

	/**
	 * Represents a state in which we are currently inside a for tag.
	 */
	FOR_TAG,

	/**
	 * Represents a state in which we are currently inside echo tag.
	 */
	ECHO_TAG;
}
