package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
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
 * This class implements {@link ShellCommand} interface. This command takes one
 * argument and returns a tree of all directories,files and subdirectories from
 * passed path.
 * 
 * @author ilovrencic
 *
 */
public class TreeCommand implements ShellCommand {

	/* ===============CONSTANTS=================== */
	private static final String NAME = "tree";
	private static final List<String> DESCRIPTION = new ArrayList<String>();

	static {
		DESCRIPTION.add("This command is called with one argument ");
		DESCRIPTION.add("and that is the name of the directory. ");
		DESCRIPTION.add("The command than prints the tree of that directory.");
	}
	/* ============================================ */

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
			System.err.println("Tree command only accepts one argument! You have: " + args.length);
		} else {
			goThroughAllDirs(args[0], env);
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
	 * This method parses arguments and checks whether are all parameter properly
	 * defined.
	 * 
	 * @param directory - path to directory we want
	 * @param env       - instance of {@link Environment}
	 */
	private void goThroughAllDirs(String directory, Environment env) {
		if (directory == null) {
			throw new NullPointerException("Directory can't be null!");
		}

		Path path = Paths.get(directory);
		File dir = path.toFile();

		if (!dir.isDirectory()) {
			System.err.println("Entered path is not directory! Ls command requires directory!");
			return;
		}

		tree(dir, 0);
	}

	/**
	 * Recursive method that goes through all files,directories and subdirectories.
	 * 
	 * @param dir   - file from which we are checking all directories and files
	 * @param depth - depth of the tree
	 */
	private void tree(File dir, int depth) {
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				String output = "";
				for (int i = 0; i < depth; i++) {
					output += " ";
				}
				output += " > " + file.getName();
				System.out.println(output);

				tree(file, depth+3);
			} else {
				String output = "";
				for (int i = 0; i < depth; i++) {
					output += " ";
				}
				output += " - " + file.getName();
				System.out.println(output);
			}
		}
	}

}
