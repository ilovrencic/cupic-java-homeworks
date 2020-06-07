package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Class that offers user a input screen to input numbers to create
 * {@link FractalViewer}.
 * 
 * @author ilovrencic
 *
 */
public class Newton {

	/**
	 * Method that takes {@link String} from input and parses it into
	 * {@link Complex} number. After that we are making a
	 * {@link ComplexRootedPolynomial} which is then passed to {@link ProducerImpl}.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		List<Complex> complex = new ArrayList<Complex>();
		Complex constant = Complex.ONE;

		int counter = 0;
		System.out.print("Constant > ");
		while (scan.hasNext()) {
			String line = scan.nextLine().trim();

			if (line.equals("done")) {
				if (complex.size() < 2) {
					System.err.println("You have to input atleast two roots! ");
					continue;
				} else {
					break;
				}
			}

			try {
				if (counter == 0) {
					constant = parse(line);
				} else {
					Complex c = parse(line);
					complex.add(c);
				}

				counter++;
			} catch (NumberFormatException e) {
				System.err.println("Parsing went wrong! Please enter root again!");
			}
			System.out.print("Root " + counter + "> ");
		}

		System.out.println("Image of fractal will appear shortly. Thank you!");
		scan.close();

		Complex[] roots = new Complex[complex.size()];
		complex.toArray(roots);

		ComplexRootedPolynomial p = new ComplexRootedPolynomial(constant, roots);
		FractalViewer.show(new ProducerImpl(p));
	}

	/**
	 * Method that parses {@link String} into {@link Complex} number. Function is
	 * going through every character are trying to determine real and complex part
	 * of the number.
	 * 
	 * @param s - input
	 * @return - Complex number
	 */
	private static Complex parse(String s) {
		String operands = "+-";
		String imaginary = "i";

		String real = "";
		String imag = "";

		char[] parts = s.toCharArray();

		String currentNumber = "";
		boolean isNegative = false;
		for (int i = 0; i < parts.length; i++) {
			if (String.valueOf(parts[i]).equals(" ")) {
				continue;
			}

			if (operands.contains(String.valueOf(parts[i]))) {
				if (!currentNumber.isEmpty()) {
					if (isNegative) {
						real = "-" + currentNumber;
					} else {
						real = currentNumber;
					}
					currentNumber = "";
				}

				String currentOperand = String.valueOf(parts[i]);
				if (currentOperand.equals("-"))
					isNegative = true;
				else
					isNegative = false;
				continue;
			}

			if (imaginary.contains(String.valueOf(parts[i]))) {
				if (currentNumber.isEmpty()) {
					if (isNegative) {
						imag = "-1";
					} else {
						imag = "1";
					}

				} else {
					if (isNegative) {
						imag = "-" + currentNumber;
					} else {
						imag = currentNumber;
					}
				}
				break;
			}

			currentNumber += String.valueOf(parts[i]);
		}

		if (!currentNumber.isEmpty() && real.isEmpty()) {
			if (isNegative) {
				real = "-" + currentNumber;
			} else {
				real = currentNumber;
			}
		}

		if (real.isEmpty()) {
			real = "0";
		}

		if (imag.isEmpty()) {
			imag = "0";
		}

		try {
			double parsedReal = Double.parseDouble(real);
			double parsedImag = Double.parseDouble(imag);
			return new Complex(parsedReal, parsedImag);
		} catch (NumberFormatException e) {
			System.err.println("Parsing went wrong. Make sure you entered a correct complex number!");
			return null;
		}
	}

}
