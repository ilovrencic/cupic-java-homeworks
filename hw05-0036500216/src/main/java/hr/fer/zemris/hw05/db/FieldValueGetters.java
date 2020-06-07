package hr.fer.zemris.hw05.db;

/**
 * Class that defines implementations of {@link IFieldValueGetter}.
 * 
 * @author ilovrencic
 *
 */
public class FieldValueGetters {

	/**
	 * Class that defines a method that returns {@link StudentRecord} first name.
	 */
	public static final IFieldValueGetter FIRST_NAME = (record) -> record.getFirstName();

	/**
	 * Class that defines a method that returns {@link StudentRecord} last name.
	 */
	public static final IFieldValueGetter LAST_NAME = (record) -> record.getLastName();

	/**
	 * Class that defines a method that returns {@link StudentRecord} jmbag.
	 */
	public static final IFieldValueGetter JMBAG = (record) -> record.getJmbag();

}
