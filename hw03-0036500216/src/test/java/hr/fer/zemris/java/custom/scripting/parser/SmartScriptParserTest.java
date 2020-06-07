package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.nodes.*;

class SmartScriptParserTest {

	/*
	 * For some reason readAllBytes method won't work for me :(
	 */
//	private String readExample(int n) {
//		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer" + n + ".txt")) {
//			if (is == null)
//				throw new RuntimeException("Datoteka extra/primjer" + n + ".txt je nedostupna.");
//			byte[] data = ((Object) this.getClass().getClassLoader().getResourceAsStream("extra/primjer1.txt")).readAllBytes();
//			String text = new String(data, StandardCharsets.UTF_8);
//			return text;
//		} catch (IOException ex) {
//			throw new RuntimeException("Greška pri čitanju datoteke.", ex);
//		}
//	}

	String example1 = "This is sample text.\n" + "{$ FOR i 1 10 1 $}\n"
			+ "  This is {$= i $}-th time this message is generated.\n" + "{$END$}\n" + "{$FOR i 0 10 2 $}\n"
			+ "  sin({$=i$}^2) = {$= i i * @sin  \"0.000\" @decfmt $}\n" + "{$END$}";

	String example2 = "{$ FOR i -1 10 1 $}{$END$}";

	String example3 = "{$ FOR sco_re \"-1\"10 \"1\" $}{$END$}";

	String example4 = "{$= i i @fja \"str\\\\in\\\"g\" @decfmt ja $}";

	@Test
	void testSmartScriptParser1() {
		// example from the homework
		SmartScriptParser parser1 = new SmartScriptParser(example1);
		DocumentNode head1 = parser1.getMainNode();
		String originalExample1 = parser1.createOriginalDocumentBody(head1);
		SmartScriptParser parser2 = new SmartScriptParser(originalExample1);
		DocumentNode head2 = parser2.getMainNode();
		String originalExample2 = parser2.createOriginalDocumentBody(head2);
		assertEquals(originalExample1, originalExample2);

		System.out.println(originalExample1);
	}

	@Test
	void testSmartScriptParser2() {
		SmartScriptParser parser1 = new SmartScriptParser(example2);
		DocumentNode head1 = parser1.getMainNode();
		String originalExample1 = parser1.createOriginalDocumentBody(head1);
		SmartScriptParser parser2 = new SmartScriptParser(originalExample1);
		DocumentNode head2 = parser2.getMainNode();
		String originalExample2 = parser2.createOriginalDocumentBody(head2);
		assertEquals(originalExample1, originalExample2);

		System.out.println(originalExample1);
	}

	@Test
	void testSmartScriptParser3() {
		SmartScriptParser parser1 = new SmartScriptParser(example3);
		DocumentNode head1 = parser1.getMainNode();
		String originalExample1 = parser1.createOriginalDocumentBody(head1);
		SmartScriptParser parser2 = new SmartScriptParser(originalExample1);
		DocumentNode head2 = parser2.getMainNode();
		String originalExample2 = parser2.createOriginalDocumentBody(head2);
		assertEquals(originalExample1, originalExample2);

		System.out.println(originalExample1);
	}

	@Test
	void testSmartScriptParser4() {
		SmartScriptParser parser1 = new SmartScriptParser(example4);
		DocumentNode head1 = parser1.getMainNode();
		String originalExample1 = parser1.createOriginalDocumentBody(head1);
		
		System.out.println(originalExample1);
	}
}
