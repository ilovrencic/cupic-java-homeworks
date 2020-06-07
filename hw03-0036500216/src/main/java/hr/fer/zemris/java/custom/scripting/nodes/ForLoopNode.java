package hr.fer.zemris.java.custom.scripting.nodes;

import javax.lang.model.type.NullType;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Node representing a single for-loop construct
 * 
 * @author ilovrencic
 *
 */
public class ForLoopNode extends Node {

	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;

	/**
	 * Default constructor with all elements
	 * 
	 * @param variable
	 * @param starExpression
	 * @param endExpression
	 * @param stepExpression
	 */
	public ForLoopNode(ElementVariable variable, Element starExpression, Element endExpression,
			Element stepExpression) {
		this.variable = variable;
		this.startExpression = starExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Default constructor where stepExpression is {@link NullType}.
	 * 
	 * @param variable
	 * @param starExpression
	 * @param endExpression
	 */
	public ForLoopNode(ElementVariable variable, Element starExpression, Element endExpression) {
		this(variable, starExpression, endExpression, null);
	}

	@Override
	public String toString() {
		String output = "";
		output += "{$FOR " + variable.asText() + " ";

		if (startExpression instanceof ElementString) {
			output += "\"" + startExpression.asText() + "\"" + " ";
		} else {
			output += startExpression.asText() + " ";
		}

		if (endExpression instanceof ElementString) {
			output += "\"" + endExpression.asText() + "\"" + " ";
		} else {
			output += endExpression.asText() + " ";
		}

		if (stepExpression != null) {
			if (stepExpression instanceof ElementString) {
				output += "\"" + stepExpression.asText() + "\"" + " ";
			} else {
				output += stepExpression.asText() + " ";
			}
		}
		output += "$}";
		return output;
	}

	/**
	 * Getter methods for read-only variables
	 */

	public Element getEndExpression() {
		return endExpression;
	}

	public Element getStartExpression() {
		return startExpression;
	}

	public Element getStepExpression() {
		return stepExpression;
	}

	public ElementVariable getVariable() {
		return variable;
	}
}
