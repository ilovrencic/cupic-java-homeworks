package hr.fer.zemris.java.custom.collections.demo;

import javax.naming.OperationNotSupportedException;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Demo class where we are going to show simple usage of {@link ObjectStack} class.
 * @author ilovrencic
 *
 */
public class StackDemo {

	/**
	 * Main method. Here we are going to perform postfix operations with a help of 
	 * {@link ObjectStack} class.
	 * @param args
	 */
	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.err.println("Number of arguments should be equal to one! Current"
					+ " number of arguments is "+String.valueOf(args.length)+"!");
			return;
		}
		
		ObjectStack stack = new ObjectStack();
		String[] expressions = splitExpression(args[0]);
		String operations = "+-*/%";
		
		for(String expression : expressions) {
			if(isInteger(expression)) {
				stack.push(Integer.parseInt(expression));
				continue;
			}
			
			if(!operations.contains(expression)) {
				System.err.println("Unallowed expression! This is not an operator, nor a word! --> "+expression);
				return;
			}
			
			Integer firstNumber;
			Integer secondNumber;
			try {
				firstNumber = (Integer) stack.pop();
				secondNumber = (Integer) stack.pop();
			} catch(EmptyStackException e) {
				e.printStackTrace();
				System.err.println("Unallowed action while stack was empty!");
				return;
			}
			
			Integer result;
			try {
				result = performOperation(expression,firstNumber,secondNumber);
			} catch (OperationNotSupportedException e) {
				e.printStackTrace();
				return;
			}
			stack.push(result);
		}
		
		if(stack.size() != 1) {
			System.err.println("Something went wrong. Stack size should be 1!");
			return;
		}
		
		System.out.println(stack.pop());
	}
	
	/**
	 * Method where we are going to determine which operation should be performed.
	 * @param expression operation expression
	 * @param firstNumber first number in operation
	 * @param secondNumber second number in operation
	 * @return result of operation
	 * @throws OperationNotSupportedException
	 */
	private static Integer performOperation(String expression,Integer firstNumber,Integer secondNumber) throws OperationNotSupportedException {
		switch(expression) {
			case "+": return secondNumber+firstNumber;
			case "-": return secondNumber-firstNumber;
			case "/": return (Integer)(secondNumber/firstNumber);
			case "*": return firstNumber*secondNumber;
			case "%": return (Integer)(secondNumber%firstNumber);
			default: throw new OperationNotSupportedException();
		}
	}
	
	
	/**
	 * Method where we are splitting expression into numbers and operations.
	 * @param expression string we got from argument line
	 * @return list of numbers and expressions
	 */
	private static String[] splitExpression(String expression) {
		String[] expressions = expression.split("\\s+");
		for(int i = 0; i < expressions.length; i++) {
			expressions[i] = expressions[i].trim();
		}
		return expressions;
	}
	
	/**
	 * Method that checks whether the string is integer
	 * @param text string for which we want to check whether it is integer
	 * @return true if string is integer, otherwise false
	 */
	private static boolean isInteger(String text) {
		try {
			Integer.parseInt(text);
			return true;
		} catch(Exception e) {
			return false;
		}
	}	
}

