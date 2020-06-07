package hr.fer.zemris.java.hw03.prob1;

/**
 * {@link Enum} that represent {@link Lexer} states.
 * 
 * @author ilovrencic
 *
 */
public enum LexerState {
	/**
	 * Basic state is state in which {@link Lexer} parses text into all
	 * {@link TokenType} types. That includes WORD, NUMBER, SYMBOL and EOF.
	 */
	BASIC,

	/**
	 * Extended state is state in which {@link Lexer} parses text into WORD and
	 * SYMBOL state, and ignores the NUMBER state.
	 */
	EXTENDED
}
