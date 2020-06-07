package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptToken;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.zemris.java.custom.scripting.nodes.*;

/**
 * Class {@link SmartScriptParser}. It uses {@link SmartScriptLexer} to parse
 * simple expression.
 * 
 * @author ilovrencic
 *
 */
public class SmartScriptParser {

	/**
	 * Instance of {@link SmartScriptLexer} thats used for getting tokens.
	 */
	private SmartScriptLexer lexer;

	/**
	 * Instance of {@link ObjectStack} thats used for storing {@link Node}.
	 */
	private ObjectStack stack;

	/**
	 * Main node of the program
	 */
	private DocumentNode mainNode;

	/**
	 * Default constructor
	 * 
	 * @param document - document we want to parse
	 */
	public SmartScriptParser(String document) {
		lexer = new SmartScriptLexer(document);
		stack = new ObjectStack();
		mainNode = new DocumentNode();

		parse();
	}

	/**
	 * Getter method for {@link DocumentNode}
	 * 
	 * @return
	 */
	public DocumentNode getMainNode() {
		return mainNode;
	}

	/**
	 * Main method of the {@link SmartScriptParser}. Here we are going through all
	 * the {@link SmartScriptToken} and building a parse tree with {@link Node}.
	 */
	private void parse() {
		stack.push(mainNode);

		SmartScriptToken currentToken = lexer.nextToken();
		while (currentToken.getType() != SmartScriptTokenType.EOF) {

			if (currentToken.getType() == SmartScriptTokenType.TEXT) {

				TextNode textNode = new TextNode((String) currentToken.getValue());

				Node peekNode = (Node) stack.peek();
				peekNode.addChildNode(textNode);

				currentToken = lexer.nextToken();
				continue;
			}

			if (currentToken.getType() == SmartScriptTokenType.OPEN) {

				currentToken = lexer.nextToken();
				if (currentToken.getType() != SmartScriptTokenType.DOLLAR_SYMBOL) {
					throw new SmartScriptParserException("There should be a dollar symbol after open parentheses!");
				}

				currentToken = lexer.nextToken();
				continue;
			}

			if (currentToken.getType() == SmartScriptTokenType.KEYWORD) {
				parseKeyword();
				currentToken = lexer.getToken();
				continue;
			}

			if (currentToken.getType() == SmartScriptTokenType.DOLLAR_SYMBOL) {
				currentToken = lexer.nextToken();
				if (currentToken.getType() != SmartScriptTokenType.CLOSE) {
					throw new SmartScriptParserException("There should be a close parentheses after dollar sign!");
				}

				currentToken = lexer.nextToken();
				continue;
			}

			throw new SmartScriptParserException("Unable to parse!");
		}

		if (stack.size() != 1) {
			throw new SmartScriptParserException("Unfinished parsing!");
		}

		mainNode = (DocumentNode) stack.pop();
	}

	/**
	 * Method that parses a keyword. Keyword indicates that we are in the tag, so we
	 * have to do separate parsing there. We are technically building subtrees with
	 * every for-loop tag.
	 */
	private void parseKeyword() {
		SmartScriptToken currentToken = lexer.getToken();

		if (currentToken.getValue().equals("FOR")) {
			ForLoopNode forLoopNode = parseForLoop();

			Node peekNode = (Node) stack.peek();
			peekNode.addChildNode(forLoopNode);

			stack.push(forLoopNode);
			return;

		} else if (currentToken.getValue().equals("END")) {
			currentToken = lexer.nextToken();
			if (currentToken.getType() != SmartScriptTokenType.DOLLAR_SYMBOL) {
				throw new SmartScriptParserException("There should be dollar sign after END!");
			}

			currentToken = lexer.nextToken();
			if (currentToken.getType() != SmartScriptTokenType.CLOSE) {
				throw new SmartScriptParserException("There should be a close parentheses after dollar sign!");
			}

			currentToken = lexer.nextToken();
			
			stack.pop();
			if (stack.size() == 0) {
				throw new SmartScriptParserException("Wrong end tag! Stack size can't be zero!");
			}
			return;

		} else if (currentToken.getValue().equals("=")) {
			EchoNode echoNode = parseEcho();

			Node peekNode = (Node) stack.peek();
			peekNode.addChildNode(echoNode);

			return;
		} else {
			throw new SmartScriptParserException("Wrong keyword value!");
		}
	}

