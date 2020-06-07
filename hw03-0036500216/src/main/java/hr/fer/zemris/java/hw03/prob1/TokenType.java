package hr.fer.zemris.java.hw03.prob1;

/**
 * TokenType is enum that represents all possible types token can represent in
 * lexical analysis
 * 
 * @author ilovrencic
 *
 */
public enum TokenType {
	/**
	 * TokenType that represents that the token carries a {@link String} value. Any
	 * series of value for which method Character.isLetter() return true.
	 */
	WORD,

	/**
	 * TokenType that represents that the token carries a {@link Long} value. Token
	 * of this type must be able to be casted to {@link Long}.
	 */
	NUMBER,

	/**
	 * TokenType that represents that token carries a symbol. Any other remaining
	 * symbol that's left after we take out all words and numbers.
	 */
	SYMBOL,

	/**
	 * TokenType that represents End-of-File. 
	 */
	EOF
}
