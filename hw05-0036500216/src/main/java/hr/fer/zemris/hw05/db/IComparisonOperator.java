package hr.fer.zemris.hw05.db;

/**
 * Interface that defines a method that checks whether comparison between two
 * {@link String}s is satisfied.
 * 
 * @author ilovrencic
 *
 */
public interface IComparisonOperator {

	/**
	 * Method that checks whether the comparison between two {@link String}s is
	 * satisfied.
	 * 
	 * @param value1 - string one
	 * @param value2 - string two
	 * @return - true if comparison is satisfied and false otherwise
	 */
	public boolean satisfied(String value1, String value2);

}
