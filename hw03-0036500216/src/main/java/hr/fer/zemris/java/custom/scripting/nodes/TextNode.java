package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Node representing textual data
 * 
 * @author ilovrencic
 *
 */
public class TextNode extends Node {

	/**
	 * Represents text value of this Node
	 */
	private String text;

	/**
	 * Default constructor
	 * 
	 * @param text - text value we want to preserve
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * Getter method for text value
	 * 
	 * @return
	 */
	public String getText() {
		return text;
	}

	/**
	 * ToString function to make a nice output for this node.
	 */
	@Override
	public String toString() {
		String output = "";
		
		if(text.contains("{")) {
			int index = text.indexOf("{");
			String firstPart = text.substring(0,index);
			String escape = "\\";
			String secondPart = text.substring(index);
			output = firstPart + escape + secondPart;
			return output;
		}
		return text;
	}
}
