package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeCommand;

/**
 * Main class. This is the entry point of this program. Here there will be all
 * organized and an instance of {@link MyShell} will be started.
 * 
 * @author ilovrencic
 *
 */
public class MyShell {

	/**
	 * Represents all the available commands at this {@link MyShell}.
	 */
	private static SortedMap<String, ShellCommand> commands = new TreeMap<>();

	/**
	 * Initialization of the commands
	 */
	static {
		commands.put("symbol", new SymbolCommand());
		commands.put("exit", new ExitCommand());
		commands.put("cat", new CatCommand());
		commands.put("charsets", new CharsetsCommand());
		commands.put("ls", new LsCommand());
		commands.put("tree", new TreeCommand());
		commands.put("copy", new CopyCommand());
		commands.put("mkdir", new MkdirCommand());
		commands.put("help", new HelpCommand());
		commands.put("hexdump", new HexdumpCommand());
	}

	/**
	 * Main method. Here we are going through a while loop, as long the status of
	 * the shell is not to TERMINATE.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ShellStatus status = ShellStatus.CONTINUE;
		Environment env = new EnvironmentImpl(commands);
		showShellIntro(env);

		while (status != ShellStatus.TERMINATE) {
			List<String> lines = readLines(env);
			for (String line : lines) {
				if (line.isEmpty()) {
					continue;
				}

				String commandName = parseCommandName(line).toLowerCase();
				String arguments = parseArguments(line);

				if (commands.get(commandName) == null) {
					env.writeln("Invaild command! You have entered: " + commandName);
					break;
				}

				try {
					ShellCommand command = commands.get(commandName);
					status = command.executeCommand(env, arguments);

					if (status == ShellStatus.TERMINATE) {
						break;
					}
				} catch (ShellIOException e) {
					env.writeln(e.getLocalizedMessage());
				}
			}
		}

		env.close();
	}

	/**
	 * This method reads lines of commands, then parses and returns them in a
	 * {@link List}.
	 * 
	 * @param env - Instance of the {@link Environment}
	 * @return - list of the commands user typed
	 */
	private static List<String> readLines(Environment env) {
		List<String> inputs = new ArrayList<String>();
		env.write(env.getPromptSymbol() + " ");
		String currentLine = env.readLine();

		if (!currentLine.endsWith(env.getMorelinesSymbol().toString())) {
			inputs.add(currentLine);
			return inputs;
		}

		while (currentLine.endsWith(env.getMorelinesSymbol().toString())) {
			currentLine = currentLine.substring(0, currentLine.length() - 2).trim();
			inputs.add(currentLine);

			env.write(env.getMultilineSymbol() + " ");
			currentLine = env.readLine();
		}

		inputs.add(currentLine);
		return inputs;
	}

	/**
	 * Method that prints {@link MyShell} intro.
	 * 
	 * @param env - Instance of the {@link Environment}
	 */
	private static void showShellIntro(Environment env) {
		env.writeln("Welcome to the MyShell v 1.0");
	}

	/**
	 * Method that parses line and extracts the command name.
	 * 
	 * @param line - string user typed
	 * @return - command name
	 */
	private static String parseCommandName(String line) {
		String[] args = line.split(" ");
		return args[0];
	}

	/**
	 * Method that parses users line and extracts only the arguments.
	 * 
	 * @param line - string user typed
	 * @return - arguments user typed after the command name
	 */
	private static String parseArguments(String line) {
		String[] args = line.split("\\s+|\\t+|\\n+|\\r+");
		String arguments = "";
		for (int i = 1; i < args.length; i++) {
			arguments += args[i] + " ";
		}

		return arguments.trim();
	}
}