	/**
	 * Method that parses elements/tokens inside for-loop tag.
	 * 
	 * @return - for loop node, if there is no errors
	 */
	private ForLoopNode parseForLoop() {
		SmartScriptToken currentToken = lexer.nextToken();

		if (currentToken.getType() != SmartScriptTokenType.ID) {
			throw new SmartScriptParserException("Wrong value for ID!");
		}

		ElementVariable id = new ElementVariable((String) currentToken.getValue());
		currentToken = lexer.nextToken();

		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		elements.add(id);

		while (currentToken.getType() != SmartScriptTokenType.DOLLAR_SYMBOL) {

			if (currentToken.getType() == SmartScriptTokenType.ID) {

				elements.add(new ElementVariable((String) currentToken.getValue()));
				currentToken = lexer.nextToken();
				continue;
			}

			if (currentToken.getType() == SmartScriptTokenType.NUMBER) {

				if (currentToken.getValue() instanceof Double) {
					elements.add(new ElementConstantDouble((Double) currentToken.getValue()));
				} else if (currentToken.getValue() instanceof Integer) {
					elements.add(new ElementConstantInteger((Integer) currentToken.getValue()));
				} else {
					throw new SmartScriptParserException("Number is not in right format!");
				}

				currentToken = lexer.nextToken();
				continue;
			}

			throw new SmartScriptParserException("Wrong value in for loop!");
		}

		if (elements.size() >= 5 || elements.size() <= 2) {
			throw new SmartScriptParserException("Number of parameters in For Loop isn't allowed!");
		}

		if (elements.size() == 4) {
			return new ForLoopNode((ElementVariable) elements.get(0), (Element) elements.get(1),
					(Element) elements.get(2), (Element) elements.get(3));
		}

		if (elements.size() == 3) {
			return new ForLoopNode((ElementVariable) elements.get(0), (Element) elements.get(1),
					(Element) elements.get(2));
		}

		throw new SmartScriptParserException("Error parsing for-loop!");
	}

	/**
	 * Method that parses Echo tag. It goes through the all tokens until there is a
	 * dollar sign.
	 * 
	 * @return EchoNode - if there is no errors
	 */
	private EchoNode parseEcho() {
		SmartScriptToken currentToken = lexer.nextToken();
		ArrayIndexedCollection elements = new ArrayIndexedCollection();

		while (currentToken.getType() != SmartScriptTokenType.DOLLAR_SYMBOL) {

			if (currentToken.getType() == SmartScriptTokenType.ID) {
				elements.add((ElementVariable) new ElementVariable((String) currentToken.getValue()));
				currentToken = lexer.nextToken();
				continue;
			}

			if (currentToken.getType() == SmartScriptTokenType.NUMBER) {
				if (currentToken.getValue() instanceof Double) {
					elements.add(new ElementConstantDouble((Double) currentToken.getValue()));
				} else if (currentToken.getValue() instanceof Integer) {
					elements.add(new ElementConstantInteger((Integer) currentToken.getValue()));
				} else {
					throw new SmartScriptParserException("Number is not in right format!");
				}

				currentToken = lexer.nextToken();
				continue;
			}

			if (currentToken.getType() == SmartScriptTokenType.FUNCTION) {
				elements.add(new ElementFunction((String) currentToken.getValue()));
				currentToken = lexer.nextToken();
				continue;
			}

			if (currentToken.getType() == SmartScriptTokenType.TAG_TEXT) {
				elements.add(new ElementString((String) currentToken.getValue()));
				currentToken = lexer.nextToken();
				continue;
			}

			if (currentToken.getType() == SmartScriptTokenType.SYMBOL) {
				elements.add(new ElementOperator((String) currentToken.getValue()));
				currentToken = lexer.nextToken();
				continue;
			}

			throw new SmartScriptParserException("Wrong format of the Echo field!");
		}

		Element[] arrayElements = new Element[elements.size()];
		for (int i = 0; i < elements.size(); i++) {
			arrayElements[i] = (Element) elements.get(i);
		}

		return new EchoNode(arrayElements);
	}

	/**
	 * Recursive method that creates original example for which we got the
	 * {@link Node} tree.
	 * 
	 * @param document
	 * @return example which we parsed at the beginning
	 */
	public String createOriginalDocumentBody(Node document) {
		if (document == null) {
			return null;
		}

		String result = document.toString();
		int size = document.numberOfChildren();

		for (int i = 0; i < size; i++) {
			String s = createOriginalDocumentBody(document.getChild(i));
			result += s;
		}

		if (document instanceof ForLoopNode) {
			return result + "{$END$}";
		}

		return result;
	}

}
