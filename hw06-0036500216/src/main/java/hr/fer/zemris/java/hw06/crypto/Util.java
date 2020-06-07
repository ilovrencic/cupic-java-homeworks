package hr.fer.zemris.java.hw06.crypto;

/**
 * Utility class that offers method for transforming byte values into hexadecimal
 * and vice versa.
 * 
 * @author ilovrencic
 *
 */
public class Util {

	/**
	 * Method that for a given {@link String} in hex, returns a byte array. It works
	 * by going through every letter and than transforming that letter/digit into a
	 * byte.
	 * 
	 * @param hex - String that represents a hexadecimal
	 * @return byte array
	 */
	public static byte[] hextobyte(String hex) {
		if (hex == null) {
			throw new NullPointerException("String can't be null!");
		}

		if (hex.length() % 2 != 0) {
			throw new IllegalArgumentException("String needs to be an even length!");
		}

		if (hex.length() == 0) {
			return new byte[0];
		}

		hex = hex.toLowerCase();
		char[] characters = hex.toCharArray();

		byte[] array = new byte[characters.length / 2];
		int counter = 0;
		for (int i = 0; i < hex.length(); i += 2) {
			byte first, second;

			if (Character.isDigit(characters[i])) {
				first = findNumberForChar(characters[i]);
			} else {
				first = findLetterForChar(characters[i]);
			}

			if (Character.isDigit(characters[i + 1])) {
				second = findNumberForChar(characters[i + 1]);
			} else {
				second = findLetterForChar(characters[i + 1]);
			}

			first <<= 4;
			array[counter++] = (byte) (first + second);

		}
		return array;
	}

	/**
	 * Method that creates a hexadecimal {@link String} from {@link Byte} array.
	 * 
	 * @param bytes - byte array that we want to transform into hex
	 * @return String hex
	 */
	public static String bytetohex(byte[] bytes) {
		String hex = "";
		for (byte number : bytes) {
			byte first = (byte) (number >> 4);
			byte second = (byte) (number & 0xF);

			if (first < 0) {
				first -= 1;
				first ^= 0xF;
			}

			if (second < 0) {
				second -= 1;
				second ^= 0xF;
			}

			String firstLetter = findCharForByte(first);
			String secondLetter = findCharForByte(second);
			hex += firstLetter;
			hex += secondLetter;
		}

		return hex;
	}

	/**
	 * Method that for certain {@link Character} number returns byte value.
	 * 
	 * @param number - {@link Character} we want to change into byte
	 * @return - byte value of passed {@link Character} value
	 */
	private static byte findNumberForChar(Character number) {
		for (int i = 0; i <= 9; i++) {
			if (Integer.parseInt(number.toString()) == i) {
				return (byte) i;
			}
		}
		throw new IllegalArgumentException("Passed value isn't a number! Value: " + number.toString());
	}

	/**
	 * Method that returns byte for a passed {@link Character} letter.
	 * 
	 * @param letter - {@link Character} we passed
	 * @return - byte value of certain letter
	 */
	private static byte findLetterForChar(Character letter) {
		String acceptable = "abcdef";
		if (!acceptable.contains(letter.toString())) {
			throw new IllegalArgumentException("Passed value isn't acceptable letter! Value: " + letter.toString());
		}

		switch (letter) {
		case 'a':
			return (byte) 10;
		case 'b':
			return (byte) 11;
		case 'c':
			return (byte) 12;
		case 'd':
			return (byte) 13;
		case 'e':
			return (byte) 14;
		case 'f':
			return (byte) 15;
		default:
			throw new IllegalArgumentException("Passed value isn't acceptable letter! Value: " + letter.toString());
		}
	}

	/**
	 * Method that for certain {@link Byte} returns a hexadecimal value in
	 * {@link String} format.
	 * 
	 * @param data - byte we want transform into hex
	 * @return - hexadecimal value of byte
	 */
	private static String findCharForByte(byte data) {
		for (int i = 0; i <= 9; i++) {
			if (i == Math.abs(data)) {
				return String.valueOf(i);
			}
		}

		switch (Math.abs(data)) {
		case 10:
			return "a";
		case 11:
			return "b";
		case 12:
			return "c";
		case 13:
			return "d";
		case 14:
			return "e";
		case 15:
			return "f";
		default:
			throw new IllegalArgumentException("Passed argument isn't acceptable! Value: " + String.valueOf(data));
		}
	}

}
