package hr.fer.zemris.java.hw02.demo;

import hr.fer.zemris.java.hw02.ComplexNumber;

/**
 * Class where we will show the usage of the class {@link ComplexNumber}
 * @author ilovrencic
 *
 */
public class ComplexDemo {

	/**
	 * Main method where we will show off the applications
	 * @param args
	 */
	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.fromImaginary(10);
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57))
		                  .div(c2).power(3).root(2)[1];
		System.out.println(c3);
	}

}
