package hr.fer.zemris.hw05.db;

import java.util.List;

/**
 * Class that represent a Query filter object. Here we are passing the query to
 * {@link Parser} and offering user the access to the method for acquiring to
 * obtained {@link ConditionalExpression}s.
 * 
 * @author ilovrencic
 *
 */
public class QueryParser {

	/**
	 * Represents a list of expressions
	 */
	private List<ConditionalExpression> expressions;

	/**
	 * Default constructor
	 * 
	 * @param query - string that we want to parse
	 */
	public QueryParser(String query) {
		if (query == null) {
			throw new NullPointerException("Query can't be null!");
		}

		parseQuery(query);
	}

	/**
	 * Method that checks whether the query is direct. Query is direct if the query
	 * is this and only this type: query jmbag="xxxxxxxx".
	 * 
	 * @return true if it is direct, otherwise false
	 */
	public boolean isDirectQuery() {
		if (expressions != null) {
			if (expressions.size() == 1) {
				return expressions.get(0).getOperator().equals(ComparisonOperator.EQUALS)
						&& expressions.get(0).getGetter().equals(FieldValueGetters.JMBAG);
			}
		}

		return false;
	}

	/**
	 * Method that returns queried jmbag, if the query is direct.
	 * 
	 * @return
	 */
	public String getQueriedJMBAG() {
		if (isDirectQuery()) {
			return expressions.get(0).getLiteral();
		} else {
			throw new IllegalStateException("This isn't a direct query!");
		}

	}

	/**
	 * Getter for expressions.
	 * 
	 * @return
	 */
	public List<ConditionalExpression> getQuery() {
		return expressions;
	}

	/**
	 * Helper method that acquires expressions with the help of {@link Parser}.
	 * 
	 * @param query
	 */
	private void parseQuery(String query) {
		Parser parser = new Parser(query);
		expressions = parser.getExpressions();
	}

}
