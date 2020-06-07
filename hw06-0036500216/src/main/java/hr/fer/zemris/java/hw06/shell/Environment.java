package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Interface that represents {@link MyShell} environment. 
 * 
 * @author ilovrencic
 *
 */
public interface Environment {

	/**
	 * Method that's used for reading lines from {@link MyShell}.
	 * 
	 * @return - line that has been read
	 * @throws ShellIOException
	 */
	String readLine() throws ShellIOException;

	/**
	 * Method that's used for writing output from the command that has been
	 * performed.
	 * 
	 * @param text - string we want to print/show to the user on the shell
	 * @throws ShellIOException
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Method that's used for writing output but with line seperator.
	 * 
	 * @param text - string we want to print/show to the user on the shell
	 * @throws ShellIOException
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Method that returns a map of all commands and their keywords.
	 * 
	 * @return - sorted map of all commands and their keywords.
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Method that returns current multiline symbol
	 * 
	 * @return - current character for multiline
	 */
	Character getMultilineSymbol();

	/**
	 * Method that sets the new current multiline symbol
	 * 
	 * @param symbol - character we want as new multiline symbol
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Method that returns current prompt symbol.
	 * 
	 * @return - current prompt character
	 */
	Character getPromptSymbol();

	/**
	 * Method that sets new current prompt symbol
	 * 
	 * @param symbol - symbol we want as new prompt symbol
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Method that returns current morelines symbol
	 * 
	 * @return - current morelines symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Method that sets new current morelines symbol.
	 * 
	 * @param symbol - character we want as new morelines symbol.
	 */
	void setMorelinesSymbol(Character symbol);

	/**
	 * Method that safely closes all instances for reading and writing.
	 */
	void close();

}
