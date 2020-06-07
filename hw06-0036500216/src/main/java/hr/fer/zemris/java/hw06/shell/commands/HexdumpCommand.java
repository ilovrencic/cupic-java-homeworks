package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.crypto.Util;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.util.ParsingUtils;

/**
 * This class implements {@link ShellCommand} interface. This class is used for
 * getting hexadecimal output of the file we want.
 * 
 * @author ilovrencic
 *
 */
public class HexdumpCommand implements ShellCommand {

	/* ===============CONSTANTS=================== */
	private static final String NAME = "hexdump";
	private static final List<String> DESCRIPTION = new ArrayList<String>();
	private static final int BUFFER_SIZE = 16;
	private static final byte DOT_BYTE = 0x2e;

	static {
		DESCRIPTION.add("This command takes one argument. The argument");
		DESCRIPTION.add("is a file name. The command than produces a hex");
		DESCRIPTION.add("output of the specified file.");
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
			System.err.println("Hexdump only accepts one argument! You have entered: " + args.length);
		} else {
			try {
				hexify(args[0], env);
			} catch (IOException e) {
				throw new ShellIOException(e.getLocalizedMessage());
			}
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(DESCRIPTION);
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	/**
	 * Method that will load the file and then hexify it by loading bytes and then
	 * turning that bytes into hexadecimal numbers.
	 * 
	 * @param filePath - path/name of the file we want to hexify
	 * @param env      - instance of the {@link Environment}
	 * @throws IOException
	 */
	private void hexify(String filePath, Environment env) throws IOException {
		Path path = Paths.get(filePath);
		File file = path.toFile();

		if (!file.isFile()) {
			System.err.println("Entered path isn't a file!");
		} else {

			InputStream is = new BufferedInputStream(Files.newInputStream(path, StandardOpenOption.READ));
			byte[] buff = new byte[BUFFER_SIZE];
			int row = 0;

			while (true) {
				int readBytes = is.read(buff);
				if (readBytes == -1)
					break;

				printRow(Arrays.copyOf(buff, readBytes), env, row);
				row += readBytes;
			}
		}
	}

	/**
	 * Method that prints one row in hexadecimal dump. First we print row number and
	 * then we have to split hexadecimal string into series of jointly combined
	 * numbers.
	 * 
	 * @param buff - byte array
	 * @param env  - instance of {@link Environment}
	 * @param row  - current row number
	 */
	private void printRow(byte[] buff, Environment env, int row) {
		env.write(String.format("%08X: ", row));
		removeUnallowedSymbols(buff);

		String hex = Util.bytetohex(buff);
		printHex(hex, env);

		String text = new String(buff);
		env.writeln(text);
	}

	/**
	 * All unallowed symbols have to replaced by "." sign.
	 * 
	 * @param buff - byte array
	 */
	private void removeUnallowedSymbols(byte[] buff) {
		for (int i = 0; i < buff.length; i++) {
			if (buff[i] < 32 || buff[i] > 127) {
				buff[i] = DOT_BYTE;
			}
		}
	}

	/**
	 * Method that prints hexadecimal string into a series of hexadecimal symbols
	 * 
	 * @param hex - string that represents a hexadecimal
	 * @param env - instance of {@link Environment}
	 */
	private void printHex(String hex, Environment env) {
		int index = 0;
		while (index < BUFFER_SIZE * 2) {
			if (index % 16 == 0 && index != 0)
				env.write(" | ");
			if (index < hex.length()) {
				env.write(hex.substring(index, index + 2).toUpperCase() + " ");
			} else {
				env.write(String.format("%3s", ""));
			}
			index += 2;
		}
		env.write("| ");
	}
}
