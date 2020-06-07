package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;

/**
 * Implementation of the interface {@link Environment}. This class servers and
 * current environment of the {@link MyShell}. Here we store all the commands
 * that are available, current characters for prompt, morelines and multiline.
 * We are using {@link Environment} to interact with user.
 * 
 * @author ilovrencic
 *
 */
public class EnvironmentImpl implements Environment {

	/* ===============CONSTANTS================= */
	private static final Character PROMPT = '>';
	private static final Character MORELINES = '/';
	private static final Character MULTILINE = '|';
	/* ========================================= */

	/**
	 * Collection that holds all commands that can be executed in {@link MyShell}.
	 */
	private SortedMap<String, ShellCommand> commands;

	/**
	 * Symbol that represents a prompt symbol in {@link MyShell}.
	 */
	private Character promptSymbol;

	/**
	 * Symbol that represents a morelines symbol in {@link MyShell}.
	 */
	private Character morelinesSymbol;

	/**
	 * Symbol that represents a multilines symbol in {@link MyShell}.
	 */
	private Character multilineSymbol;

	/**
	 * Instance with which we are reading inputs
	 */
	private Scanner sc;

	/**
	 * Default constructor
	 * 
	 * @param commands  - commands that are available in {@link MyShell}.
	 * @param prompt    - custom prompt symbol
	 * @param morelines - custom morelines symbol
	 * @param multiline - custom multiline symbol
	 */
	public EnvironmentImpl(SortedMap<String, ShellCommand> commands, Character prompt, Character morelines,
			Character multiline) {
		this.commands = commands;
		this.promptSymbol = prompt;
		this.morelinesSymbol = morelines;
		this.multilineSymbol = multiline;
		this.sc = new Scanner(System.in);
	}

	/**
	 * Default constructor
	 * 
	 * @param commands - commands that are available in {@link MyShell}
	 */
	public EnvironmentImpl(SortedMap<String, ShellCommand> commands) {
		this(commands, PROMPT, MORELINES, MULTILINE);
	}

	@Override
	public String readLine() throws ShellIOException {
		if (sc.hasNext()) {
			return sc.nextLine().trim();
		}
		throw new ShellIOException("There isn't anything to read!");
	}

	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return this.multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		this.multilineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return this.promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		this.promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return this.morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		this.morelinesSymbol = symbol;
	}

	@Override
	public void close() {
		this.sc.close();
	}

}
