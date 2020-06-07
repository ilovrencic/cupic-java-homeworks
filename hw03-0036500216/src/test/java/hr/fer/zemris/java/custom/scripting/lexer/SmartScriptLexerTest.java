package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SmartScriptLexerTest {

	String example1 = "This is sample text.\n" + "{$ FOR i 1 10 1 $}\n"
			+ "  This is {$= i $}-th time this message is generated.\n" + "{$END$}\n" + "{$FOR i 0 10 2 $}\n"
			+ "  sin({$=i$}^2) = {$= i i * @sin  \"0.000\" @decfmt $}\n" + "{$END$}";

	String example2 = "{$ FOR i -1 10 1 $}";

	String example3 = "{$ FOR sco_re \"-1\"10 \"1\" $}";
	
	String example4 = "{$= i i @fja \"str\\\\in\\\"g\" @decfmt ja $}";

	@Test
	void testNextToken() {
		SmartScriptLexer lexer1 = new SmartScriptLexer(example1);

		SmartScriptToken token = lexer1.nextToken();
		assertEquals(token.getType(), SmartScriptTokenType.TEXT);
		token = lexer1.nextToken();
		assertEquals(token.getType(), SmartScriptTokenType.OPEN);
		token = lexer1.nextToken();
		assertEquals(token.getType(), SmartScriptTokenType.DOLLAR_SYMBOL);
		token = lexer1.nextToken();
		assertEquals(token.getType(), SmartScriptTokenType.KEYWORD);
		assertEquals(token.getValue(), "FOR");
		token = lexer1.nextToken();
		assertEquals(token.getType(), SmartScriptTokenType.ID);

		SmartScriptLexer lexer2 = new SmartScriptLexer(example2);

		SmartScriptToken token2 = lexer2.nextToken();
		assertEquals(token2.getType(), SmartScriptTokenType.OPEN);
		token2 = lexer2.nextToken();
		assertEquals(token2.getType(), SmartScriptTokenType.DOLLAR_SYMBOL);
		token2 = lexer2.nextToken();
		assertEquals(token2.getType(), SmartScriptTokenType.KEYWORD);
		assertEquals(token2.getValue(), "FOR");
		token2 = lexer2.nextToken();
		assertEquals(token2.getType(), SmartScriptTokenType.ID);
		token2 = lexer2.nextToken();
		assertEquals(token2.getType(), SmartScriptTokenType.NUMBER);
		token2 = lexer2.nextToken();
		assertEquals(token2.getType(), SmartScriptTokenType.NUMBER);
		token2 = lexer2.nextToken();
		assertEquals(token2.getType(), SmartScriptTokenType.NUMBER);
		token2 = lexer2.nextToken();
		assertEquals(token2.getType(), SmartScriptTokenType.DOLLAR_SYMBOL);
		token2 = lexer2.nextToken();
		assertEquals(token2.getType(), SmartScriptTokenType.CLOSE);

		SmartScriptLexer lexer3 = new SmartScriptLexer(example3);

		SmartScriptToken token3 = lexer3.nextToken();
		assertEquals(token3.getType(), SmartScriptTokenType.OPEN);
		token3 = lexer3.nextToken();
		assertEquals(token3.getType(), SmartScriptTokenType.DOLLAR_SYMBOL);
		token3 = lexer3.nextToken();
		assertEquals(token3.getType(), SmartScriptTokenType.KEYWORD);
		assertEquals(token3.getValue(), "FOR");
		token3 = lexer3.nextToken();
		assertEquals(token3.getType(), SmartScriptTokenType.ID);
		assertEquals(token3.getValue(), "sco_re");
		token3 = lexer3.nextToken();
		assertEquals(token3.getType(), SmartScriptTokenType.NUMBER);
		token3 = lexer3.nextToken();
		assertEquals(token3.getType(), SmartScriptTokenType.NUMBER);
		token3 = lexer3.nextToken();
		assertEquals(token3.getType(), SmartScriptTokenType.NUMBER);
		token3 = lexer3.nextToken();
		assertEquals(token3.getType(), SmartScriptTokenType.DOLLAR_SYMBOL);
		token3 = lexer3.nextToken();
		assertEquals(token3.getType(), SmartScriptTokenType.CLOSE);
		
		SmartScriptLexer lexer4 = new SmartScriptLexer(example4);
		
		SmartScriptToken token4 = lexer4.nextToken();
		assertEquals(token4.getType(), SmartScriptTokenType.OPEN);
		token4 = lexer4.nextToken();
		assertEquals(token4.getType(), SmartScriptTokenType.DOLLAR_SYMBOL);
		token4 = lexer4.nextToken();
		assertEquals(token4.getType(), SmartScriptTokenType.KEYWORD);
		assertEquals(token4.getValue(), "=");
		token4 = lexer4.nextToken();
		assertEquals(token4.getType(), SmartScriptTokenType.ID);
		token4 = lexer4.nextToken();
		assertEquals(token4.getType(), SmartScriptTokenType.ID);
		token4 = lexer4.nextToken();
		assertEquals(token4.getType(), SmartScriptTokenType.FUNCTION);
		token4 = lexer4.nextToken();
		assertEquals(token4.getType(), SmartScriptTokenType.TAG_TEXT);
		token4 = lexer4.nextToken();
	}
}
