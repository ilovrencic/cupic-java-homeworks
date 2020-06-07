package hr.fer.zemris.java.hw02;

/**
 * Class that represents Complex Number
 * It has it's real and imaginary parts.
 * @author ilovrencic
 *
 */
public class ComplexNumber {
	
	//Maximum precision between two numbers
	private static final double EPSILON_VALUE = 1e-06;
	
	private double real;
	private double imag;
	
	/**
	 * Default constructor for {@link ComplexNumber}
	 * @param real double that will represent real part of Complex number
	 * @param imag double that will represent imaginary part of Complex number
	 */
	public ComplexNumber(double real,double imag) {
		this.real = real;
		this.imag = imag;
	}
	
	/**
	 * Method that returns real part of {@link ComplexNumber}
	 * @return real part of {@link ComplexNumber}
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Method that returns imaginary part of {@link ComplexNumber}
	 * @return imaginary part of {@link ComplexNumber}
	 */
	public double getImaginary() {
		return imag;
	}
	
	/**
	 * Method that returns magnitude of {@link ComplexNumber}
	 * @return magnitude
	 */
	public double getMagnitude() {
		return Math.sqrt((real*real+imag*imag));
	}
	
	/**
	 * Method that returns angle of {@link ComplexNumber}
	 * The angle is confined from 0 to 2*PI
	 * @return
	 */
	public double getAngle() {
		//both values are zero, so we can return zero
		if( Math.abs(real) < EPSILON_VALUE && Math.abs(imag) < EPSILON_VALUE) {
			return 0;	
		}
		
		if(Math.abs(imag) < EPSILON_VALUE) {
			return 0;
		}
		
		if( Math.abs(real) < EPSILON_VALUE ) {
			if(imag >= 0 && real >= 0) {
				return Math.PI/2;
			} else if(imag >= 0 && real <= 0) {
				return 3*Math.PI/2;
			} else if(imag <= 0 && real >= 0) {
				return 3*Math.PI/2;
			} else {
				return Math.PI/2;
			}
		}
		
		if(real >= 0 && imag > 0) {
			return Math.atan(imag/real);
		} else if(real < 0 && imag > 0) {
			return Math.atan(imag/real) + Math.PI;
		} else if(real < 0 && imag < 0) {
			return Math.atan(imag/real) + Math.PI;
		} else {
			return Math.atan(imag/real) + Math.PI*2;
		}
		
	}
	
	/**
	 * Method that adds complex number to current instance of complex number
	 * @param c complex number
	 * @return result of addition
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(real+c.getReal(),imag+c.getImaginary());
	}
	
	/**
	 * Method that subtracts complex number from current instance of complex number
	 * @param c complex number
	 * @return result of subtraction
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(real-c.getReal(),imag-c.getImaginary());
	}
	
	/**
	 * Method that multiplies complex number with current instance of complex number
	 * @param c complex number
	 * @return result of multiplication
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double newReal = real*c.getReal()-imag*c.getImaginary();
		double newImag = real*c.getImaginary()+imag*c.getReal();
		return new ComplexNumber(newReal,newImag);
	}
	
	/**
	 * Method that divides complex number with current instance of complex number
	 * @param c complex number
	 * @return result of division
	 */
	public ComplexNumber div(ComplexNumber c) {
		double newReal = 
				(real*c.getReal()+imag*c.getImaginary())/(c.getReal()*c.getReal()+c.getImaginary()*c.getImaginary());
		double newImag = 
				(imag*c.getReal()-real*c.getImaginary())/(c.getReal()*c.getReal()+c.getImaginary()*c.getImaginary());
		return new ComplexNumber(newReal,newImag);
	}
	
	/**
	 * Method that rises Complex Number to the power of n
	 * @param n - power we want to take Complex Number
	 * @return Complex number that's result of rising to the power of n
	 */
	public ComplexNumber power(int n) {
		if( n < 0 ) {
			throw new IllegalArgumentException("n can't be lower than zero!");
		}
		
		return ComplexNumber.fromMagnitudeAndAngle(Math.pow(this.getMagnitude(),n),this.getAngle()*n);
	}
	
