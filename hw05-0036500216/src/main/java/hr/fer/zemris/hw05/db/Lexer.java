package hr.fer.zemris.hw05.db;

/**
 * Class that represent a simple query lexer. This class tokenizes text and
 * creates meaningful lexical tokens.
 * 
 * @author ilovrencic
 *
 */
public class Lexer {

	/* ============= CONSTANTS ============= */
	private static final String AND = "and";
	private static final String QUERY = "query";
	private static final String LIKE = "LIKE";
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	private static final String JMBAG = "jmbag";
	/* ===================================== */

	/**
	 * Represents an array of letters that represent a query
	 */
	private char[] data;

	/**
	 * Represents a current token
	 */
	private Token token;

	/**
	 * Represents a current index
	 */
	private int currentIndex;

	/**
	 * Default constructor
	 * 
	 * @param query - we want to tokenize
	 */
	public Lexer(String query) {
		initialize(query);
	}

	/**
	 * Returns next token in lexical analysis. If there is no one, throws
	 * {@link LexerException}.
	 * 
	 * @return
	 */
	public Token getNextToken() {
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("End-of-line!");
		}

		removeEmptyCharacters();

		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}

		token = generateNextToken();
		return token;
	}

	/**
	 * Returns current token
	 * 
	 * @return {@link Token}
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Method that initializes {@link Lexer}
	 * 
	 * @param query
	 */
	private void initialize(String query) {
		if (query == null) {
			throw new NullPointerException("Query can't be null!");
		}

		data = query.toCharArray();
		currentIndex = 0;
	}

	/**
	 * Method that removes empty characters
	 */
	private void removeEmptyCharacters() {
		String skipOperators = " \r\n\t";

		while (currentIndex < data.length) {
			Character character = data[currentIndex];
			if (skipOperators.contains(character.toString())) {
				currentIndex++;
			} else {
				break;
			}
		}
	}

	/**
	 * Method that returnes next token
	 * 
	 * @return
	 */
	private Token generateNextToken() {
		Character currentChar = data[currentIndex];

		String operators = "=<>!";

		if (operators.contains(currentChar.toString())) {
			token = generateSymbolToken();
		} else {
			token = generateTextToken();
		}

		return token;
	}

	/**
	 * Method that generates next symbol {@link Token}
	 * 
	 * @return
	 */
	private Token generateSymbolToken() {
		Character symbol = data[currentIndex];
		currentIndex++;
		
		String symbols = symbol.toString();
		if(data[currentIndex] == '=') {
			symbols += String.valueOf(data[currentIndex]);
			currentIndex++;
		}

		return new Token(TokenType.SYMBOL, symbols);
	}

	/**
	 * Method that generates next textual token. This can be FIELD, QUERY or AND
	 * token.
	 * 
	 * @return
	 */
	private Token generateTextToken() {
		Character letter = data[currentIndex];

		if (letter.charValue() == '"') {
			currentIndex++;
			return generateValueToken();
		}

		String operators = "<>=!  \r\n\t";
		String text = "";
		while (currentIndex < data.length) {
			letter = data[currentIndex];

			if (operators.contains(letter.toString())) {
				break;
			}

			text += letter.toString();
			currentIndex++;
		}

		if (text.equalsIgnoreCase(AND)) {
			return new Token(TokenType.AND, text);
		} else if (text.equalsIgnoreCase(QUERY)) {
			return new Token(TokenType.QUERY, text);
		} else if (text.equals(LIKE)) {
			return new Token(TokenType.SYMBOL, text);
		} else if (text.equals(FIRST_NAME) || text.equals(LAST_NAME) || text.equals(JMBAG)) {
			return new Token(TokenType.FIELD, text);
		} else {
			throw new LexerException("Wrong argument in Lexer! This isn't a query: "+text);
		}
	}

	/**
	 * Returns next value token. This is text we are searching in query.
	 * 
	 * @return
	 */
	private Token generateValueToken() {
		Character letter;

		String value = "";
		while (currentIndex < data.length) {
			letter = data[currentIndex];

			if (letter.charValue() == '"') {
				currentIndex++;
				break;
			}

			value += letter.toString();
			currentIndex++;
		}

		return new Token(TokenType.VALUE, value);
	}

}
