package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class models the polynomial of type:
 * zn*z^n+(zn1)*z^(n-1)+...+z2*z^2+z1*z+z0 The zn, zn1, etc are the factors of
 * the polynomial. The factors are defined in the constructor.
 * 
 * @author ilovrencic
 *
 */
public class ComplexPolynomial {

	/**
	 * Represents a list of polynomial factors. E.g. zn...z0.
	 */
	private List<Complex> factors;

	/**
	 * Default constructor that takes a list of factors
	 * 
	 * @param factors - list of factors (z0, z1, .... zn)
	 */
	public ComplexPolynomial(Complex... factors) {
		Objects.requireNonNull(factors);
		if (factors.length == 0) {
			throw new IllegalArgumentException("You have to pass atleast one factor!");
		}

		int index = firstIndexOfNotNullFactor(factors);
		this.factors = new ArrayList<Complex>();

		for (int i = 0; i < index + 1; i++) {
			this.factors.add(factors[i]);
		}
	}

	/**
	 * Method that returns an order of polynom
	 * 
	 * @return - order of the polynom
	 */
	public short order() {
		return (short) (factors.size() - 1);
	}

	/**
	 * Method that multiplies two {@link ComplexPolynomial}
	 * 
	 * @param p - instance of {@link ComplexPolynomial}
	 * @return - product of multiplication
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		int currentOrder = this.order() + p.order();
		Complex[] newFactors = new Complex[currentOrder + 1];

		for (int i = 0; i < currentOrder + 1; i++) {
			newFactors[i] = Complex.ZERO;
		}

		for (int i = 0; i < currentOrder + 1; i++) {
			for (int j = 0; j <= i; j++) {
				Complex firstFactor;
				if (j < factors.size()) {
					firstFactor = factors.get(j);
				} else {
					firstFactor = Complex.ZERO;
				}

				Complex secondFactor;
				if (i - j < p.factors.size()) {
					secondFactor = p.factors.get(i - j);
				} else {
					secondFactor = Complex.ZERO;
				}

				newFactors[i] = newFactors[i].add(firstFactor.multiply(secondFactor));
			}
		}

		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Method that does first derivation over {@link ComplexPolynomial}. E.g. 2*z^2
	 * + 3*z^1 + 2 will be 4*z^1 + 3
	 * 
	 * @return
	 */
	public ComplexPolynomial derive() {
		Complex[] derivedPolynomial = new Complex[factors.size() - 1];

		for (int i = 1; i < factors.size(); i++) {
			derivedPolynomial[i - 1] = factors.get(i).multiply(new Complex(i, 0));
		}

		if (derivedPolynomial.length == 0) {
			return new ComplexPolynomial(new Complex(0, 0));
		} else {
			return new ComplexPolynomial(derivedPolynomial);
		}
	}

	/**
	 * Method that applies concrete value z, to the polynomial.
	 * 
	 * @param z - instance of the {@link Complex}
	 * @return - calculated value of the {@link ComplexPolynomial}
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		for (int i = 0; i < factors.size(); i++) {
			if (i == 0) {
				result = result.add(factors.get(i));
			} else {
				result = result.add(factors.get(i).multiply((z.power(i))));
			}
		}

		return result;
	}

	@Override
	public String toString() {
		String output = "";
		for (int i = factors.size() - 1; i >= 0; i--) {

			output += "(" + factors.get(i).toString() + ")" + "*z^" + String.valueOf(i) + " ";
		}
		return output;
	}

	/**
	 * This method is used to get only the non-zero factors. For example ( 0*z^2 +
	 * 3*z^1 + 2) is not a of order of two, but an order of one.
	 * 
	 * @param factors
	 * @return
	 */
	private int firstIndexOfNotNullFactor(Complex[] factors) {
		for (int i = factors.length - 1; i >= 0; i--) {
			if (!factors[i].equals(Complex.ZERO)) {
				return i;
			}
		}

		return 0;
	}

}
