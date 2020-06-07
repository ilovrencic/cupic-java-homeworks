package hr.fer.zemris.hw05.db;

/**
 * Interface that defines method for obtaining the requested field from
 * {@link StudentRecord}
 * 
 * @author ilovrencic
 *
 */
public interface IFieldValueGetter {

	/**
	 * Method that for a given {@link StudentRecord} returns the requested field
	 * 
	 * @param record - {@link StudentRecord}
	 * @return field we want from the {@link StudentRecord}
	 */
	public String get(StudentRecord record);

}
