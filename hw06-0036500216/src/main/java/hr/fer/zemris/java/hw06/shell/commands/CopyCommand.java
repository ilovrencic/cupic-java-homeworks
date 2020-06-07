package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.util.ParsingUtils;

/**
 * Class that implements {@link ShellCommand} interface. This command is used
 * for copying the contents of one file to another.
 * 
 * @author ilovrencic
 *
 */
public class CopyCommand implements ShellCommand {

	/* ===============CONSTANTS=================== */
	private static final String NAME = "copy";
	private static final List<String> DESCRIPTION = new ArrayList<String>();

	static {
		DESCRIPTION.add("This command takes two arguments. The first ");
		DESCRIPTION.add("one is the source file name and the second one ");
		DESCRIPTION.add("is the destination file name. If the destination ");
		DESCRIPTION.add("file exists the user will be asked if it is ok to ");
		DESCRIPTION.add("overwrite it. The source file can not be a ");
		DESCRIPTION.add("directory, but the destination can and it is ");
		DESCRIPTION.add("than assumed that the user wants to copy the ");
		DESCRIPTION.add("source file into that directory.");
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

		String args[] = ParsingUtils.parse(arguments);
		if (args.length != 2) {
			System.err.println("Copy command requires two arguments! You entered: " + args.length);
		} else {
			copy(args[0], args[1], env);
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
	 * This method is taking two arguments. First one is the path to original file
	 * and the second one is to the copied file. If the second argument is
	 * directory, the method will create a file with identical name.
	 * 
	 * @param original - path to original file
	 * @param copy     - path to copied file
	 * @param env      - instance of the {@link Environment}
	 */
	private void copy(String original, String copy, Environment env) {
		if (original == null || copy == null) {
			throw new NullPointerException("File path shouldn't be null!");
		}

		Path originalPath = Paths.get(original);
		Path copiedPath = Paths.get(copy);
		File originalFile = originalPath.toFile();
		File copiedFile = copiedPath.toFile();

		if (originalFile.isDirectory()) {
			System.err.println("Entered path is not a file, but a directory!");
		}

		if (copiedFile.isDirectory()) {
			copy += "/"+originalFile.getName();
			copiedPath = Paths.get(copy);
		}

		try (InputStream is = new BufferedInputStream(Files.newInputStream(originalPath, StandardOpenOption.READ));
				OutputStream os = new BufferedOutputStream(
						Files.newOutputStream(copiedPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE))) {

			byte buff[] = new byte[4096];

			while (true) {
				int numberOfBytes = is.read(buff);

				if (numberOfBytes == -1) {
					break;
				}

				os.write(buff, 0, numberOfBytes);
			}

			os.flush();
			env.writeln("Copying went succesfully!");
		} catch (IOException e) {
			throw new ShellIOException(e.getLocalizedMessage());
		}
	}

}
