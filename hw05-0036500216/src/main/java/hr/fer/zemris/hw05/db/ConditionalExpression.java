package hr.fer.zemris.hw05.db;

/**
 * Class that stores {@link IComparisonOperator}, {@link IFieldValueGetter} and
 * {@link String} and than checks whether {@link StudentRecord} satisfies this
 * {@link ConditionalExpression}.
 * 
 * @author ilovrencic
 *
 */
public class ConditionalExpression {

	/**
	 * Operator that checks some operation between two strings.
	 */
	private IComparisonOperator operator;

	/**
	 * Getter that returnes certain {@link String} from {@link StudentRecord}.
	 */
	private IFieldValueGetter getter;

	/**
	 * {@link String} we want to compare with some part of {@link StudentRecord}.
	 */
	private String literal;

	/**
	 * Default constructor
	 * 
	 * @param operator - {@link IComparisonOperator} instance
	 * @param getter   - {@link IFieldValueGetter} instance
	 * @param literal  - {@link String} instance
	 */
	public ConditionalExpression(IComparisonOperator operator, IFieldValueGetter getter, String literal) {
		if (operator == null || getter == null || literal == null) {
			throw new NullPointerException("You can't pass null value to Conditional Expression!");
		}
		this.operator = operator;
		this.getter = getter;
		this.literal = literal;
	}

	/* -------- GETTERS ---------- */

	public IFieldValueGetter getGetter() {
		return getter;
	}

	public String getLiteral() {
		return literal;
	}

	public IComparisonOperator getOperator() {
		return operator;
	}

	/* ---------------------------- */

}
