package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.hw03.prob1.LexerException;

/**
 * Class SmartScriptLexer is responsible for generation of tokens, that are
 * going to be later used by Parser.
 * 
 * @author ilovrencic
 *
 */
public class SmartScriptLexer {

	private SmartScriptToken token;
	private SmartScriptLexerState state;
	private char[] data;
	private int currentIndex;

	/**
	 * Default constructor for {@link SmartScriptLexer}.
	 */
	public SmartScriptLexer(String text) {
		initialize(text);
	}

	/**
	 * 
	 * Method that returns next token. If there is no next token, then it throws
	 * {@link LexerException}.
	 * 
	 */
	public SmartScriptToken nextToken() {
		if (token != null && token.getType() == SmartScriptTokenType.EOF) {
			throw new LexerException("The end of file is reached! There can't be any more new tokens!");
		}

		removeEmptyCharacters();

		if (currentIndex >= data.length) {
			token = new SmartScriptToken(SmartScriptTokenType.EOF, null);
			return token;
		}

		token = generateNextToken();
		return token;
	}

	/**
	 * Method returns current token in {@link SmartScriptLexer}.
	 */
	public SmartScriptToken getToken() {
		return token;
	}

	/**
	 * Methods sets current state as @param state.
	 */
	public void setState(SmartScriptLexerState state) {
		if (state == null)
			throw new NullPointerException("State can't be null!");
		this.state = state;
	}

	/**
	 * 
	 * Method that initializes the {@link SmartScriptLexer}. If the passed
	 * {@link String} is null, method throws {@link NullPointerException}.
	 * 
	 */
	private void initialize(String text) {
		if (text == null) {
			throw new NullPointerException("Text can't be null!");
		}

		data = text.toCharArray();
		currentIndex = 0;
		state = SmartScriptLexerState.TEXT;
	}

	/**
	 * 
	 * Method that removes empty characters before the actual words/numbers.
	 * 
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
	 * Method that checks whether the next current is "}". If it is, it means that
	 * we are entering the {@link SmartScriptLexerState} TAG. In other words, it
	 * means we are now parsing elements inside the tag.
	 */
	private boolean checkIfEnteringTag() {
		Character symbol = data[currentIndex];

		if (symbol.toString().equals("{")) {
			token = new SmartScriptToken(SmartScriptTokenType.OPEN, "{");
			state = SmartScriptLexerState.TAG;
			currentIndex++;
			return true;
		}

		return false;
	}

	/**
	 * Method that checks if the current symbol is "$". Return true if it is,
	 * otherwise false.
	 */
	private boolean checkForDollarSign() {
		Character symbol = data[currentIndex];

		if (symbol.toString().equals("$")) {
			token = new SmartScriptToken(SmartScriptTokenType.DOLLAR_SYMBOL, "$");
			currentIndex++;
			return true;
		}

		return false;
	}

	/**
	 * 
	 * Method that checks whether the current array of symbols holds any keyword. If
	 * they do, it usually means we have to change state. Keywords indicate we are
	 * inside tag.
	 * 
	 */
	private boolean checkForTheKeyword() {
		if (token != null && token.getType() == SmartScriptTokenType.DOLLAR_SYMBOL) {

			Character symbol = data[currentIndex];

			if (symbol.toString().equals("=")) {
				token = new SmartScriptToken(SmartScriptTokenType.KEYWORD, "=");
				state = SmartScriptLexerState.ECHO_TAG;
				currentIndex++;
				return true;
			}

			int tempIndex = currentIndex;
			String keyword = "";

			while (tempIndex < data.length) {
				symbol = data[tempIndex];

				if (symbol.toString().equals(" ") || symbol.toString().equals("$")) {
					break;
				}

				keyword += symbol.toString();
				tempIndex++;
			}

			if (keyword.equals("FOR")) {
				token = new SmartScriptToken(SmartScriptTokenType.KEYWORD, "FOR");
				state = SmartScriptLexerState.FOR_TAG;
				currentIndex += 3;
				return true;
			}

			if (keyword.equals("END")) {
				token = new SmartScriptToken(SmartScriptTokenType.KEYWORD, "END");
				currentIndex += 3;
				return true;
			}
		}

		return false;
	}

	/**
	 * 
	 * Method that checks whether the current symbol holds "}". If it does, that
	 * means we are exiting tag, and entering text space.
	 * 
	 */
	private boolean checkIfExitingTag() {
		Character symbol = data[currentIndex];

		if (symbol.toString().equals("}")) {
			token = new SmartScriptToken(SmartScriptTokenType.CLOSE, "}");
			state = SmartScriptLexerState.TEXT;
			currentIndex++;
			return true;
		}

		return false;
	}

