package hr.fer.zemris.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StudentDatabaseTest {

	String one = "0036500216 Ivan Lovrencic 5";
	String two = "0032323133        Petar      Lukic 3";
	String three = "003231312 Jaksa Jakovac          2";
	String four = "003231312 Jaksa Jakovac          2";

	@Test
	void testForJMBAG() {
		List<String> records = new ArrayList<String>();
		records.add(one);
		records.add(two);
		records.add(three);
	
		StudentDatabase database = new StudentDatabase(records);
		assertEquals(database.forJMBAG("0036500216").toString(),"0036500216 Ivan Lovrencic 5");
	}

	@Test
	void testFilter() {
		List<String> records = new ArrayList<String>();
		records.add(one);
		records.add(two);
		records.add(three);
	
		StudentDatabase database = new StudentDatabase(records);
		List<StudentRecord> filterdRecords = database.filter(new IFilter() {
		
			@Override
			public boolean accepts(StudentRecord record) {
				return true;
			}
		});
		
		assertEquals(filterdRecords.size(),3);
	}
	
	@Test
	void testComparisonOperators() {
		IComparisonOperator oper1 = ComparisonOperator.LESS;
		assertEquals(oper1.satisfied("Ana","Ivana"), true);
		assertEquals(oper1.satisfied("Ana", "Ana"), false);
		
		IComparisonOperator oper2 = ComparisonOperator.LESS_OR_EQUALS;
		assertEquals(oper2.satisfied("Ana","Ivana"), true);
		assertEquals(oper2.satisfied("Ana", "Ana"), true);
		assertEquals(oper2.satisfied("Jasna", "Ana"),false);
		
		IComparisonOperator oper3 = ComparisonOperator.GREATER;
		assertEquals(oper3.satisfied("Ivana","Ana"), true);
		assertEquals(oper3.satisfied("Ana", "Ana"), false);
		assertEquals(oper3.satisfied("Ana", "Jasna"),false);
		
		IComparisonOperator oper4 = ComparisonOperator.GREATER_OR_EQUAL;
		assertEquals(oper4.satisfied("Ivana","Ana"), true);
		assertEquals(oper4.satisfied("Ana", "Ana"), true);
		assertEquals(oper4.satisfied("Ana", "Jasna"),false);
		
		IComparisonOperator oper5 = ComparisonOperator.EQUALS;
		assertEquals(oper5.satisfied("Ana","Ivana"), false);
		assertEquals(oper5.satisfied("Ana", "Ana"), true);
		
		IComparisonOperator oper6 = ComparisonOperator.NOT_EQUALS;
		assertEquals(oper6.satisfied("Ana","Ivana"), true);
		assertEquals(oper6.satisfied("Ana", "Ana"), false);
		
		IComparisonOperator oper7 = ComparisonOperator.LIKE;
		assertEquals(oper7.satisfied("AAA", "A*"), true);
		assertEquals(oper7.satisfied("AAAA", "A*A"), true);
		assertEquals(oper7.satisfied("AAA", "AA*AA"), false);
		assertEquals(oper7.satisfied("ABCD", "A*"), true);
		assertEquals(oper7.satisfied("ABCD", "*B"), false);
		assertEquals(oper7.satisfied("ABCD", "*D"), true);
		assertEquals(oper7.satisfied("AABBCC", "A*C"), true);
		assertEquals(oper7.satisfied("AAA", "A*A"), true);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> oper7.satisfied("AAA", "A**"));
	}
	
	@Test
	void testFieldGetters() {
		StudentRecord record = new StudentRecord("0036500216","Ivan","Lovrencic",5);
		
		IFieldValueGetter firstName = FieldValueGetters.FIRST_NAME;
		assertEquals(firstName.get(record),"Ivan");
		
		IFieldValueGetter lastName = FieldValueGetters.LAST_NAME;
		assertEquals(lastName.get(record),"Lovrencic");
		
		IFieldValueGetter jmbag = FieldValueGetters.JMBAG;
		assertEquals(jmbag.get(record),"0036500216");
	}
	
	@Test
	void testConditionalExpression() {
		StudentRecord record = new StudentRecord("0036500216","Ivan","Lovrencic",5);
		ConditionalExpression expression = new ConditionalExpression(ComparisonOperator.LIKE, FieldValueGetters.JMBAG, "00365*");
		
		boolean satisfied = expression.getOperator().satisfied(expression.getGetter().get(record),expression.getLiteral());
		assertTrue(satisfied);
	}
	
	@Test
	void testLexer() {
		String query = "queRy jmbag=\"0123456789\" and lastName<=\"J\"";
		Lexer lexer = new Lexer(query);
		
		assertEquals(lexer.getNextToken().getType(),TokenType.QUERY);
		assertEquals(lexer.getNextToken().getType(),TokenType.FIELD);
		assertEquals(lexer.getNextToken().getType(),TokenType.SYMBOL);
		assertEquals(lexer.getNextToken().getType(),TokenType.VALUE);
		assertEquals(lexer.getNextToken().getType(),TokenType.AND);
		assertEquals(lexer.getNextToken().getType(),TokenType.FIELD);
		assertEquals(lexer.getNextToken().getType(),TokenType.SYMBOL);
		assertEquals(lexer.getNextToken().getType(),TokenType.VALUE);
		assertEquals(lexer.getNextToken().getType(),TokenType.EOF);
	}
	
	@Test
	void testParser() {
		String query = "queRy jmbag=\"0123456789\" ";
		QueryParser parser = new QueryParser(query);
		
		assertEquals(parser.getQueriedJMBAG(),"0123456789");
	}

}