	/**
	 * Method that takes n-th root of complex number
	 * @param n - number of the root we want
	 * @return resulting complex number
	 */
	public ComplexNumber[] root(int n) {
		if( n < 1 ) {
			throw new IllegalArgumentException("n can't be lower than one!");
		}
		
		ComplexNumber numbers[] = new ComplexNumber[n];
		double magnitude = Math.pow(this.getMagnitude(), 1/(double)n);
		
		for (int i = 0; i < n; i++) {
			double currentAngle = (this.getAngle()+2*i*Math.PI)/((double)n);
			currentAngle = transferAngle(currentAngle);
			numbers[i] = ComplexNumber.fromMagnitudeAndAngle(magnitude, currentAngle);
		}
		
		return numbers;
	}
	
	/**
	 * Method that returns string version of {@link ComplexNumber} instance
	 */
	@Override
	public String toString() {
		if( imag < 0 ) {
			return String.format(" %.2f - %.2fi ", real, Math.abs(imag));
		} else {
			return String.format(" %.2f + %.2fi ", real, imag);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if( obj instanceof ComplexNumber ) {
			ComplexNumber current = (ComplexNumber) obj;
			if(Math.abs(current.getReal()-real) < EPSILON_VALUE 
					&& Math.abs(current.imag-imag) < EPSILON_VALUE) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Static factory method used for creating Complex Number from real component
	 * @param real - real component of Complex Number
	 * @return Complex Number with real component set by argument and with imaginary component 0 
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real,0);
	}
	
	/**
	 * Static factory method used for creating Complex Number from imaginary component
	 * @param imag - imaginary component of Complex Number
	 * @return Complex Number with imaginary component set and with real component 0
	 */
	public static ComplexNumber fromImaginary(double imag) {
		return new ComplexNumber(0,imag);
	}
	
	/**
	 * Static factory method used for creating Complex Number from magnitude and angle
	 * @param magnitude - magnitude of the complex number
	 * @param angle - angle of the complex number
	 * @return Complex number made with magnitude and angle 
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude,double angle) {
		if(magnitude < 0) {
			throw new IllegalArgumentException("Magnitude can't be number smaller than zero!");
		}
		return new ComplexNumber(magnitude*Math.cos(angle),magnitude*Math.sin(angle));
	}
	
	/**
	 * Static factory method used for creating Complex Number by parsing string
	 * @param s - string that we are going to parse into Complex Number
	 * @return Complex Number thats gotten from parsing string s
	 */
	public static ComplexNumber parse(String s) {
		String operands = "+-";
		String imaginary = "i";
		
		String real = "";
		String imag = "";
		
		char[] parts = s.toCharArray();
		
		String currentNumber = "";
		boolean isNegative = false;
		for(int i = 0; i < parts.length; i++) {
			if(String.valueOf(parts[i]).equals(" ")) {
				continue;
			}
			
			if(operands.contains(String.valueOf(parts[i]))) {
				if(!currentNumber.isEmpty()) {
					if(isNegative) {
						real = "-" + currentNumber;
					} else {
						real = currentNumber;
					}
					currentNumber = "";
				}
				
				String currentOperand = String.valueOf(parts[i]);
				if(currentOperand.equals("-")) isNegative = true;
				else isNegative = false;
				continue;
			}
			
			if(imaginary.contains(String.valueOf(parts[i]))){
				if(currentNumber.isEmpty()) {
					if(isNegative) {
						imag = "-1";
					} else {
						imag = "1";
					}
					
				} else {
					if(isNegative) {
						imag = "-" + currentNumber;
					} else {
						imag = currentNumber;
					}
				}
				break;
			}
			
			currentNumber += String.valueOf(parts[i]);
		}
		
		if(!currentNumber.isEmpty() && real.isEmpty()) {
			real = currentNumber;
		}
		
		if(real.isEmpty()){
			real = "0";
		}
		
		if(imag.isEmpty()) {
			imag = "0";
		}
		
		try {
			double parsedReal = Double.parseDouble(real);
			double parsedImag = Double.parseDouble(imag);
			return new ComplexNumber(parsedReal,parsedImag);
		} catch (NumberFormatException e) {
			System.err.println("Parsing went wrong. Make sure you entered a correct complex number!");
			return null;
		}
	}
	
	/**
	 * Method that normalizes angle to scope between 0 and 2*PI
	 * @param angle - angle we want to normalize
	 * @return normalized angle
	 */
	private double transferAngle(double angle) {
		if( angle >= 0) {
			return angle%(Math.PI*2);
		} else {
			return (angle%(Math.PI*2))+Math.PI*2;
		}
	}

}
