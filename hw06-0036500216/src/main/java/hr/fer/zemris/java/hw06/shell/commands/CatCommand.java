package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.util.ParsingUtils;

/**
 * Implementation of the {@link ShellCommand} interface. This class is used for
 * printing the text from the file path that was passed as an argument.
 * 
 * @author ilovrencic
 *
 */
public class CatCommand implements ShellCommand {

	/* ===============CONSTANTS=================== */
	private static final String NAME = "cat";
	private static final List<String> DESCRIPTION = new ArrayList<String>();

	static {
		DESCRIPTION.add("This command takes one or two arguments. The first ");
		DESCRIPTION.add("one is mandatory and it is the path to some file. The ");
		DESCRIPTION.add("second one is charset name which sould be uset to ");
		DESCRIPTION.add("interpret chars from bytes.");
	}
	/* ============================================ */

	/**
	 * This method calls methods that will take care of printing the containments of
	 * the file we want to print.
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
		if (args.length == 1) {
			fileOutput(args[0], Charset.defaultCharset().name(), env);
		} else if (args.length == 2) {
			fileOutput(args[0], args[1], env);
		} else {
			System.err.println("Wrong number of arguments for cat command! Number of arguments: " + args.length);
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
	 * Method that opens a {@link BufferedReader} for the path that was given. Every
	 * line from the file is than printed to the user.
	 * 
	 * @param path - to the file we want to print on the screen
	 * @param charset - we want to use when we print
	 * @param env - {@link Environment} instance
	 */
	private void fileOutput(String path, String charset, Environment env) {
		Path filePath = Paths.get(path);
		File file = filePath.toFile();

		if (!file.isFile()) {
			System.err.println("Entered path doesn't lead to file!");
			return;
		}

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(Files.newInputStream(filePath)), charset))) {

			String line = reader.readLine();
			while (line != null) {
				env.writeln(line);
				line = reader.readLine();
			}

		} catch (IOException e) {
			System.err.println("Something went wrong, while reading the file!");
		}
	}

}