	/**
	 * 
	 * Method that generates next token. Based on current symbol the method chooses
	 * which method will it call to generate next {@link SmartScriptToken}.
	 * 
	 */
	private SmartScriptToken generateNextToken() {
		if (checkIfEnteringTag()) {
			return token;
		}

		if (state == SmartScriptLexerState.TEXT) {
			token = generateTextToken();
			return token;
		} else {

			if (checkForDollarSign()) {
				return token;
			}

			if (checkForTheKeyword()) {
				return token;
			}

			if (checkIfExitingTag()) {
				return token;
			}

			if (state == SmartScriptLexerState.FOR_TAG) {
				token = generateForTagToken();
				return token;
			} else {
				token = generateEchoTagToken();
				return token;
			}
		}
	}

	/**
	 * 
	 * Method that generates ID token.
	 *
	 */
	private SmartScriptToken generateIdToken() {
		Character character;

		String tokenId = "";
		String skipOperators = " \r\n\t $";
		while (currentIndex < data.length) {
			character = data[currentIndex];
			if (skipOperators.contains(character.toString()))
				break;

			tokenId += character.toString();
			currentIndex++;
		}

		checkId(tokenId);
		return new SmartScriptToken(SmartScriptTokenType.ID, tokenId);
	}

	/**
	 * 
	 * Method that generates Number token. It also checks whether @param isNegative.
	 * If the current number is negative then it has to parse it as a negative
	 * number.
	 * 
	 */
	private SmartScriptToken generateNumberToken(boolean isNegative) {
		Character character;

		String number = "";
		boolean isInteger = true;
		while (currentIndex < data.length) {
			character = data[currentIndex];

			if (character.toString().equals(".")) {
				if (currentIndex + 1 < data.length && Character.isDigit(data[currentIndex + 1])) {
					number += character.toString();
					currentIndex++;
					isInteger = false;
					continue;
				} else {
					break;
				}
			}

			if (Character.isDigit(character)) {
				number += character.toString();
				currentIndex++;
			} else {
				break;
			}
		}

		if (isNegative) {
			number = "-" + number;
		}

		if (isInteger) {
			try {
				Integer intNumber = Integer.parseInt(number);
				return new SmartScriptToken(SmartScriptTokenType.NUMBER, intNumber);
			} catch (NumberFormatException e) {
				throw new LexerException("Invalid integer number in Lexer file!");
			}
		} else {
			try {
				Double doubleNumber = Double.parseDouble(number);
				return new SmartScriptToken(SmartScriptTokenType.NUMBER, doubleNumber);
			} catch (NumberFormatException e) {
				throw new LexerException("Invalid double number in Lexer file!");
			}
		}
	}

	/**
	 * 
	 * Method that generates TEXT token.
	 * 
	 */
	private SmartScriptToken generateTextToken() {
		Character letter;

		String wordToken = "";
		while (currentIndex < data.length) {
			letter = data[currentIndex];

			if (letter.toString().equals("{"))
				break;

			if (letter.toString().equals("\\"))
				letter = extraTextAnaylsis();

			wordToken += letter.toString();
			currentIndex++;
		}
		return new SmartScriptToken(SmartScriptTokenType.TEXT, wordToken);
	}

	/**
	 * 
	 * Method that generates FUNCTION token.
	 * 
	 */
	private SmartScriptToken generateFunctionToken() {
		Character character;

		String functionId = "";
		String skipOperators = " \r\n\t $";
		while (currentIndex < data.length) {
			character = data[currentIndex];
			if (skipOperators.contains(character.toString()))
				break;

			functionId += character.toString();
			currentIndex++;
		}

		checkId(functionId);
		return new SmartScriptToken(SmartScriptTokenType.FUNCTION, functionId);
	}

	/**
	 * Method that generates STRING token.
	 */
	private SmartScriptToken generateStringToken() {
		Character character;

		String string = "";
		while (currentIndex < data.length) {
			character = data[currentIndex];
			
			if (character.charValue() == '"')
				break;

			if (character.toString().equals("$"))
				throw new LexerException("The quotes must be closed!");

			if (character.charValue() == '\\') {
				System.out.println(data[currentIndex+1]);
				if (data[currentIndex + 1] != '\"' && data[currentIndex + 1] != '\\') {
					throw new LexerException("Invalid escaping!");
				}

				currentIndex++;
				character = data[currentIndex];
				string += character.toString();
				currentIndex++;
				continue;
			}

			string += character.toString();
			currentIndex++;
		}

		currentIndex++;
		return new SmartScriptToken(SmartScriptTokenType.TAG_TEXT, string);
	}

