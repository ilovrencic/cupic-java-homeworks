package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class that represents Complex rooted polynomial form. E.g f(z) =
 * z0*(z-z1)*(z-z2)*...*(z-zn).
 * 
 * @author ilovrencic
 *
 */
public class ComplexRootedPolynomial {

	/**
	 * Represents a constant z0
	 */
	private Complex constant;

	/**
	 * Represents all the roots z1...zN
	 */
	private List<Complex> roots;

	/**
	 * Default constructor that initializes {@link ComplexRootedPolynomial}
	 * 
	 * @param constant - polynomial constant
	 * @param roots    - roots
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		Objects.requireNonNull(constant);
		Objects.requireNonNull(roots);

		this.constant = constant;
		this.roots = new ArrayList<Complex>();

		fillRoots(roots);
	}

	/**
	 * Method that applies concrete value z to Complex rooted polynomial
	 * 
	 * @param z - concrete value of {@link Complex}
	 * @return - generated value
	 */
	public Complex apply(Complex z) {
		if (z == null) {
			throw new NullPointerException("Passed z can't be null!");
		}

		Complex result = new Complex().add(constant);
		for (Complex root : roots) {
			Complex sub = z.sub(root);
			result = result.multiply(sub);
		}

		return result;
	}

	/**
	 * Method that transforms {@link ComplexRootedPolynomial} to
	 * {@link ComplexPolynomial}.
	 * 
	 * @return - transformed {@link ComplexPolynomial}
	 */
	public ComplexPolynomial toComplexPolynomial() {
		Complex[] factors = new Complex[roots.size() + 1];
		for (int i = roots.size(); i >= 0; i--) {
			factors[i] = i % 2 != 0 ? getFactor(i).negate() : getFactor(i);
		}

		return new ComplexPolynomial(factors);
	}

	/**
	 * Method that finds the closest root for given {@link Complex} number z.
	 * 
	 * @param z         - instance of {@link Complex} number z
	 * @param threshold - limit for the root similarity
	 * @return - index of the closest root
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		if(z == null) {
			throw new NullPointerException("Complex number can't be null!");
		}
		
		if(roots.size() == 0) {
			return -1;
		}
		
		int index = 0;
		double min = roots.get(index).sub(z).module();
		for(int i = 1; i < roots.size(); i++) {
			double current = roots.get(i).sub(z).module();
			if(current < min) {
				min = current;
				index = i;
			}
		}
		
		if(min < threshold) {
			return index;
		} else {
			return -1;
		}
	}
	
	@Override
	public String toString() {
		String output = "("+constant.toString()+")*";
		for(int i = 0; i < roots.size(); i++) {
			output += "( z - ("+roots.get(i)+") ) ";
		}
		return output;
	}

	/**
	 * Method that calculates factor for current power of root
	 * 
	 * @param level - power of the factor
	 * @return - factor
	 */
	private Complex getFactor(int level) {
		if (level == roots.size())
			return constant;
		Complex factor = Complex.ZERO;

		List<int[]> combinations = generate(roots.size() - level);
		for (int[] combination : combinations) {
			Complex current = Complex.ONE;
			for (int index : combination) {
				current = current.multiply(roots.get(index));
			}
			current = current.multiply(constant);
			factor = factor.add(current);
		}

		return factor;
	}

	/**
	 * Method that fills list with passed roots
	 * 
	 * @param roots
	 */
	private void fillRoots(Complex[] roots) {
		for (int i = 0; i < roots.length; i++) {
			this.roots.add(roots[i]);
		}
	}

	/**
	 * Method that generates a list of possible combinations for roots
	 * 
	 * @param r - number of combinations
	 * @return - list of array with combinations
	 */
	private List<int[]> generate(int r) {
		int n = roots.size();
		List<int[]> combinations = new ArrayList<int[]>();
		helper(combinations, new int[r], 0, n - 1, 0);
		return combinations;
	}

	/**
	 * Helper method that recursively makes arrays of combinations.
	 * 
	 * @param combinations - list of arrays with combinations
	 * @param data         - current array of combinations
	 * @param start        - starting index
	 * @param end          - ending index
	 * @param index        - current index
	 */
	private void helper(List<int[]> combinations, int data[], int start, int end, int index) {
		if (index == data.length) {
			int[] combination = data.clone();
			combinations.add(combination);
		} else if (start <= end) {
			data[index] = start;
			helper(combinations, data, start + 1, end, index + 1);
			helper(combinations, data, start + 1, end, index);
		}
	}

}
