package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * 
 * @author ilovrencic
 *
 * Program that calculates Rectangles area and circumference. It supports argument or console way of handling input.
 * 	
 */
public class Rectangle {
	
	private static String WIDTH_INPUT = "Unesite širinu > ";
	private static String HEIGTH_INPUT = "Unesite visinu > ";

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		if(args.length == 2) {
			try {
				handleUserInputArgs(args);
			} catch(NumberFormatException exception) {
				System.out.println(exception);
			}
		} else if(args.length > 0 && args.length != 2) {
			System.err.println("Pogrešan broj argumenata! Prekid programa.");
		} else {
			handleUserInputScanner(sc);
		}
		
		sc.close();
	}
	
	/**
	 * Function that calculates area of a rectangle based on a given parameters
	 * @param width
	 * @param heigth
	 * @return area of the rectangle
	 */
	static double calculateRectangleArea(double width,double heigth) {
		return width*heigth;
	}
	
	/**
	 * Function that calculates circumference of a rectangle based on a given parameters
	 * @param width
	 * @param heigth
	 * @return
	 */
	static double calculateRectangleCircumference(double width,double heigth) {
		return 2*(width+heigth);
	}
	
	/**
	 * Function that handles user input through scanner
	 * @param sc Scanner object
	 */
	static void handleUserInputScanner(Scanner sc) {
		double heigth,width;
		width = getParamInput(WIDTH_INPUT,sc);
		heigth = getParamInput(HEIGTH_INPUT,sc);
		
		System.out.format(
				"Pravokutnik širine %s i visine %s ima površinu %s i opseg %s.%n",
				width,
				heigth,
				calculateRectangleArea(width, heigth),
				calculateRectangleCircumference(width, heigth)
				);
	}
	
	/**
	 * Method used for handling user input from the keyboard.
	 * 
	 * @param input String that's printed before user input
	 * @param sc Scanner object
	 * @return double input
	 */
	static double getParamInput(String input, Scanner sc) {
		double param;
		while(true) {
			System.out.print(input);
			
			if(sc.hasNext()) {
				String element = sc.next();
				
				try {
					param = Double.parseDouble(element);
					if( param < 0 ) {
						System.out.println("Unijeli ste negativnu vrijednost.");
					}
					else {
						return param;
					}
				} catch( NumberFormatException exception ) {
					System.out.format("'%s' se ne može protumačiti kao broj.%n",element);
				}
			}
		}
	}
	
	/**
	 * Function that handles user input via arguments
	 * @param args arguments
	 */
	static void handleUserInputArgs(String[] args) {
		double heigth,width;
		width = Double.parseDouble(args[0]);
		heigth = Double.parseDouble(args[1]);
		
		System.out.format(
				"Pravokutnik širine %s i visine %s ima površinu %s i opseg %s.%n",
				width,
				heigth,
				calculateRectangleArea(width, heigth),
				calculateRectangleCircumference(width, heigth)
				);
	}
}