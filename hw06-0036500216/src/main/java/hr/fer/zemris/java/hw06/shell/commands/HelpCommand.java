package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.util.ParsingUtils;

/**
 * Class that implements {@link ShellCommand}. This class is used for retrieving
 * information about commands.
 * 
 * @author ilovrencic
 *
 */
public class HelpCommand implements ShellCommand {

	/* ===============CONSTANTS=================== */
	private static final String NAME = "help";
	private static final List<String> DESCRIPTION = new ArrayList<String>();

	static {
		DESCRIPTION.add("This command when executed without arguments ");
		DESCRIPTION.add("lists the names of all available commands. ");
		DESCRIPTION.add("If called with a single argument it prints the ");
		DESCRIPTION.add("name and the description of the selected command.");
	}
	/* ============================================ */

	/**
	 * Method that either prints description about certain command or lists all
	 * available commands.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (env == null) {
			throw new NullPointerException("Environment can't be null!");
		}

		if (arguments == null) {
			throw new NullPointerException("Arguments can't be null!");
		}

		String[] args = ParsingUtils.parse(arguments);
		if (args.length == 0) {
			printAllCommands(env);
		} else if (args.length == 1) {
			printDescription(args[0], env);
		} else {
			System.err.println("Number of arguments for help command isn't right!");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(DESCRIPTION);
	}

	/**
	 * Method that prints all available commands.
	 * 
	 * @param env - instance of the {@link Environment}
	 */
	private void printAllCommands(Environment env) {
		env.commands().forEach((k, v) -> env.writeln(k));
	}

	/**
	 * Method that prints description of certain command
	 * 
	 * @param commandName - command name
	 * @param env         - instance of the {@link Environment}
	 */
	private void printDescription(String commandName, Environment env) {
		ShellCommand command = env.commands().get(commandName);

		if (command != null) {
			command.getCommandDescription().forEach((k) -> env.writeln(k));
		} else {
			System.err.println("Entered command doesn't exist! You have entered: " + commandName);
		}
	}

}
