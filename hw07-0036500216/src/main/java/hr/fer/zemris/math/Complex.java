package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a Complex number in usual format (i+j).
 * 
 * @author ilovrencic
 *
 */
public class Complex {

	/* =====================CONSTANTS====================== */
	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);
	protected static final double EPSILON_VALUE = 1e-06;
	/* ==================================================== */

	/**
	 * Represents a real part of {@link Complex} number
	 */
	private double re;

	/**
	 * Represents an imaginary part of {@link Complex} number
	 */
	private double im;

	/**
	 * Default constructor that builds 0,0 {@link Complex} number
	 */
	public Complex() {
		this(0, 0);
	}

	/**
	 * Default constructor
	 * 
	 * @param re - real part
	 * @param im - imaginary part
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * Method that returns a module of {@link Complex} number
	 * 
	 * @return - module of complex number
	 */
	public double module() {
		return Math.sqrt(re * re + im * im);
	}

	/**
	 * Method that multiplies two complex numbers together
	 * 
	 * @param c - Instance of {@link Complex}
	 * @return - product of two complex numbers
	 */
	public Complex multiply(Complex c) {
		if (c == null) {
			throw new NullPointerException("Other complex number can't be null when you multiply!");
		}

		double newReal = re * c.re - im * c.im;
		double newImag = re * c.im + im * c.re;
		return new Complex(newReal, newImag);
	}

	/**
	 * Method that divides two complex numbers
	 * 
	 * @param c - Instance of {@link Complex}
	 * @return - product of two complex numbers
	 */
	public Complex divide(Complex c) {
		if (c == null) {
			throw new NullPointerException("Other complex number can't be null when you divide complex numbers!");
		}

		if (c.module() == 0) {
			throw new IllegalArgumentException("Module is zero!");
		}

		double newReal = (re * c.re + im * c.im) / (c.module() * c.module());
		double newImag = (im * c.re - re * c.im) / (c.module() * c.module());
		return new Complex(newReal, newImag);
	}

	/**
	 * Method that adds two {@link Complex} numbers
	 * 
	 * @param c - Instance of {@link Complex}
	 * @return - sum of two complex numbers
	 */
	public Complex add(Complex c) {
		return new Complex(re + c.re, im + c.im);
	}

	/**
	 * Method that subtracts two {@link Complex} numbers
	 * 
	 * @param c - Instance of {@link Complex}
	 * @return - subtraction of two complex numbers
	 */
	public Complex sub(Complex c) {
		return new Complex(re - c.re, im - c.im);
	}

	/**
	 * Method that negates {@link Complex} number
	 * 
	 * @return - negated {@link Complex} number
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}

	/**
	 * Method that calculates power of the {@link Complex}
	 * 
	 * @param n - power
	 * @return - complex number to the power of n
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("N can't be lower than zero!");
		}

		double magnitude = Math.pow(this.module(), n);
		double angle = this.getAngle() * n;
		return new Complex(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

	/**
	 * Method that calculates the n-th root of {@link Complex} number.
	 * 
	 * @param n - n-th root
	 * @return - list of roots
	 */
	public List<Complex> root(int n) {
		if (n < 1) {
			throw new IllegalArgumentException("n can't be lower than one!");
		}

		List<Complex> numbers = new ArrayList<Complex>();
		double magnitude = Math.pow(this.module(), 1 / (double) n);

		for (int i = 0; i < n; i++) {
			double currentAngle = (this.getAngle() + 2 * i * Math.PI) / ((double) n);
			currentAngle = transferAngle(currentAngle);

			double newRe = magnitude * Math.cos(currentAngle);
			double newIm = magnitude * Math.sin(currentAngle);
			numbers.add(new Complex(newRe, newIm));
		}

		return numbers;
	}

	/**
	 * Method that returns string version {@link Complex} number.
	 */
	@Override
	public String toString() {
		String output = re + "";
		if (im < 0) {
			output += " " + "-" + " " + Math.abs(im) + "j";
		} else {
			output += " " + "+" + " " + Math.abs(im) + "j";
		}

		return output;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Complex) {
			Complex c = (Complex) obj;
			if (Math.abs(c.re - re) < EPSILON_VALUE && Math.abs(c.im - im) < EPSILON_VALUE) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Method that returns angle of {@link Complex}. The angle is confined from 0 to
	 * 2*PI
	 * 
	 * @return
	 */
	private double getAngle() {
		// both values are zero, so we can return zero
		if (Math.abs(re) < EPSILON_VALUE && Math.abs(im) < EPSILON_VALUE) {
			return 0;
		}

		if (Math.abs(im) < EPSILON_VALUE) {
			return 0;
		}

		if (Math.abs(re) < EPSILON_VALUE) {
			if (im >= 0 && re >= 0) {
				return Math.PI / 2;
			} else if (im >= 0 && re <= 0) {
				return 3 * Math.PI / 2;
			} else if (im <= 0 && re >= 0) {
				return 3 * Math.PI / 2;
			} else {
				return Math.PI / 2;
			}
		}

		if (re >= 0 && im > 0) {
			return Math.atan(im / re);
		} else if (re < 0 && im > 0) {
			return Math.atan(im / re) + Math.PI;
		} else if (re < 0 && im < 0) {
			return Math.atan(im / re) + Math.PI;
		} else {
			return Math.atan(im / re) + Math.PI * 2;
		}

	}

	/**
	 * Method that transforms angle to 0 to 2*PI scale
	 * 
	 * @param angle - current angle
	 * @return - transfered angle
	 */
	private double transferAngle(double angle) {
		if (angle >= 0) {
			return angle % (Math.PI * 2);
		} else {
			return (angle % (Math.PI * 2)) + Math.PI * 2;
		}
	}

}
