package hr.fer.zemris.java.gui.layouts;

/**
 * Class that models the layout position. It has two parameters - row and
 * column. Two positions are equal when they share the same column and row.
 * 
 * @author ilovrencic
 *
 */
public class RCPosition {

	/**
	 * Represents a row in layout
	 */
	private int row;

	/**
	 * Represents a column in layout
	 */
	private int column;

	/**
	 * Default constructor.
	 * 
	 * @param row    - represents a row in layout
	 * @param column - represents a column in layout
	 */
	public RCPosition(int row, int column) {
		this.column = column;
		this.row = row;
	}

	/**
	 * Method that check whether two {@link RCPosition} are some.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RCPosition) {
			RCPosition position = (RCPosition) obj;
			return position.row == row && position.column == column;
		}

		return false;
	}

	/**
	 * Method that returns column value
	 * 
	 * @return - column value
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Method that returns row value
	 * 
	 * @return - row value
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Method that parses {@link String} into {@link RCPosition}.
	 * 
	 * @param position - position written in {@link String}. E.g "1,2".
	 * @return - Instance of {@link RCPosition} parsed from {@link String}.
	 */
	public static RCPosition parse(String position) {
		String[] coordinates = position.split(",");
		if (coordinates.length != 2) {
			throw new IllegalArgumentException(
					"Illegal argument passed as position. There should be two arguments, but you passed "
							+ coordinates.length);
		}

		int[] rcps = new int[2];
		for (int i = 0; i < 2; i++) {
			String coordinate = coordinates[i].trim();
			try {
				rcps[i] = Integer.parseInt(coordinate);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Entered position isn't a number! You have entered " + coordinate);
			}
		}

		return new RCPosition(rcps[0], rcps[1]);
	}
	
	@Override
	public String toString() {
		String output = "Position:";
		output += row + " " +column;
		return output;
	}
}
