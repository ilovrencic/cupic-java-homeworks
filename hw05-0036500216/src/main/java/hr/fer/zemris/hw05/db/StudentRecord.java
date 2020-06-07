package hr.fer.zemris.hw05.db;

/**
 * Class that represent a student record in database.
 * 
 * @author ilovrencic
 *
 */
public class StudentRecord {

	/**
	 * Students jmbag
	 */
	private String jmbag;

	/**
	 * Student's first name
	 */
	private String firstName;

	/**
	 * Student's last name
	 */
	private String lastName;

	/**
	 * Student's final grade
	 */
	private int finalGrade;

	/**
	 * Default constructor
	 * 
	 * @param jmbag
	 * @param firstName
	 * @param lastName
	 * @param finalGrade
	 */
	public StudentRecord(String jmbag, String firstName, String lastName, int finalGrade) {
		this.jmbag = jmbag;
		this.firstName = firstName;
		this.lastName = lastName;
		this.finalGrade = finalGrade;
	}

	/* -------------- GETTERS --------------- */

	public int getFinalGrade() {
		return finalGrade;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getJmbag() {
		return jmbag;
	}

	public String getLastName() {
		return lastName;
	}

	/* ------------------------------------- */

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StudentRecord) {
			StudentRecord record = (StudentRecord) obj;
			return record.jmbag.equals(jmbag);
		}

		return false;
	}

	@Override
	public int hashCode() {
		if (jmbag != null) {
			return 1997 * jmbag.hashCode();
		} else {
			return super.hashCode();
		}
	}
	
	@Override
	public String toString() {
		String output = "";
		output += jmbag + " " + firstName + " " + lastName + " " + String.valueOf(finalGrade);
		return output;
	}
}
