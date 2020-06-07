package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.util.ParsingUtils;

/**
 * Implementation of the {@link ShellCommand} interface. Here we are utilizing
 * the command for changing the prompt, multiline and morelines sign.
 * 
 * @author ilovrencic
 *
 */
public class SymbolCommand implements ShellCommand {

	/* ===============CONSTANTS=================== */
	private static final String PROMPT = "prompt";
	private static final String MORELINES = "morelines";
	private static final String MULTILINE = "multiline";

	private static final String NAME = "symbol";
	private static final List<String> DESCRIPTION = new ArrayList<String>();

	static {
		DESCRIPTION.add("This method changes shells prompt, morelines and multiline");
		DESCRIPTION.add("symbols. Use it to change this symbols into custom signs.");
	}
	/* ============================================ */

	/**
	 * Method executes this command by changing the current environmental symbols.
	 * Symbols for PROMPT, MORELINES and MULTILINES can be changed.
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

		if (args.length != 2) {
			System.err.println("Symbol command requires only two parameters! You provided: " + args.length);
		} else {
			if (args[1].length() != 1) {
				System.err.println("Symbol has to be exactly one character long! You entered: " + args[1] + "!");
			} else {
				if (args[0].toLowerCase().equals(PROMPT)) {
					env.writeln("Symbol for " + PROMPT.toUpperCase() + " has changed from " + env.getPromptSymbol()
							+ " to " + args[1].charAt(0));
					env.setPromptSymbol(args[1].charAt(0));
				} else if (args[0].toLowerCase().equals(MORELINES)) {
					env.writeln("Symbol for " + MORELINES.toUpperCase() + " has changed from " + env.getPromptSymbol()
							+ " to " + args[1].charAt(0));
					env.setMorelinesSymbol(args[1].charAt(0));
				} else if (args[0].toLowerCase().equals(MULTILINE)) {
					env.writeln("Symbol for " + MULTILINE.toUpperCase() + " has changed from " + env.getPromptSymbol()
							+ " to " + args[1].charAt(0));
					env.setMultilineSymbol(args[1].charAt(0));
				} else {
					System.err.println("Symbol command doesn't recognize this word: " + args[0] + "!");
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
