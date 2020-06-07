package hr.fer.zemris.java.hw06.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class that represents a main entry point to this {@link Crypto} program.
 * 
 * @author ilovrencic
 *
 */
public class Crypto {

	/**
	 * Size of bytes we want to store into a buffer
	 */
	private static final int BUFFER_SIZE = 4096;

	/**
	 * Main method of the program. Here we are checking which action will be
	 * performed.
	 * 
	 * @param args - user input on what we should do.
	 */
	public static void main(String[] args) {
		if (args.length == 2) {
			if (args[0].toLowerCase().equals("checksha")) {
				checksha(args[1]);
			} else {
				throw new IllegalArgumentException("Irregular keyword! Keyword entered: " + args[0]);
			}
		} else if (args.length == 3) {
			if (args[0].toLowerCase().equals("encrypt")) {
				encryptAndDecryptAlgorithm(args[1], args[2], Cipher.ENCRYPT_MODE);
			} else if (args[0].toLowerCase().equals("decrypt")) {
				encryptAndDecryptAlgorithm(args[1], args[2], Cipher.DECRYPT_MODE);
			} else {
				throw new IllegalArgumentException("Irregular keyword! Keyword entered: " + args[0]);
			}
		} else {
			throw new IllegalArgumentException("Irregular number of arguments!");
		}

	}

	/**
	 * This method does a digest of the {@link String} fileName that is passed.
	 * 
	 * @param fileName - name of the file we want to hash.
	 */
	private static void checksha(String fileName) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Please provide expected sha-256 digest for " + fileName + ":");
		System.out.print("> ");

		String userDigest = sc.nextLine().trim();

		sc.close();

		Path path = Paths.get("./" + fileName);
		try (InputStream is = new BufferedInputStream(Files.newInputStream(path, StandardOpenOption.READ))) {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] buff = new byte[BUFFER_SIZE];

			while (true) {
				int readBytes = is.read(buff);
				if (readBytes < 1)
					break;

				digest.update(buff, 0, readBytes);
			}

			byte[] hash = digest.digest();
			String fileDigest = Util.bytetohex(hash);

			if (userDigest.equals(fileDigest)) {
				System.out.println("Digesting completed! Digest of " + fileName + " matches expected digest!");
			} else {
				System.out.println("Digesting completed! Digest of " + fileName
						+ " did not match expected digest!\nDigest was: " + fileDigest);
			}
			is.close();

		} catch (IOException | NoSuchAlgorithmException e) {
		}
	}

	/**
	 * This method decrypts or encrypts based on a @param cipherMode that is passed.
	 * This method creates a new file in which will be written encrypted or
	 * decrypted version of the original file.
	 * 
	 * @param originalFileName  - name of the original file (it can be encrypted or
	 *                          decrypted)
	 * @param encryptedFileName - name of the file we will encrypt or decrypt
	 *                          depending on the cipherMode parameter
	 * @param cipherMode        - mode that determines whether we are doing
	 *                          encryption or decryption.
	 */
	private static void encryptAndDecryptAlgorithm(String originalFileName, String encryptedFileName, int cipherMode) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		System.out.print("> ");

		String keyText = sc.nextLine().trim();

		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		System.out.print("> ");

		String ivText = sc.nextLine().trim();
		sc.close();

		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));

		Path inputPath = Paths.get("./" + originalFileName);
		Path outputPath = Paths.get("./" + encryptedFileName);

		try (InputStream is = new BufferedInputStream(Files.newInputStream(inputPath, StandardOpenOption.READ));
				OutputStream os = new BufferedOutputStream(
						Files.newOutputStream(outputPath, StandardOpenOption.CREATE))) {

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(cipherMode, keySpec, paramSpec);

			byte[] buff = new byte[BUFFER_SIZE];
			byte[] cipheredBuffer;
			while (true) {
				int readBytes = is.read(buff);
				if (readBytes < 1)
					break;

				cipheredBuffer = cipher.update(buff, 0, readBytes);
				os.write(cipheredBuffer, 0, cipheredBuffer.length);
			}

			cipheredBuffer = cipher.doFinal();
			os.write(cipheredBuffer, 0, cipheredBuffer.length);

			if (cipherMode == Cipher.ENCRYPT_MODE) {
				System.out.println("Encryption completed. Generated file " + encryptedFileName + " based on file "
						+ originalFileName + ".");
			} else {
				System.out.println("Decryption completed. Generated file " + encryptedFileName + " based on file "
						+ originalFileName + ".");
			}

			is.close();
			os.close();
		} catch (Exception e) {
			System.err.println("Error occured during encryption! Shuting down!");
			System.exit(0);
		}

	}
}