	/**
	 * 
	 * This method performs some extra analysis on the text. For example, it check
	 * whether the escape sign is at the of the string or if there is some other
	 * escape displacement.
	 * 
	 */
	private Character extraTextAnaylsis() {
		if (currentIndex + 1 >= data.length) {
			throw new LexerException("Escape char \\ can not be the last char in the text!");
		}

		currentIndex++;
		Character character = data[currentIndex];

		if (!character.toString().equals("{") && !character.toString().equals("\\")) {
			throw new LexerException("After \\ there has to be { or another escape character!");
		}

		return character;
	}

	/**
	 * 
	 * This method generates tokens for FOR_TAG state.
	 * 
	 */
	private SmartScriptToken generateForTagToken() {
		Character character = data[currentIndex];

		if (checkForDollarSign()) {
			return token;
		}

		if (checkIfExitingTag()) {
			return token;
		}

		if (Character.isLetter(character)) {
			return generateIdToken();
		}

		if (Character.isDigit(character)) {
			return generateNumberToken(false);
		}

		if (checkForSignedNumbers()) {
			return token;
		}
		
		if (checkForStringNumber()) {
			return token;
		}

		throw new LexerException("Wrong input in the for-tag field! Tag: " + character);
	}

	/**
	 * 
	 * This method generates tokens for ECHO_TAG state.
	 * 
	 */
	private SmartScriptToken generateEchoTagToken() {
		Character character = data[currentIndex];

		if (checkForDollarSign()) {
			return token;
		}

		if (checkIfExitingTag()) {
			return token;
		}

		if (Character.isLetter(character)) {
			return generateIdToken();
		}

		if (Character.isDigit(character)) {
			return generateNumberToken(false);
		}

		if (checkForSignedNumbers()) {
			return token;
		}

		if (checkForOperators()) {
			return token;
		}

		if (checkForFunction()) {
			return token;
		}

		if (checkForString()) {
			return token;
		}

		throw new LexerException("Wrong input in the echo-tag field! Tag: " + character);

	}

	/**
	 * 
	 * This method checks whether the ID or FUNCTION_ID is properly named.
	 * 
	 */
	private void checkId(String id) {
		for (int i = 0; i < id.length(); i++) {
			Character character = id.charAt(i);
			if (i == 0 && !Character.isLetter(character)) {
				throw new LexerException("Id error! Should start with letter! Id: " + id);
			}

			if (i != 0 && !(Character.isLetter(character) || Character.isDigit(character)
					|| character.toString().equals("_"))) {
				throw new LexerException("Id error! Id should only contain letters, numbers and underscore! Id: " + id);
			}
		}
	}

	/**
	 * 
	 * This method checks whether the next few symbols represent signed number.
	 * 
	 */
	private boolean checkForSignedNumbers() {
		Character character = data[currentIndex];
		if (character.toString().equals("-") || character.toString().equals("+")) {
			if (currentIndex + 1 < data.length && Character.isDigit(data[currentIndex + 1])) {
				currentIndex++;

				if (character.toString().equals("-")) {
					token = generateNumberToken(true);
				} else {
					token = generateNumberToken(false);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * This method checks whether the current symbol is operator.
	 */
	private boolean checkForOperators() {
		Character character = data[currentIndex];
		String operators = "+-*/^";

		if (operators.contains(character.toString())) {
			token = new SmartScriptToken(SmartScriptTokenType.SYMBOL, character.toString());
			currentIndex++;
			return true;
		}
		return false;
	}

	/**
	 * 
	 * This method checks whether the current symbol represents function token.
	 * 
	 * @return
	 */
	private boolean checkForFunction() {
		Character character = data[currentIndex];

		if (character.toString().equals("@")) {
			currentIndex++;
			token = generateFunctionToken();
			return true;
		}

		return false;
	}

	/**
	 * 
	 * This method checks whether the current symbol represents tag string token.
	 * 
	 * @return true if they are, otherwise false
	 */
	private boolean checkForString() {
		Character character = data[currentIndex];

		if (character.charValue() == '"') {
			currentIndex++;
			token = generateStringToken();
			return true;
		}

		return false;
	}
	
	/**
	 * This method checks whether the numbers in for loop are string.
	 * @return true if they are, false otherwise
	 */
	private boolean checkForStringNumber() {
		Character character = data[currentIndex];
		
		if(character.charValue() == '"') {
			currentIndex++;
			if(checkForSignedNumbers()) {
				currentIndex++;
				return true;
			} else {
				token = generateNumberToken(false);
				currentIndex++;
				return true;
			}
		}
		return false;
	}

}
