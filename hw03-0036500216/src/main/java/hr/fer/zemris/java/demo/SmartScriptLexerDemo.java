package hr.fer.zemris.java.demo;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * 
 * @author ilovrencic
 *
 */
public class SmartScriptLexerDemo {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String example = "Example \\{$=1$}. Now actually write one {$=1$}";
		
		SmartScriptParser parser = new SmartScriptParser(example);
		String example1 = parser.createOriginalDocumentBody(parser.getMainNode());
		SmartScriptParser parser1 = new SmartScriptParser(example1);
		String example2 = parser1.createOriginalDocumentBody(parser1.getMainNode());
		System.out.println(example2);
	}
}
