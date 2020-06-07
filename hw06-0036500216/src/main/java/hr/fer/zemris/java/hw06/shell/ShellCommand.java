package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Interface that describes behavior of certain {@link MyShell} command.
 * 
 * @author ilovrencic
 *
 */
public interface ShellCommand {

	/**
	 * Method that executes current command with {@link String} arguments.
	 * 
	 * @param env       - {@link Environment} instance
	 * @param arguments
	 * @return - ShellStatus depending on how the command execution went
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Method that returns command name
	 * 
	 * @return - string that represents command name
	 */
	String getCommandName();

	/**
	 * Method that returns command description.
	 * 
	 * @return - list of strings that represents command description
	 */
	List<String> getCommandDescription();

}
