package hr.fer.zemris.java.hw06.shell.util;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ShellIOException;

public class ParsingUtils {

	/**
	 * Method that parses passed arguments. Depending on the structure of the
	 * arguments we will call simple or complex argument parsing.
	 * 
	 * @param arguments - string of arguments
	 * @return - list of parsed arguments
	 */
	public static String[] parse(String arguments) {
		String[] args;

		if (arguments.isEmpty()) {
			return new String[0];
		}

		if (arguments.contains("\"")) {
			args = parseArgumentsComplex(arguments);
		} else {
			args = parseArgumentsSimple(arguments);
		}

		return args;
	}

	/**
	 * Method that simply splits the arguments and returns the list
	 * 
	 * @param arguments
	 * @return
	 */
	private static String[] parseArgumentsSimple(String arguments) {
		String[] args = arguments.split("\\s+|\\t+|\\n+|\\r+");
		return args;
	}

	/**
	 * Method that parses complex expressions for arguments. E.g "C:/Ivan
	 * Lovrencic/Documents/..."
	 * 
	 * @param arguments - arguments for command
	 * @return - list of parsed documents
	 */
	private static String[] parseArgumentsComplex(String arguments) {
		char[] letters = arguments.toCharArray();
		List<String> args = new ArrayList<String>();

		boolean insideExpression = false;
		String currentWord = "";
		for (int i = 0; i < letters.length; i++) {
			Character c = letters[i];
			if (c.charValue() == '"') {
				if (insideExpression) {

					if (i + 1 <= letters.length - 1 && letters[i + 1] != ' ') {
						throw new ShellIOException("Wrong argument! You have entered: " + arguments);
					}

					args.add(currentWord);
					currentWord = "";
					insideExpression = false;
					continue;
				} else {
					insideExpression = true;
					continue;
				}
			}

			if (c.charValue() == ' ') {
				if (insideExpression) {
					currentWord += c.toString();
					continue;
				} else {
					args.add(currentWord);
					currentWord = "";
					continue;
				}
			}

			currentWord += c.toString();
		}

		String[] argumentsList = new String[args.size()];
		for (int i = 0; i < args.size(); i++) {
			argumentsList[i] = args.get(i);
		}

		return argumentsList;
	}
}
