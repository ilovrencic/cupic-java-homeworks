package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * 
 * @author ilovrencic
 * 
 * Program that's used for computing factorial for numbers in range from 3 to 20
 *
 */

public class Factorial {
	
	private static String END_OF_PROGRAM = "kraj";

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.print("Unesite broj > ");
			
			if(sc.hasNextInt()) {
				int current = sc.nextInt();
				
				if( current > 20 || current < 3) {
					System.out.format("'%s' nije u dozvoljenom rasponu.%n",current);
				} else {
					try {
						System.out.format("%s! = %s%n",current,calculateFactorial(current));
					} catch( IllegalArgumentException exception ) {
						System.out.println(exception.getMessage());
					}
				}
			} else {
				String element = sc.next();
				
				if(element.equals(END_OF_PROGRAM)) {
					System.out.println("Doviđenja!");
					break;
				}
				
				System.out.format("'%s' nije cijeli broj.%n",element);
			}
		}
		
		sc.close();
	}
	
	/**
	 * Function used to calculate factorial from the given number. Function throws
	 * an IllegalArgumentException for numbers larger than 19.
	 * @param number variable from which we calculate factorial
	 * @return factorial of a passed number variable
	 */
	static int calculateFactorial(int number) {
		int factorial = 1;
		
		if( number > 19 ) {
			throw new IllegalArgumentException("Faktorijel ovog broj je prevelik!");
		}
		
		for( int i = 1; i <= number; i++) {
			factorial *= i;
		}
		
		return factorial;
	}

}
