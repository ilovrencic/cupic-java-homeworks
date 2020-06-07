package hr.fer.zemris.java.hw03.prob1;

/**
 * Class that represents Lexer
 * 
 * @author ilovrencic
 *
 */
public class Lexer {

	/**
	 * Char array that will be filled with characters from the text we are parsing.
	 */
	private char[] data;

	/**
	 * Current token we have in our {@link Lexer} instance
	 */
	private Token token;

	/**
	 * Current index we are at in out {@link CharSequence}
	 */
	private int currentIndex;

	/**
	 * Parameter that determines in which state is {@link Lexer}. Lexer can be in
	 * {@link LexerState} BASIC or EXTENDED
	 */
	private LexerState state;

	/**
	 * Default constructor for {@link Lexer} class
	 * 
	 * @param text
	 */
	public Lexer(String text) {
		initialize(text);
	}

	/**
	 * Method that returns next token. Depending on the text, it can return
	 * {@link TokenType} WORD, EOF, SYMBOL or NUMBER. Based on the current
	 * {@link LexerState} it can return all of the {@link TokenType} or only WORD
	 * and SYMBOL.
	 * 
	 * @return next token - or {@link LexerException} if there is no next token.
	 */
	public Token nextToken() {
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
	 * Returns current token in {@link Lexer}.
	 * 
	 * @return
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Method that sets the {@link LexerState} for {@link Lexer}. If you pass null,
	 * it will throw {@link NullPointerException}.
	 * 
	 * @param state
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new NullPointerException("State can't be null!");
		}
		this.state = state;
	}

	/**
	 * Method that initializes {@link Lexer}. Initial state for {@link Lexer} is
	 * BASIC state.
	 * 
	 * @param text
	 */
	private void initialize(String text) {
		if (text == null) {
			throw new NullPointerException();
		}

		state = LexerState.BASIC;
		data = text.toCharArray();
		currentIndex = 0;
	}

	/**
	 * Method that removes any empty or unnecessary characters from the begging of
	 * the text.
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
	 * Method that will generate next token. Based on the first character it will
	 * choose to call some other helper function that will parse the text.
	 * 
	 * @return {@link Token}
	 */
	private Token generateNextToken() {
		Character currentChar = data[currentIndex];

		if (state == LexerState.BASIC) {
			if (Character.isLetter(currentChar) || currentChar.toString().equals("\\")) {
				token = generateWordToken();
				return token;
			} else if (Character.isDigit(currentChar)) {
				token = generateNumberToken();
				return token;
			} else {
				token = generateSymbolToken();
				return token;
			}
		} else {
			if (currentChar.toString().equals("#")) {
				token = generateSymbolToken();
				return token;
			} else {
				token = generateExtendedToken();
				return token;
			}
		}
	}

	/**
	 * Method that generates next WORD {@link TokenType}. Word is comprised only of
	 * letters and numbers if they are prefaced with escape symbol ("\").
	 * 
	 * @return {@link Token}, that's WORD {@link TokenType}
	 */
	private Token generateWordToken() {
		Character letter;

		String wordToken = "";
		while (currentIndex < data.length) {
			letter = data[currentIndex];

			if (Character.isLetter(letter)) {
				wordToken += letter;
				currentIndex++;
			} else if (letter.toString().equals("\\")) {
				if (currentIndex + 1 >= data.length || Character.isLetter(data[currentIndex + 1])) {
					throw new LexerException("Invalid escape sequance!");
				} else {
					currentIndex++;
					letter = data[currentIndex];
					wordToken += letter;
					currentIndex++;
				}
			} else {
				break;
			}
		}

		return new Token(TokenType.WORD, wordToken);
	}

	/**
	 * Method that generates next NUMBER {@link TokenType}. Number is comprised only
	 * of numbers. Numbers have to be parsable to {@link Long}.
	 * 
	 * @return {@link Token}, that's NUMBER {@link TokenType}
	 */
	private Token generateNumberToken() {
		Character digit;

		String numberToken = "";
		while (currentIndex < data.length) {
			digit = data[currentIndex];
			if (Character.isDigit(digit)) {
				numberToken += digit.toString();
				currentIndex++;
			} else {
				break;
			}
		}

		try {
			long number = Long.parseLong(numberToken);
			return new Token(TokenType.NUMBER, number);
		} catch (Exception e) {
			throw new LexerException("Invalid number sequence!");
		}
	}

	/**
	 * Method that generates next SYMBOL {@link TokenType}. Symbol is comprised of
	 * all the others characters that aren't a letter or digit. If the symbol is #
	 * then, we have to switch {@link Lexer} state.
	 * 
	 * @return {@link Token}, that's SYMBOL {@link TokenType}
	 */
	private Token generateSymbolToken() {
		Character symbol = data[currentIndex];
		currentIndex++;

		if (symbol.toString().equals("#")) {
			if (state == LexerState.BASIC) {
				setState(LexerState.EXTENDED);
			} else {
				setState(LexerState.BASIC);
			}
		}

		return new Token(TokenType.SYMBOL, symbol);
	}

	/**
	 * Method that generates token when {@link Lexer} works in EXTENDED
	 * {@link LexerState}. It parses all characters together, except for the
	 * symbols.
	 * 
	 * @return {@link Token}, that's WORD {@link TokenType}.
	 */
	private Token generateExtendedToken() {
		Character character;

		String wordToken = "";
		String skipOperators = " \r\n\t";
		while (currentIndex < data.length) {
			character = data[currentIndex];

			if (character.toString().equals("#") || skipOperators.contains(character.toString())) {
				break;
			} else {
				wordToken += character.toString();
				currentIndex++;
			}
		}

		return new Token(TokenType.WORD, wordToken);
	}
}
