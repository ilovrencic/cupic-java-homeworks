package hr.fer.zemris.java.fractals;

import java.util.concurrent.Callable;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Class that encapsulates work thats necessary to draw fractal.
 * 
 * @author ilovrencic
 *
 */
public class CalculateFractalJob implements Callable<Void> {

	/**
	 * Represents minimal real value
	 */
	private double reMin;

	/**
	 * Represent maximal real value
	 */
	private double reMax;

	/**
	 * Represents minimal imaginary value
	 */
	private double imMin;

	/**
	 * Represents maximal imaginary value
	 */
	private double imMax;

	/**
	 * Represents minimal value of y
	 */
	private int yMin;

	/**
	 * Represents maximal value of y
	 */
	private int yMax;

	/**
	 * The height of the frame
	 */
	private int heigth;

	/**
	 * The width of the frame
	 */
	private int width;

	/**
	 * Represents an array to store data
	 */
	private short[] data;

	/**
	 * Represents a structure of the polynomial
	 */
	private ComplexRootedPolynomial poly;

	/**
	 * Threshold for convergence
	 */
	private static double CONVERGENCE = 1E-04;

	/**
	 * Number of iterations
	 */
	private static int ITERATIONS = 16*16*16;

	/**
	 * Default constructor
	 * 
	 * @param reMin  - initial value of real minimum
	 * @param reMax  - initial value of real maximum
	 * @param imMin  - initial value of imaginary minimum
	 * @param imMax  - initial value of imaginary maximum
	 * @param yMin   - initial value of y minimum
	 * @param yMax   - initial value of y maximum
	 * @param heigth - height of the frame
	 * @param width  - width of the frame
	 * @param data   - data we want to store
	 * @param poly   - polynomial
	 */
	public CalculateFractalJob(double reMin, double reMax, double imMin, double imMax, int yMin, int yMax, int heigth,
			int width, short[] data, ComplexRootedPolynomial poly) {
		super();
		this.data = data;
		this.heigth = heigth;
		this.width = width;
		this.reMax = reMax;
		this.reMin = reMin;
		this.imMax = imMax;
		this.imMin = imMin;
		this.poly = poly;
		this.yMin = yMin;
		this.yMax = yMax;
	}

	/**
	 * Method where we calculate the data for the {@link FractalViewer}.
	 */
	public Void call() throws Exception {
		ComplexPolynomial polynom = poly.toComplexPolynomial();
		ComplexPolynomial derived = poly.toComplexPolynomial().derive();

		for (int y = yMin; y <= yMax; y++) {
			for (int x = 0; x < width; x++) {
				double c_re = ((double) x / (width - 1)) * (reMax - reMin) + reMin;
				double c_im = ((double) y / (heigth - 1)) * (imMax - imMin) + imMin;


				Complex zn = new Complex(c_re, c_im);
				int iteration = 0;
				double module;

				do {
					Complex numerator = polynom.apply(zn);
					Complex denominator = derived.apply(zn);
					Complex fraction = numerator.divide(denominator);
					Complex zn_new = zn.sub(fraction);
					
					module = zn_new.sub(zn).module();
					zn = zn_new;
					iteration++;
				} while (iteration < ITERATIONS && (Math.abs(module) > CONVERGENCE));

				int index = poly.indexOfClosestRootFor(zn, CONVERGENCE);
				int offset = (y % heigth) * width+x;
				data[offset] = (short)(index+1);
			}
		}

		return null;

	}

}
