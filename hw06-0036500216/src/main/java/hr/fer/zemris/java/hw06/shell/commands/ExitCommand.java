package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Implementation of the {@link ShellCommand} interface. This command is used
 * for closing/terminating the {@link MyShell}.
 * 
 * @author ilovrencic
 *
 */
public class ExitCommand implements ShellCommand {

	/* ===============CONSTANTS=================== */
	private static final String NAME = "exit";
	private static final List<String> DESCRIPTION = new ArrayList<String>();

	static {
		DESCRIPTION.add("This command is used for terminating the Shell. After we use");
		DESCRIPTION.add("it, the shell will close down. There is no extra parameters to");
		DESCRIPTION.add("this method.");
	}
	/* ============================================ */

	/**
	 * Method that terminates {@link MyShell} if there is no arguments. If there is
	 * arguments, method reports an error but the {@link MyShell} still continues to
	 * work.
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
			env.writeln("There shouldn't be any arguments for exit command!");
			return ShellStatus.CONTINUE;
		}

		return ShellStatus.TERMINATE;
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
