package hr.fer.zemris.java.hw03.prob2;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Demo class where we show how the {@link SmartScriptParser} works.
 * 
 * @author ilovrencic
 *
 */
public class SmartScriptTester {

	public static void main(String[] args) {

		String example1 = "This is sample text.\n" + "{$ FOR i 1 10 1 $}\n"
				+ "  This is {$= i $}-th time this message is generated.\n" + "{$END$}\n" + "{$FOR i 0 10 2 $}\n"
				+ "  sin({$=i$}^2) = {$= i i * @sin  \"0.000\" @decfmt $}\n" + "{$END$}";

		SmartScriptParser parser1 = new SmartScriptParser(example1);
		DocumentNode head1 = parser1.getMainNode();
		String reparsedExample1 = parser1.createOriginalDocumentBody(head1);

		System.out.println("Original example:");
		System.out.println(example1);
		System.out.println("----------------------------------");
		System.out.println("Reparsed example:");
		System.out.println(reparsedExample1);
	}

}
