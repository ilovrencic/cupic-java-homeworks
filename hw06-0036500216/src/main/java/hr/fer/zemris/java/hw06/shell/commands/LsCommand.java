package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.util.ParsingUtils;

/**
 * Implementation of the {@link ShellCommand} interface. This method returns a
 * list of all the directories and files inside a given directory.
 * 
 * @author ilovrencic
 *
 */
public class LsCommand implements ShellCommand {

	/* ===============CONSTANTS=================== */
	private static final String NAME = "ls";
	private static final List<String> DESCRIPTION = new ArrayList<String>();

	static {
		DESCRIPTION.add("This command takes only one argument and");
		DESCRIPTION.add("that is the name of the directory whose content ");
		DESCRIPTION.add("the command is about to list. Lists the names of ");
		DESCRIPTION.add("all files and directories in the given directory ");
		DESCRIPTION.add("along with some other additional useful information.");
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
			System.err.println("ls command only accepts one argument! You have: " + args.length);
		} else {
			goThroughAllFiles(args[0], env);
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
	 * Method that goes through all files and lists them with some additional info.
	 * 
	 * @param directory - path of the directory we want to check
	 * @param env       - instance of the {@link Environment}
	 */
	private void goThroughAllFiles(String directory, Environment env) {
		if (directory == null) {
			throw new NullPointerException("Directory can't be null!");
		}

		Path path = Paths.get(directory);
		File dir = path.toFile();

		if (!dir.isDirectory()) {
			System.err.println("Entered path is not directory! Ls command requires directory!");
			return;
		}

		File[] files = dir.listFiles();
		int maxLength = longestFile(files);

		try {
			for (File file : files) {
				String actions = getActionsForFile(file);
				int size = String.valueOf(file.length()).length();

				String output = "";
				output += actions + "  ";

				for (int i = 0; i < maxLength - size; i++) {
					output += " ";
				}

				output += file.length() + "  ";
				output += getFileDate(file) + "  ";
				output += file.getName();
				env.writeln(output);
			}
		} catch (IOException e) {
			throw new ShellIOException("Something went wrong will executing the command!");
		}
	}

	/**
	 * Method that returns all the actions we can perform on certain file.
	 * 
	 * @param file - file we want to check for actions
	 * @return
	 */
	private String getActionsForFile(File file) {
		String actions = "";
		if (file.isDirectory()) {
			actions += "d";
		} else {
			actions += "-";
		}

		if (file.canRead()) {
			actions += "r";
		} else {
			actions += "-";
		}

		if (file.canWrite()) {
			actions += "w";
		} else {
			actions += "-";
		}

		if (file.canExecute()) {
			actions += "x";
		} else {
			actions += "-";
		}

		return actions;
	}

	/**
	 * Method that returns the length of the biggest file in files.
	 * 
	 * @param files - list of files
	 * @return
	 */
	private int longestFile(File[] files) {
		long length = 0;
		for (File file : files) {
			if (file.length() > length) {
				length = file.length();
			}
		}

		return String.valueOf(length).length();
	}

	/**
	 * Method that returns a file date of creation.
	 * 
	 * @param file - file which date of creation we want to get
	 * @return - date of creation
	 * @throws IOException
	 */
	private String getFileDate(File file) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Path path = file.toPath();
		BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes = faView.readAttributes();
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		return formattedDateTime;
	}

}
