package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.util.ParsingUtils;

/**
 * Class that implements {@link ShellCommand}. This command is used for creation
 * of new directory. Starting point of this program is naturally "." so all
 * mkdir command can only create a directory in that parent directory.
 * 
 * @author ilovrencic
 *
 */
public class MkdirCommand implements ShellCommand {

	/* ===============CONSTANTS=================== */
	private static final String NAME = "mkdir";
	private static final List<String> DESCRIPTION = new ArrayList<String>();
	private static final String STARTING_DIRECTORY = ".";

	static {
		DESCRIPTION.add("This command is called with one argument ");
		DESCRIPTION.add("and that is the name of the directory ");
		DESCRIPTION.add("to be created. The command than creates the");
		DESCRIPTION.add("specified directory structure.");
	}
	/* ============================================ */

	/**
	 * This method creates a new directory. Directory is made in predefined starting
	 * directory. Directory name can't be empty!
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
		if (args.length != 1) {
			System.err.println("Mkdir command only accepts one argument! You have entered: " + args.length);
		} else {
			if (args[0].length() == 0) {
				System.err.println("Argument for mkdir command can't be empty!");
			} else {
				try {
					Path startingDirectory = Paths.get(STARTING_DIRECTORY);
					Files.createDirectory(startingDirectory.resolve(args[0]));
					env.writeln("Directory successfully created!");
				} catch (IOException e) {
					throw new ShellIOException(e.getLocalizedMessage());
				}
			}
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

}
