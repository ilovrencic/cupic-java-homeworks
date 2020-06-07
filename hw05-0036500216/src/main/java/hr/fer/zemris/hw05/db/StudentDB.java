package hr.fer.zemris.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class that used to communicate with the {@link StudentDatabase}.
 * 
 * @author ilovrencic
 *
 */
public class StudentDB {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		StudentDatabase sdb = null;

		try {
			List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
			sdb = new StudentDatabase(lines);

		} catch (IOException e) {
			System.out.println("Could not read the file.");
			System.exit(0);
		} catch (ParserException e) {
			System.out.println("The error during parsing: " + e.getMessage());
			System.exit(0);
		}

		System.out.print("> ");

		while (sc.hasNext()) {
			String input = sc.nextLine().trim();
			if (input.equalsIgnoreCase("exit"))
				break;

			try {
				QueryParser parser = new QueryParser(input);

				if (parser.isDirectQuery()) {
					List<StudentRecord> students = new ArrayList<StudentRecord>();
					students.add(sdb.forJMBAG(parser.getQueriedJMBAG()));
					System.out.println("Using index for record retrieval.");
					printRecords(students);
				} else {
					List<StudentRecord> students = sdb.filter(new QueryFilter(parser.getQuery()));
					printRecords(students);

				}
			} catch (ParserException | LexerException e) {
				System.out.println("Something went wrong during the parsing! " + e.getLocalizedMessage());
			}

			System.out.print("> ");
		}

		System.out.println("Goodbye!");
		sc.close();

	}

	/**
	 * Method used for printing a {@link StudentRecord}.
	 * 
	 * @param student
	 */
	private static void printRecord(StudentRecord student, int first, int last) {
		String output = "";
		output += "|";

		output += " " + student.getJmbag().trim() + " " + "|" + " ";
		output += student.getLastName().trim();

		for (int i = 0; i < last - student.getLastName().trim().length(); i++) {
			output += " ";
		}

		output += " " + "|" + " ";
		output += student.getFirstName().trim();

		for (int i = 0; i < first - student.getFirstName().trim().length(); i++) {
			output += " ";
		}

		output += " " + "|" + " ";
		output += String.valueOf(student.getFinalGrade()) + " " + "|";
		System.out.println(output);
	}

	/**
	 * Method used for printing a list of {@link StudentRecord}
	 * 
	 * @param students
	 */
	private static void printRecords(List<StudentRecord> students) {
		int longestFirst = longestFirstName(students);
		int longestLast = longestlastName(students);

		if (students.isEmpty()) {
			System.out.println("Records retrieved: 0");
			return;
		}

		String output = generateFirstAndLastLine(longestFirst, longestLast);
		System.out.println(output);

		for (StudentRecord student : students) {
			printRecord(student, longestFirst, longestLast);
		}

		System.out.println(output);
		System.out.println("Records retrieved: " + String.valueOf(students.size()));

	}

	/**
	 * Method that returns the length of the longest last name.
	 * 
	 * @param students
	 * @return
	 */
	private static int longestlastName(List<StudentRecord> students) {
		int longestName = 0;
		for (StudentRecord record : students) {
			if (record.getLastName().length() > longestName) {
				longestName = record.getLastName().length();
			}
		}

		return longestName;

	}

	/**
	 * Method that returns the length of the longest first name.
	 * 
	 * @param students
	 * @return
	 */
	private static int longestFirstName(List<StudentRecord> students) {
		int longestName = 0;
		for (StudentRecord record : students) {
			if (record.getFirstName().length() > longestName) {
				longestName = record.getFirstName().length();
			}
		}

		return longestName;
	}

	/**
	 * Method used for generating the first and last line in the output.
	 * @param longestFirst - length of the longest first name
	 * @param longestLast - length of the longest last name
	 * @return - generated line
	 */
	private static String generateFirstAndLastLine(int longestFirst, int longestLast) {
		String output = "";
		output += "+";
		for (int i = 0; i < 12; i++) {
			output += "=";
		}

		output += "+";
		for (int i = 0; i < longestLast + 2; i++) {
			output += "=";
		}

		output += "+";
		for (int i = 0; i < longestFirst + 2; i++) {
			output += "=";
		}

		output += "+";
		for (int i = 0; i < 3; i++) {
			output += "=";
		}
		output += "+";
		return output;
	}

}
