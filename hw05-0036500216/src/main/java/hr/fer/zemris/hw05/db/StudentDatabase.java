package hr.fer.zemris.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that holds all student records that it got in from of {@link List}
 * through constructor. Then it parses it to {@link StudentRecord}. Class offer
 * {@link List} collection for filtering and {@link Map} for O(1) acquiring.
 * 
 * @author ilovrencic
 *
 */
public class StudentDatabase {

	/**
	 * Collection that stores {@link StudentRecord}s
	 */
	private List<StudentRecord> studentRecords;

	/**
	 * Collection that stores {@link StudentRecord}s, and the key is student jmbag.
	 */
	private Map<String, StudentRecord> index;

	/**
	 * Default constructor
	 * 
	 * @param records - records in a form of {@link String} which need to be parsed
	 *                into {@link StudentRecord}.
	 */
	public StudentDatabase(List<String> records) {
		studentRecords = new ArrayList<StudentRecord>();
		index = new HashMap<String, StudentRecord>();

		transformRecords(records);
	}

	/**
	 * Method that returns {@link StudentRecord} for given jmbag. If there is no
	 * student with that specific jmbag, returns null. This call works in O(1) time,
	 * because we are using {@link Map}.
	 * 
	 * @param jmbag - for which we want {@link StudentRecord}
	 * @return {@link StudentRecord} or if there is no student record with that
	 *         jmbag, returns null.
	 */
	public StudentRecord forJMBAG(String jmbag) {
		if (jmbag == null) {
			throw new NullPointerException("Jmbag shouldn't be null!");
		}

		return index.get(jmbag);
	}

	/**
	 * Method that for a given {@link IFilter} filters through dataset and returns
	 * filtered {@link StudentRecord} dataset.
	 * 
	 * @param filter - instance that filters through dataset
	 * @return - filtered dataset
	 */
	public List<StudentRecord> filter(IFilter filter) {
		if (filter == null) {
			throw new NullPointerException("Filter shouldn't be null!");
		}

		List<StudentRecord> filteredRecords = new ArrayList<StudentRecord>();
		for (StudentRecord record : studentRecords) {
			if (filter.accepts(record)) {
				filteredRecords.add(record);
			}
		}

		return filteredRecords;
	}

	/**
	 * Method that transforms {@link String} array into {@link StudentRecord} array.
	 * 
	 * @param stringRecords - string array of student records
	 */
	private void transformRecords(List<String> stringRecords) {
		for (String stringRecord : stringRecords) {
			StudentRecord record = getStudentRecord(stringRecord);

			if(record == null) {
				continue;
			}
			
			if (index.get(record.getJmbag()) != null) {
				throw new IllegalArgumentException("There is a duplicate student record in database! Shutting down!");
			}

			index.put(record.getJmbag(), record);
			studentRecords.add(record);
		}

	}

	/**
	 * Method that parses string student record into {@link StudentRecord}.
	 * 
	 * @param stringRecord - student record in string
	 * @return {@link StudentRecord}
	 */
	private StudentRecord getStudentRecord(String stringRecord) {
		String[] parts = stringRecord.trim().split("\\s+|\\t+|\\n+|\\r+");
		if (parts.length != 4) {
			return null;
		}

		try {
			int finalGrade = Integer.parseInt(parts[3]);
			return new StudentRecord(parts[0], parts[2], parts[1], finalGrade);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Final grade is not the right type! It should be integer!");
		}
	}

}
