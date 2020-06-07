package hr.fer.zemris.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a simple query parser. This class is responsible to
 * parse queries into {@link ConditionalExpression}s.
 * 
 * @author ilovrencic
 *
 */
public class Parser {

	/* ============= CONSTANTS ============= */
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	private static final String JMBAG = "jmbag";
	/* ===================================== */

	/**
	 * Represents an instance of {@link Lexer}
	 */
	private Lexer lexer;

	/**
	 * List of expressions that will be parser from tokens
	 */
	private List<ConditionalExpression> expressions;

	/**
	 * Default constructor
	 * 
	 * @param query
	 */
	public Parser(String query) {
		lexer = new Lexer(query);
		expressions = new ArrayList<ConditionalExpression>();

		parse();
	}

	/**
	 * Getter for expressions
	 * 
	 * @return
	 */
	public List<ConditionalExpression> getExpressions() {
		return expressions;
	}

	/**
	 * Method that parses tokens into {@link ConditionalExpression}. We are going
	 * through expressions and checking if they are following wanted query
	 * structure.
	 */
	private void parse() {
		Token currentToken = lexer.getNextToken();

		if (!currentToken.getType().equals(TokenType.QUERY)) {
			throw new ParserException("Query must begin with QUERY keyword!");
		}

		currentToken = lexer.getNextToken();

		if (currentToken.getType().equals(TokenType.EOF)) {
			throw new LexerException("Parser is parsing empty string!");
		}

		while (!currentToken.getType().equals(TokenType.EOF)) {
			if (currentToken.getType().equals(TokenType.FIELD)) {
				ConditionalExpression expression = handleQueryField(currentToken);
				expressions.add(expression);
				currentToken = lexer.getNextToken();
				continue;
			} else if (currentToken.getType().equals(TokenType.AND)) {
				currentToken = lexer.getNextToken();
				continue;
			} else {
				throw new ParserException("Invalid query!");
			}
		}
	}

	/**
	 * Method that parses field tokens into {@link ConditionalExpression}. We are
	 * checking whether every field token is followed by symbol token and value
	 * token.
	 * 
	 * @param currentToken
	 * @return
	 */
	private ConditionalExpression handleQueryField(Token currentToken) {
		IFieldValueGetter getter;

		if (currentToken.getValue().equals(FIRST_NAME)) {
			getter = FieldValueGetters.FIRST_NAME;
		} else if (currentToken.getValue().equals(LAST_NAME)) {
			getter = FieldValueGetters.LAST_NAME;
		} else if (currentToken.getValue().equals(JMBAG)) {
			getter = FieldValueGetters.JMBAG;
		} else {
			throw new ParserException("Unknown field name!");
		}

		currentToken = lexer.getNextToken();

		if (currentToken.getType().equals(TokenType.SYMBOL)) {
			IComparisonOperator operator = getOperatorFromToken(currentToken);

			currentToken = lexer.getNextToken();
			if (currentToken.getType().equals(TokenType.VALUE)) {
				String value = (String) currentToken.getValue();

				return new ConditionalExpression(operator, getter, value);
			} else {
				throw new ParserException("Irregular query!");
			}

		} else {
			throw new ParserException("Irregular query!");
		}
	}

	/**
	 * Method that from token returns an {@link ComparisonOperator}.
	 * 
	 * @param token
	 * @return
	 */
	private IComparisonOperator getOperatorFromToken(Token token) {
		if (token.getValue().equals("=")) {
			return ComparisonOperator.EQUALS;
		}

		if (token.getValue().equals("!=")) {
			return ComparisonOperator.NOT_EQUALS;
		}

		if (token.getValue().equals("<")) {
			return ComparisonOperator.LESS;
		}

		if (token.getValue().equals("<=")) {
			return ComparisonOperator.LESS_OR_EQUALS;
		}

		if (token.getValue().equals(">")) {
			return ComparisonOperator.GREATER;
		}

		if (token.getValue().equals(">=")) {
			return ComparisonOperator.GREATER_OR_EQUAL;
		}

		if (token.getValue().equals("LIKE")) {
			return ComparisonOperator.LIKE;
		}

		throw new ParserException("Query symbol is not recognized!");
	}
}
