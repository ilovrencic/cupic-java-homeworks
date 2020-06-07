package hr.fer.zemris.hw05.db;

/**
 * Class that initializes different {@link ComparisonOperator}.
 * 
 * @author ilovrencic
 *
 */
public class ComparisonOperator {

	/**
	 * Class that checks whether the first {@link String} is smaller (in Unicode)
	 * than the second {@link String}.
	 */
	public static final IComparisonOperator LESS = new IComparisonOperator() {
		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.compareTo(value2) < 0;
		}
	};

	/**
	 * Class that checks whether the first {@link String} is smaller or equal (in
	 * Unicode) than the second {@link String}.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = new IComparisonOperator() {
		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.compareTo(value2) <= 0;
		}
	};

	/**
	 * Class that checks whether the first {@link String} is greater (in Unicode)
	 * than the second {@link String}.
	 */
	public static final IComparisonOperator GREATER = new IComparisonOperator() {
		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.compareTo(value2) > 0;
		}
	};

	/**
	 * Class that checks whether the first {@link String} is greater or equal (in
	 * Unicode) than the second {@link String}.
	 */
	public static final IComparisonOperator GREATER_OR_EQUAL = new IComparisonOperator() {
		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.compareTo(value2) >= 0;
		}
	};

	/**
	 * Class that checks whether the first {@link String} is equal with the second
	 * {@link String}.
	 */
	public static final IComparisonOperator EQUALS = new IComparisonOperator() {
		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.equals(value2);
		}
	};

	/**
	 * Class that checks whether the first {@link String} is not equal with the
	 * second {@link String}.
	 */
	public static final IComparisonOperator NOT_EQUALS = new IComparisonOperator() {
		@Override
		public boolean satisfied(String value1, String value2) {
			return !value1.equals(value2);
		}
	};

	/**
	 * Class that check whether the first {@link String} satisfies the pattern that
	 * is presented in second {@link String}. E.g. "AAAA" and "AA*AA" should return
	 * true, but "AAA" and "AA*AA" should return false.
	 */
	public static final IComparisonOperator LIKE = new IComparisonOperator() {
		@Override
		public boolean satisfied(String value1, String value2) {
			if (containsMoreThanOneStar(value2)) {
				throw new IllegalArgumentException("Matcher regex in LIKE operator contains more than one * symobol!");
			}

			String[] parts = value2.split("\\*");
			if (parts.length == 2) {
				if (value1.length() < parts[0].length() + parts[1].length()) {
					return false;
				}

				String leftSubstring = value1.substring(0, parts[0].length());
				String rightSubstring = value1.substring(value1.length() - parts[1].length(), value1.length());

				if (leftSubstring.equals(parts[0]) && rightSubstring.equals(parts[1])) {
					return true;
				} else {
					return false;
				}
			} else {
				if (value1.length() < parts[0].length()) {
					return false;
				}

				if (value2.charAt(0) == '*') {
					String rightSubstring = value1.substring(value1.length() - parts[0].length(), value1.length());
					return rightSubstring.equals(parts[0]);

				} else if (value2.charAt(value2.length() - 1) == '*') {

					String leftSubstring = value1.substring(0, parts[0].length());
					return leftSubstring.equals(parts[0]);

				} else {
					throw new IllegalArgumentException("There shouldn't be more than one * symbol!");
				}
			}
		}

		/**
		 * Method that checks whether the pattern contains more than one * symbol.
		 * 
		 * @param value - string we want to check
		 * @return false if there isn't more than one, otherwise true
		 */
		private boolean containsMoreThanOneStar(String value) {
			char[] letters = value.toCharArray();

			int counter = 0;
			for (Character letter : letters) {
				if (letter == '*') {
					counter++;
				}

				if (counter == 2) {
					return true;
				}
			}

			return counter > 1;
		}
	};

}
