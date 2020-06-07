package hr.fer.zemris.hw05.db;

/**
 * Enum that represent all the units we can find in lexical analysis of the
 * query.
 * 
 * @author ilovrencic
 *
 */
public enum TokenType {

	/**
	 * Represents a keyword query that should be always present before query.
	 */
	QUERY,

	/**
	 * Represent a field in query. E.g. "lastName" or "jmbag"
	 */
	FIELD,
	
	/**
	 * Represent a value of the field
	 */
	VALUE,
	
	/**
	 * Represents a keyword AND
	 */
	AND,
	
	/**
	 * Represents a symbols like <>=
	 */
	SYMBOL,

	/**
	 * Represent an end of line
	 */
	EOF
}
