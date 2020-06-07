package hr.fer.zemris.hw05.db;

/**
 * Interface that serves as filter through {@link StudentRecord} records
 * 
 * @author ilovrencic
 *
 */
public interface IFilter {

	/**
	 * Method that checks whether we should acept passed {@link StudentRecord}.
	 * 
	 * @param record - for which we want to check whether it is accepted or not
	 * @return true if it is, otherwise false
	 */
	public boolean accepts(StudentRecord record);

}
