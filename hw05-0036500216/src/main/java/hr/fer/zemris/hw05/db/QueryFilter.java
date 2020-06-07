package hr.fer.zemris.hw05.db;

import java.util.List;

/**
 * Class that implements {@link IFilter}.
 * 
 * @author ilovrencic
 *
 */
public class QueryFilter implements IFilter {

	/**
	 * List of expressions
	 */
	private List<ConditionalExpression> expressions;

	/**
	 * Default constructor
	 * 
	 * @param expressions - list of expressions we want to check
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = expressions;
	}

	/**
	 * Method that checks whether the query satisfies all the conditions
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression expression : expressions) {
			boolean condition = expression.getOperator().satisfied(expression.getGetter().get(record),
					expression.getLiteral());

			if (!condition)
				return false;

		}

		return true;
	}

}
