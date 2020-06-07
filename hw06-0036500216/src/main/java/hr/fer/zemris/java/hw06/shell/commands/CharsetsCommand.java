package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Implementation of the {@link ShellCommand} interface. This class is
 * responsible for executing the charsets command that will return a list of the
 * available charsets for this {@link MyShell} instance.
 * 
 * @author ilovrencic
 *
 */
public class CharsetsCommand implements ShellCommand {

	/* ===============CONSTANTS=================== */
	private static final String NAME = "charsets";
	private static final List<String> DESCRIPTION = new ArrayList<String>();

	static {
		DESCRIPTION.add("This command is called with no arguments and lists all");
		DESCRIPTION.add("names of charsets supported on your Java platform.");
	}
	/* ============================================ */

	/**
	 * Method returns/prints a list of charsets that are available.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (env == null) {
			throw new NullPointerException("Environment can't be null!");
		}

		if (arguments == null) {
			throw new NullPointerException("Arguments can't be null!");
		}

		if (!arguments.isEmpty()) {
			System.err.println("There shouldn't be arguments for charsets command!");
		} else {
			SortedMap<String, Charset> charsets = Charset.availableCharsets();
			for (Map.Entry<String, Charset> charset : charsets.entrySet()) {
				env.writeln(charset.getKey());
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
		return DESCRIPTION;
	}

}
