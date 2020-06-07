package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;

/**
 * Node representing a command that generates some textual output
 * 
 * @author ilovrencic
 *
 */
public class EchoNode extends Node {

	/**
	 * Array of elements in EchoNode
	 */
	private Element[] elements;

	/**
	 * Default constructor
	 * 
	 * @param elements
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	@Override
	public String toString() {
		String output = "";

		output += "{$= ";
		for (Element element : elements) {
			if (element instanceof ElementFunction) {
				output += "@" + element.asText() + " ";
			} else if (element instanceof ElementString) {
				output += "\"" + element.asText() + "\"" + " ";
			} else {
				output += element.asText() + " ";
			}
		}
		output += "$}";
		return output;
	}

	public Element[] getElements() {
		return elements;
	}

}
